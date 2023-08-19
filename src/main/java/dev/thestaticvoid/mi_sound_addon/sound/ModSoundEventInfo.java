package dev.thestaticvoid.mi_sound_addon.sound;

import net.minecraft.sounds.SoundEvent;

public class ModSoundEventInfo {
    private SoundEvent soundEvent;
    private int soundDuration;
    private float volume;

    public ModSoundEventInfo(SoundEvent soundEvent, int soundDuration, float volume) {
        this.soundEvent = soundEvent;
        this.soundDuration = soundDuration;
        this.volume = volume;
    }

    public SoundEvent getSoundEvent() {
        return this.soundEvent;
    }

    public int getSoundDuration() {
        return this.soundDuration;
    }

    public void setSoundDuration(int soundDuration) {
        this.soundDuration = soundDuration;
    }

    public float getVolume() {
        return this.volume;
    }

    public void setVolume(float volume) {
        this.volume =  volume;
    }
}
