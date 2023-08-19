package dev.thestaticvoid.mi_sound_addon.sound;

import aztech.modern_industrialization.machines.init.MIMachineRecipeTypes;
import aztech.modern_industrialization.machines.recipe.MachineRecipe;
import aztech.modern_industrialization.machines.recipe.MachineRecipeType;
import dev.thestaticvoid.mi_sound_addon.MISoundAddon;
import dev.thestaticvoid.mi_sound_addon.MISoundAddonConfig;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;

public class ModSounds {

    public static HashMap<String, ModSoundEventInfo> machineRecipeTypeSoundEvents;
    private static final int DEFAULT_SOUND_DURATION = 60;


    public static void initializeSounds() {
        MISoundAddon.LOGGER.debug("Registering sounds for " + MISoundAddon.MOD_ID);
        machineRecipeTypeSoundEvents = new HashMap<>();
        if (MISoundAddonConfig.getConfig().enableSounds) {
            populateDefaultRecipeTypes(MIMachineRecipeTypes.getRecipeTypes());
        }
    }

    public static void playSound(@NotNull BlockEntity blockEntity, MachineRecipe activeRecipe) {
        Level world = blockEntity.getLevel();
        if (world == null) { return; }

        ModSoundEventInfo soundEventInfo = machineRecipeTypeSoundEvents.get(getRecipeType(activeRecipe));

        if (soundEventInfo.getSoundEvent() != null) {
            world.playSound(null, blockEntity.getBlockPos().getX(), blockEntity.getBlockPos().getY(),
                    blockEntity.getBlockPos().getZ(), soundEventInfo.getSoundEvent(), SoundSource.BLOCKS,
                    soundEventInfo.getVolume(), 1.0F);
        }
    }

    public static int getSoundDuration(MachineRecipe activeRecipe) {
        return machineRecipeTypeSoundEvents.get(getRecipeType(activeRecipe)).getSoundDuration();
    }

    private static String getRecipeType(@NotNull MachineRecipe activeRecipe) {
        return ((MachineRecipeType) activeRecipe.getType()).getPath();
    }

    private static void populateDefaultRecipeTypes(@NotNull List<MachineRecipeType> machineRecipeTypes) {
        for (MachineRecipeType mrt : machineRecipeTypes) {
            String type = mrt.getPath();

            // forge hammer isn't a machine :P
            if (type.equals("forge_hammer")) continue;

            addSoundEvent(type);
        }
        // TODO fission reactor
        updateDurations();
        updateVolumes();
    }

    public static void addSoundEvent(String type) {
        addSoundEvent(type, 1.0f);
    }

    public static void addSoundEvent(String type, float volume) {
        addSoundEvent(type, DEFAULT_SOUND_DURATION, volume);
    }

    public static void addSoundEvent(String type, int duration, float volume) {
        SoundEvent newSoundEvent = Registry.register(Registry.SOUND_EVENT,
                new ResourceLocation(MISoundAddon.MOD_ID, type),
                new SoundEvent(new ResourceLocation(MISoundAddon.MOD_ID, type)));

        machineRecipeTypeSoundEvents.put(type, new ModSoundEventInfo(newSoundEvent, duration, volume));
        MISoundAddon.LOGGER.debug("Registered SoundEvent for RecipeType: " + type);
    }

    private static void updateDurations() {
        // Values are calculated by hand here
        // I would prefer if they weren't, but I just haven't gotten there yet
        machineRecipeTypeSoundEvents.get("furnace").setSoundDuration(64);
        machineRecipeTypeSoundEvents.get("fusion_reactor").setSoundDuration(68);
        machineRecipeTypeSoundEvents.get("mixer").setSoundDuration(38);
    }

    private static void updateVolumes() {
        MISoundAddonConfig config = MISoundAddonConfig.getConfig();
        machineRecipeTypeSoundEvents.get("assembler").setVolume(config.assemblerVolume);
        machineRecipeTypeSoundEvents.get("centrifuge").setVolume(config.centrifugeVolume);
        machineRecipeTypeSoundEvents.get("chemical_reactor").setVolume(config.chemicalReactorVolume);
        machineRecipeTypeSoundEvents.get("coke_oven").setVolume(config.cokeOvenVolume);
        machineRecipeTypeSoundEvents.get("compressor").setVolume(config.compressorVolume);
        machineRecipeTypeSoundEvents.get("cutting_machine").setVolume(config.cuttingMachineVolume);
        machineRecipeTypeSoundEvents.get("distillation_tower").setVolume(config.distillationTowerVolume);
        machineRecipeTypeSoundEvents.get("electrolyzer").setVolume(config.electrolyzerVolume);
        machineRecipeTypeSoundEvents.get("fusion_reactor").setVolume(config.fusionReactorVolume);
        machineRecipeTypeSoundEvents.get("furnace").setVolume(config.furnaceVolume);
        machineRecipeTypeSoundEvents.get("implosion_compressor").setVolume(config.implosionCompressorVolume);
        machineRecipeTypeSoundEvents.get("macerator").setVolume(config.maceratorVolume);
        machineRecipeTypeSoundEvents.get("mixer").setVolume(config.mixerVolume);
        machineRecipeTypeSoundEvents.get("oil_drilling_rig").setVolume(config.oilDrillingRigVolume);
        machineRecipeTypeSoundEvents.get("packer").setVolume(config.packerVolume);
        machineRecipeTypeSoundEvents.get("polarizer").setVolume(config.polarizerVolume);
        machineRecipeTypeSoundEvents.get("pressurizer").setVolume(config.pressurizerVolume);
        machineRecipeTypeSoundEvents.get("quarry").setVolume(config.pressurizerVolume);
        machineRecipeTypeSoundEvents.get("unpacker").setVolume(config.unpackerVolume);
        machineRecipeTypeSoundEvents.get("vacuum_freezer").setVolume(config.vacuumFreezerVolume);
        machineRecipeTypeSoundEvents.get("wiremill").setVolume(config.wiremillVolume);
    }
}