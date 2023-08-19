package dev.thestaticvoid.mi_sound_addon.compat.kubejs.sound;

import dev.latvian.mods.kubejs.event.EventJS;
import dev.thestaticvoid.mi_sound_addon.sound.ModSounds;

public class AddSoundsEventJS extends EventJS {
    public void register(String recipeType, int duration, float volume) {
        ModSounds.addSoundEvent(recipeType, duration, volume);
    }
}
