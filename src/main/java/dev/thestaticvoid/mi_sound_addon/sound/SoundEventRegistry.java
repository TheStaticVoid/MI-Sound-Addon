package dev.thestaticvoid.mi_sound_addon.sound;

import aztech.modern_industrialization.machines.recipe.MachineRecipeType;
import dev.thestaticvoid.mi_sound_addon.MISoundAddon;
import dev.thestaticvoid.mi_sound_addon.MISoundAddonConfig;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

import java.util.List;

public class SoundEventRegistry {
    private static final int DEFAULT_SOUND_DURATION = 60;

    public static void populateDefaultRecipeTypes(List<MachineRecipeType> machineRecipeTypes) {
        for (MachineRecipeType mrt : machineRecipeTypes) {
            String type = mrt.getPath();
            addSoundEvent(type);
        }

        addSoundEvent("electric_blast_furnace");
        addSoundEvent("fission_reactor");
        addSoundEvent("wrench");
        updateDurations();
        updateVolumes();
    }

    public static void setDuration(String type, int duration) {
        if (ModSounds.SOUND_EVENTS.containsKey(type)) {
            ModSounds.SOUND_EVENTS.get(type).setSoundDuration(duration);
        } else {
            throw new IllegalStateException("Tried to set duration of non-existent recipe type");
        }
    }

    public static void setVolume(String type, float volume) {
        if (ModSounds.SOUND_EVENTS.containsKey(type)) {
            ModSounds.SOUND_EVENTS.get(type).setVolume(volume);
        } else {
            throw new IllegalStateException("Tried to set volume of non-existent recipe type");
        }
    }

    public static void addSoundEvent(String type) {
        addSoundEvent(type, 1.0f);
    }

    public static void addSoundEvent(String type, float volume) {
        addSoundEvent(type, volume, DEFAULT_SOUND_DURATION);
    }

    public static void addSoundEvent(String type, float volume, int duration) {
        ResourceLocation identifier = new ResourceLocation(MISoundAddon.MOD_ID, type);
        SoundEvent newSoundEvent = Registry.register(BuiltInRegistries.SOUND_EVENT, identifier,
                SoundEvent.createVariableRangeEvent(identifier));

        ModSounds.SOUND_EVENTS.put(type, new ModSoundEventInfo(newSoundEvent, duration, volume));
    }

    private static void updateDurations() {
        setDuration("assembler", 39);
        setDuration("blast_furnace", 31);
        setDuration("centrifuge", 39);
        setDuration("chemical_reactor", 80);
        setDuration("coke_oven", 33);
        setDuration("compressor", 30);
        setDuration("cutting_machine", 68);
        setDuration("distillation_tower", 110);
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
        setDuration("oil_drilling_rig", 33);
        setDuration("packer", 23);
        setDuration("polarizer", 35);
        setDuration("pressurizer", 89);
        setDuration("quarry", 61);
        setDuration("unpacker", 23);
        setDuration("vacuum_freezer", 62);
        setDuration("wiremill", 45);
    }

    private static void updateVolumes() {
        MISoundAddonConfig config = MISoundAddonConfig.getConfig();
        setVolume("assembler", config.assemblerVolume);
        setVolume("blast_furnace", config.blastFurnaceVolume);
        setVolume("centrifuge", config.centrifugeVolume);
        setVolume("chemical_reactor", config.chemicalReactorVolume);
        setVolume("coke_oven", config.cokeOvenVolume);
        setVolume("compressor", config.compressorVolume);
        setVolume("cutting_machine", config.cuttingMachineVolume);
        setVolume("distillation_tower", config.distillationTowerVolume);
        setVolume("distillery", config.distilleryVolume);
        setVolume("electric_blast_furnace", config.electricBlastFurnaceVolume);
        setVolume("electrolyzer", config.electrolyzerVolume);
        setVolume("fission_reactor", config.fissionReactorVolume);
        setVolume("fusion_reactor", config.fusionReactorVolume);
        setVolume("furnace", config.furnaceVolume);
        setVolume("heat_exchanger", config.heatExchangerVolume);
        setVolume("implosion_compressor", config.implosionCompressorVolume);
        setVolume("macerator", config.maceratorVolume);
        setVolume("mixer", config.mixerVolume);
        setVolume("oil_drilling_rig", config.oilDrillingRigVolume);
        setVolume("packer", config.packerVolume);
        setVolume("polarizer", config.polarizerVolume);
        setVolume("pressurizer", config.pressurizerVolume);
        setVolume("quarry", config.quarryVolume);
        setVolume("unpacker", config.unpackerVolume);
        setVolume("vacuum_freezer", config.vacuumFreezerVolume);
        setVolume("wiremill", config.wiremillVolume);
        setVolume("wrench", config.wrenchVolume);
    }
}
