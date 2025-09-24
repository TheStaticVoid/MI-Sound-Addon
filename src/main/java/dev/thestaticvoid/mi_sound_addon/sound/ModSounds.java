package dev.thestaticvoid.mi_sound_addon.sound;

import aztech.modern_industrialization.machines.MachineBlockEntity;
import aztech.modern_industrialization.machines.init.MIMachineRecipeTypes;
import aztech.modern_industrialization.machines.recipe.MachineRecipe;
import aztech.modern_industrialization.machines.recipe.MachineRecipeType;
import dev.thestaticvoid.mi_sound_addon.MISoundAddon;
import dev.thestaticvoid.mi_sound_addon.MISoundAddonConfig;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.function.Supplier;

public class ModSounds {
    private static final DeferredRegister<SoundEvent> SOUND_EVENTS_REGISTRY = DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, MISoundAddon.MOD_ID);
    public static HashMap<String, ModSoundEventInfo> SOUND_EVENTS = new HashMap<>();

    private static final int DEFAULT_SOUND_DURATION = 60; // measured in ticks

    public static void init(IEventBus bus) {
        MISoundAddon.LOGGER.debug("Registering sounds for " + MISoundAddon.MOD_ID);
        populateDefaultRecipeTypes(MIMachineRecipeTypes.getRecipeTypes());
        for (MachineRecipeType mrt : MIMachineRecipeTypes.getRecipeTypes()) {
            MISoundAddon.LOGGER.debug(mrt.getPath());
        }
        SOUND_EVENTS_REGISTRY.register(bus);
    }

    public static void populateDefaultRecipeTypes(List<MachineRecipeType> machineRecipeTypes) {
        for (MachineRecipeType mrt : machineRecipeTypes) {
            String type = mrt.getPath();
            if (type.equals("forge_hammer")) continue;
            addSoundEvent(type);
        }

        addSoundEvent("electric_blast_furnace");
        addSoundEvent("fission_reactor");
        addSoundEvent("wrench");
        addSoundEvent("replicator");
        updateDurations();
    }

    public static void addSoundEvent(String type) {
        addSoundEvent(type, 1.0F);
    }

    public static void addSoundEvent(String type, float volume) {
        addSoundEvent(type, volume, DEFAULT_SOUND_DURATION);
    }

    public static void addSoundEvent(String type, float volume, int duration) {
        ResourceLocation identifier = MISoundAddon.id(type);
        Supplier<SoundEvent> soundEvent = SOUND_EVENTS_REGISTRY.register(type, () -> SoundEvent.createVariableRangeEvent(identifier));
        SOUND_EVENTS.put(type, new ModSoundEventInfo(soundEvent, duration, volume));
    }

    private static String getRecipeType(@NotNull MachineRecipe activeRecipe) {
        return ((MachineRecipeType) activeRecipe.getType()).getPath();
    }

    public static int getDuration(MachineRecipe activeRecipe) {
        // getSoundDuration will cause a crash if the recipe is added via addon
        if (SOUND_EVENTS.get(getRecipeType(activeRecipe)) != null) {
            return SOUND_EVENTS.get(getRecipeType(activeRecipe)).getSoundDuration();
        } else {
            return DEFAULT_SOUND_DURATION;
        }
    }

    public static int getDurationFromString(String type) {
        return SOUND_EVENTS.get(type).getSoundDuration();
    }

    public static void setDuration(String type, int duration) {
        if (ModSounds.SOUND_EVENTS.containsKey(type)) {
            ModSounds.SOUND_EVENTS.get(type).setSoundDuration(duration);
        } else {
            throw new IllegalStateException("Tried to set duration of non-existent recipe type: " + type);
        }
    }

    public static void setVolume(String type, float volume) {
        if (ModSounds.SOUND_EVENTS.containsKey(type)) {
            ModSounds.SOUND_EVENTS.get(type).setVolume(volume);
        } else {
            throw new IllegalStateException("Tried to set volume of non-existent recipe type: " + type);
        }
    }

    public static void playSound(@NotNull MachineBlockEntity blockEntity, MachineRecipe activeRecipe) {
        Level world = blockEntity.getLevel();
        if (world == null) { return; }
        if (activeRecipe == null) { return; }

        ModSoundEventInfo soundEventInfo;

        if (!blockEntity.guiParams.blockId.getPath().equals("electric_blast_furnace")) {
            soundEventInfo = SOUND_EVENTS.get(getRecipeType(activeRecipe));
        } else {
            soundEventInfo = SOUND_EVENTS.get("electric_blast_furnace");
        }

        if (soundEventInfo != null && soundEventInfo.getSoundEvent() != null) {
            world.playSound(null, blockEntity.getBlockPos().getX(), blockEntity.getBlockPos().getY(),
                    blockEntity.getBlockPos().getZ(), soundEventInfo.getSoundEvent().get(), SoundSource.BLOCKS,
                    soundEventInfo.getVolume(), 1.0F);
        }
    }

    public static void playSoundNoRecipe(@NotNull MachineBlockEntity blockEntity, String type) {
        Level world = blockEntity.getLevel();
        if (world == null) { return; }

        ModSoundEventInfo soundEventInfo = SOUND_EVENTS.get(type);
        if (soundEventInfo.getSoundEvent() != null) {
            world.playSound(null, blockEntity.getBlockPos().getX(), blockEntity.getBlockPos().getY(),
                    blockEntity.getBlockPos().getZ(), soundEventInfo.getSoundEvent().get(), SoundSource.BLOCKS,
                    soundEventInfo.getVolume(), 1.0F);
        }
    }

    private static void updateDurations() {
        setDuration("assembler", 39);
        setDuration("blast_furnace", 31);
        setDuration("centrifuge", 39);
        setDuration("chemical_reactor", 80);
        setDuration("coke_oven", 34);
        setDuration("compressor", 30);
        setDuration("cutting_machine", 68);
        setDuration("distillation_tower", 109);
        setDuration("distillery", 71);
        setDuration("electric_blast_furnace", 61);
        setDuration("electrolyzer", 7);
        setDuration("fission_reactor", 60);
        setDuration("fusion_reactor", 67);
        setDuration("furnace", 38);
        setDuration("heat_exchanger", 71);
        setDuration("implosion_compressor", 70);
        setDuration("macerator", 16);
        setDuration("mixer", 27);
        setDuration("oil_drilling_rig", 29);
        setDuration("packer", 23);
        setDuration("polarizer", 29);
        setDuration("pressurizer", 89);
        setDuration("quarry", 61);
        setDuration("unpacker", 23);
        setDuration("vacuum_freezer", 62);
        setDuration("wiremill", 45);
        setDuration("replicator", 20);
    }

    public static void updateVolumes() {
        setVolume("assembler", (float) MISoundAddonConfig.assemblerVolume);
        setVolume("blast_furnace", (float)MISoundAddonConfig.blastFurnaceVolume);
        setVolume("centrifuge", (float)MISoundAddonConfig.centrifugeVolume);
        setVolume("chemical_reactor", (float)MISoundAddonConfig.chemicalReactorVolume);
        setVolume("coke_oven", (float) MISoundAddonConfig.cokeOvenVolume);
        setVolume("compressor", (float)MISoundAddonConfig.compressorVolume);
        setVolume("cutting_machine", (float)MISoundAddonConfig.cuttingMachineVolume);
        setVolume("distillation_tower", (float)MISoundAddonConfig.distillationTowerVolume);
        setVolume("distillery", (float)MISoundAddonConfig.distilleryVolume);
        setVolume("electric_blast_furnace", (float)MISoundAddonConfig.electricBlastFurnaceVolume);
        setVolume("electrolyzer", (float)MISoundAddonConfig.electrolyzerVolume);
        setVolume("fission_reactor", (float)MISoundAddonConfig.fissionReactorVolume);
        setVolume("fusion_reactor", (float)MISoundAddonConfig.fusionReactorVolume);
        setVolume("furnace", (float)MISoundAddonConfig.furnaceVolume);
        setVolume("heat_exchanger", (float)MISoundAddonConfig.heatExchangerVolume);
        setVolume("implosion_compressor", (float)MISoundAddonConfig.implosionCompressorVolume);
        setVolume("macerator", (float)MISoundAddonConfig.maceratorVolume);
        setVolume("mixer", (float)MISoundAddonConfig.mixerVolume);
        setVolume("oil_drilling_rig", (float)MISoundAddonConfig.oilDrillingRigVolume);
        setVolume("packer", (float)MISoundAddonConfig.packerVolume);
        setVolume("polarizer", (float)MISoundAddonConfig.polarizerVolume);
        setVolume("pressurizer", (float)MISoundAddonConfig.pressurizerVolume);
        setVolume("quarry", (float)MISoundAddonConfig.quarryVolume);
        setVolume("unpacker", (float)MISoundAddonConfig.unpackerVolume);
        setVolume("vacuum_freezer", (float)MISoundAddonConfig.vacuumFreezerVolume);
        setVolume("wiremill", (float)MISoundAddonConfig.wiremillVolume);
        setVolume("wrench", (float)MISoundAddonConfig.wrenchVolume);
        setVolume("replicator", (float)MISoundAddonConfig.replicatorVolume);
    }
}
