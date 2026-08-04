package dev.thestaticvoid.mi_sound_addon.compat.kubejs.sound;

import aztech.modern_industrialization.MI;
import dev.latvian.mods.kubejs.event.KubeEvent;
import dev.thestaticvoid.mi_sound_addon.sound.ModSounds;
import net.minecraft.resources.ResourceLocation;

public class ModifySoundEventsJS implements KubeEvent {
    private static ResourceLocation toResourceLocation(String input) {
        return input.contains(":") ? ResourceLocation.parse(input) : MI.id(input);
    }

    public void modifyDuration(String recipeType, int duration) {
        ModSounds.setDuration(toResourceLocation(recipeType), duration);
    }

    public void modifyVolume(String recipeType, float volume) {
        ModSounds.setVolume(toResourceLocation(recipeType), volume);
    }

    public void registerGeneratorSound(String generatorId, int duration, float volume) {
        ModSounds.addSoundEvent(toResourceLocation(generatorId), volume, duration);
    }
}