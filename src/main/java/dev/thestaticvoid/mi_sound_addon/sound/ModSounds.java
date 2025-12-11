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
        setVolume("assembler", (float)MISoundAddonConfig.CONFIG.assemblerVolume.get().doubleValue());
        setVolume("blast_furnace", (float)MISoundAddonConfig.CONFIG.blastFurnaceVolume.get().doubleValue());
        setVolume("centrifuge", (float)MISoundAddonConfig.CONFIG.centrifugeVolume.get().doubleValue());
        setVolume("chemical_reactor", (float)MISoundAddonConfig.CONFIG.chemicalReactorVolume.get().doubleValue());
        setVolume("coke_oven", (float) MISoundAddonConfig.CONFIG.cokeOvenVolume.get().doubleValue());
        setVolume("compressor", (float)MISoundAddonConfig.CONFIG.compressorVolume.get().doubleValue());
        setVolume("cutting_machine", (float)MISoundAddonConfig.CONFIG.cuttingMachineVolume.get().doubleValue());
        setVolume("distillation_tower", (float)MISoundAddonConfig.CONFIG.distillationTowerVolume.get().doubleValue());
        setVolume("distillery", (float)MISoundAddonConfig.CONFIG.distilleryVolume.get().doubleValue());
        setVolume("electric_blast_furnace", (float)MISoundAddonConfig.CONFIG.electricBlastFurnaceVolume.get().doubleValue());
        setVolume("electrolyzer", (float)MISoundAddonConfig.CONFIG.electrolyzerVolume.get().doubleValue());
        setVolume("fission_reactor", (float)MISoundAddonConfig.CONFIG.fissionReactorVolume.get().doubleValue());
        setVolume("fusion_reactor", (float)MISoundAddonConfig.CONFIG.fusionReactorVolume.get().doubleValue());
        setVolume("furnace", (float)MISoundAddonConfig.CONFIG.furnaceVolume.get().doubleValue());
        setVolume("heat_exchanger", (float)MISoundAddonConfig.CONFIG.heatExchangerVolume.get().doubleValue());
        setVolume("implosion_compressor", (float)MISoundAddonConfig.CONFIG.implosionCompressorVolume.get().doubleValue());
        setVolume("macerator", (float)MISoundAddonConfig.CONFIG.maceratorVolume.get().doubleValue());
        setVolume("mixer", (float)MISoundAddonConfig.CONFIG.mixerVolume.get().doubleValue());
        setVolume("oil_drilling_rig", (float)MISoundAddonConfig.CONFIG.oilDrillingRigVolume.get().doubleValue());
        setVolume("packer", (float)MISoundAddonConfig.CONFIG.packerVolume.get().doubleValue());
        setVolume("polarizer", (float)MISoundAddonConfig.CONFIG.polarizerVolume.get().doubleValue());
        setVolume("pressurizer", (float)MISoundAddonConfig.CONFIG.pressurizerVolume.get().doubleValue());
        setVolume("quarry", (float)MISoundAddonConfig.CONFIG.quarryVolume.get().doubleValue());
        setVolume("unpacker", (float)MISoundAddonConfig.CONFIG.unpackerVolume.get().doubleValue());
        setVolume("vacuum_freezer", (float)MISoundAddonConfig.CONFIG.vacuumFreezerVolume.get().doubleValue());
        setVolume("wiremill", (float)MISoundAddonConfig.CONFIG.wiremillVolume.get().doubleValue());
        setVolume("wrench", (float)MISoundAddonConfig.CONFIG.wrenchVolume.get().doubleValue());
        setVolume("replicator", (float)MISoundAddonConfig.CONFIG.replicatorVolume.get().doubleValue());
    }
}
