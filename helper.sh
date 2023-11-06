#! /usr/bin/env bash

# =====================================================================

usage() {
  echo "usage: ${0} <cmd> [<args>...]"
  echo
  echo "  autogenAll                        calculate all durations"
  echo "  updateDurations [<machines>...]   calculate durations for list of machines"
  echo
  echo "examples:"
  echo "  ${0} updateDurations coke_oven distillation_tower oil_drilling_rig polarizer"
  echo "  ${0} autogenAll"
}

require_command() {
  command -v ${1} >/dev/null 2>/dev/null || {
    echo "[ERROR] ${1} is not installed!"
    echo
    usage
    exit 1
  }
}

require_command ffprobe
cd -- "${BASH_SOURCE[0]%/*}"

# =====================================================================

calculateDuration() {
  LC_NUMERIC=C printf "%.0f" $(echo "$(ffprobe -show_entries format=duration -of default=noprint_wrappers=1:nokey=1 "${1}" 2>/dev/null) * 20 - 1" | bc)
}

updateDuration() {
  sed -i -r "s/(setDuration\(\"${1}\",) [0-9]*/\1 ${2}/" src/main/java/dev/thestaticvoid/mi_sound_addon/sound/SoundEventRegistry.java
}

autogenAll() {
  for f in src/main/resources/assets/mi_sound_addon/sounds/*.ogg; do
    local m=$(basename "${f}" .ogg)
    local d=$(calculateDuration "${f}")
    updateDuration ${m} ${d}
  done
}

updateDurations() {
  for m in "${@}"; do
    local d=$(calculateDuration "src/main/resources/assets/mi_sound_addon/sounds/${m}.ogg")
    updateDuration ${m} ${d}
  done
}

case "${1}" in
  autogenAll|updateDurations) "${@}" ;;
  *) usage ;;
esac
