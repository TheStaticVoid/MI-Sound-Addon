package dev.thestaticvoid.mi_sound_addon.sound;

import net.minecraft.sounds.SoundEvent;

import java.util.function.Supplier;

public record ModSoundEvent(Supplier<SoundEvent> event, float volume) {
    public ModSoundEvent(Supplier<SoundEvent> event, float volume) {
        this.event = event;

        if (volume < 0.0f || volume > 5.0f) {
            throw new IllegalStateException(
                    "Volume was set out of range. Value: %s for event %s".formatted(volume, event.get().getLocation())
            );
        }
        this.volume = volume;
    }
}
