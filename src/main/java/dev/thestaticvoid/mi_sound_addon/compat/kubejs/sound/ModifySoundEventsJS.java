package dev.thestaticvoid.mi_sound_addon.compat.kubejs.sound;

import dev.latvian.mods.kubejs.event.EventJS;
import dev.thestaticvoid.mi_sound_addon.sound.SoundEventRegistry;

public class ModifySoundEventsJS extends EventJS {
    public void modifyDuration(String recipeType, int duration) {
        SoundEventRegistry.setDuration(recipeType, duration);
    }

    public void modifyVolume(String recipeType, float volume) {
        SoundEventRegistry.setVolume(recipeType, volume);
    }
}
