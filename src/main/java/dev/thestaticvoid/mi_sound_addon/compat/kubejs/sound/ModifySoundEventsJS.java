package dev.thestaticvoid.mi_sound_addon.compat.kubejs.sound;

import dev.latvian.mods.kubejs.event.EventJS;
import dev.thestaticvoid.mi_sound_addon.sound.ModSounds;

public class ModifySoundEventsJS extends EventJS {
    public void modifyDuration(String recipeType, int duration) {
        ModSounds.setDuration(recipeType, duration);
    }

    public void modifyVolume(String recipeType, float volume) {
        ModSounds.setVolume(recipeType, volume);
    }
}
