package dev.thestaticvoid.mi_sound_addon.sound;

import net.minecraft.sounds.SoundEvent;

import java.util.function.Supplier;

public class ModSoundEventInfo {
    private final Supplier<SoundEvent> soundEvent;
    private int soundDuration;
    private float volume;

    public ModSoundEventInfo(Supplier<SoundEvent> soundEvent, int soundDuration, float volume) {
        this.soundEvent = soundEvent;
        this.soundDuration = soundDuration;
        this.volume = volume;
    }

    public Supplier<SoundEvent> getSoundEvent() {
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
        this.volume = volume;
    }
}
