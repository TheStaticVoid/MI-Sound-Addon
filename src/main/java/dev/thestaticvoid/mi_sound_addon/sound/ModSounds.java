package dev.thestaticvoid.mi_sound_addon.sound;

import aztech.modern_industrialization.machines.MachineBlockEntity;
import aztech.modern_industrialization.machines.init.MIMachineRecipeTypes;
import aztech.modern_industrialization.machines.recipe.MachineRecipe;
import aztech.modern_industrialization.machines.recipe.MachineRecipeType;
import dev.thestaticvoid.mi_sound_addon.MISoundAddon;
import dev.thestaticvoid.mi_sound_addon.MISoundAddonConfig;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class ModSounds {
    public static HashMap<String, ModSoundEventInfo> SOUND_EVENTS;

    public static void initializeSounds() {
        MISoundAddon.LOGGER.debug("Registering sounds for " + MISoundAddon.MOD_ID);
        SOUND_EVENTS = new HashMap<>();
        if (MISoundAddonConfig.getConfig().enableSounds) {
            SoundEventRegistry.populateDefaultRecipeTypes(MIMachineRecipeTypes.getRecipeTypes());
        }
    }

    private static String getRecipeType(@NotNull MachineRecipe activeRecipe) {
        return ((MachineRecipeType) activeRecipe.getType()).getPath();
    }

    public static int getDuration(MachineRecipe activeRecipe) {
        return SOUND_EVENTS.get(getRecipeType(activeRecipe)).getSoundDuration();
    }

    public static void playSound(@NotNull MachineBlockEntity blockEntity, MachineRecipe activeRecipe) {
        Level world = blockEntity.getLevel();
        if (world == null) { return; }
        if (activeRecipe == null) { return; }

        ModSoundEventInfo soundEventInfo;

        if (!blockEntity.guiParams.blockId.equals("electric_blast_furnace")) {
            soundEventInfo = SOUND_EVENTS.get(getRecipeType(activeRecipe));
        } else {
            soundEventInfo = SOUND_EVENTS.get("electric_blast_furnace");
        }

        if (soundEventInfo.getSoundEvent() != null) {
            world.playSound(null, blockEntity.getBlockPos().getX(), blockEntity.getBlockPos().getY(),
                    blockEntity.getBlockPos().getZ(), soundEventInfo.getSoundEvent(), SoundSource.BLOCKS,
                    soundEventInfo.getVolume(), 1.0F);
        }
    }
}