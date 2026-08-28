package dev.thestaticvoid.mi_sound_addon.sound;

import aztech.modern_industrialization.MI;
import aztech.modern_industrialization.machines.init.MIMachineRecipeTypes;
import dev.thestaticvoid.mi_sound_addon.MISAConfig;
import dev.thestaticvoid.mi_sound_addon.util.MISAUtil;
import dev.wp.industrialization_overdrive.IO;
import net.minecraft.resources.ResourceLocation;
import net.swedz.extended_industrialization.EI;
import net.swedz.extended_industrialization.EISounds;

public class DefaultSoundRegistry {
    private static void populateRecipeTypes() {
        var types = MIMachineRecipeTypes.getRecipeTypes();
        for (var type : types) {
            ResourceLocation location = type.getId();
            if (location.equals(MI.id("forge_hammer"))) continue;
            if (location.equals(MISAUtil.YAIResource("pulse_detonation_generator"))) continue;
            MISASound.addSoundEvent(location);
        }
    }

    private static void populateExtraTypes() {
        MISASound.addSoundEvent(MI.id("electric_blast_furnace"));
        MISASound.addSoundEvent(MI.id("fission_reactor"));
        MISASound.addSoundEvent(MI.id("wrench"));
        MISASound.addSoundEvent(MI.id("config_card"));
        MISASound.addSoundEvent(MI.id("replicator"));
        MISASound.addSoundEvent(MI.id("boiler"));
        MISASound.addSoundEvent(MI.id("turbine"));
        MISASound.addSoundEvent(MI.id("diesel"));
    }

    private static void populateEISounds() {
        MISASound.SOUND_EVENTS.put(
                EI.id("tesla.loop"),
                new ModSoundEvent(EISounds.TESLA_COIL_LOOP, 1.0f)
        );
        MISASound.addSoundEvent(EI.id("tesla_tower"));
        MISASound.addSoundEvent(EI.id("solar_boiler"));
    }

    private static void populateIOSounds() {

    }

    private static void populateYAISounds() {

    }

    public static void register() {
        populateRecipeTypes();
        populateExtraTypes();

        if (MISAUtil.checkModIsLoaded(MISAUtil.EIID)) {
            populateEISounds();
        }

        if (MISAUtil.checkModIsLoaded(MISAUtil.IOID)) {
            populateIOSounds();
        }

        if (MISAUtil.checkModIsLoaded(MISAUtil.YAIID)) {
            populateYAISounds();
        }
    }

    public static void updateVolumes() {
        // -- MODERN INDUSTRIALIZATION -- //
        updateVolume(MI.id("assembler"), MISAConfig.CONFIG.assemblerVolume.get());
        updateVolume(MI.id("blast_furnace"), MISAConfig.CONFIG.blastFurnaceVolume.get());
        updateVolume(MI.id("centrifuge"), MISAConfig.CONFIG.centrifugeVolume.get());
        updateVolume(MI.id("chemical_reactor"), MISAConfig.CONFIG.chemicalReactorVolume.get());
        updateVolume(MI.id("coke_oven"), MISAConfig.CONFIG.cokeOvenVolume.get());
        updateVolume(MI.id("compressor"), MISAConfig.CONFIG.compressorVolume.get());
        updateVolume(MI.id("cutting_machine"), MISAConfig.CONFIG.cuttingMachineVolume.get());
        updateVolume(MI.id("distillation_tower"), MISAConfig.CONFIG.distillationTowerVolume.get());
        updateVolume(MI.id("distillery"), MISAConfig.CONFIG.distilleryVolume.get());
        updateVolume(MI.id("electric_blast_furnace"), MISAConfig.CONFIG.electricBlastFurnaceVolume.get());
        updateVolume(MI.id("electrolyzer"), MISAConfig.CONFIG.electrolyzerVolume.get());
        updateVolume(MI.id("fission_reactor"), MISAConfig.CONFIG.fissionReactorVolume.get());
        updateVolume(MI.id("fusion_reactor"), MISAConfig.CONFIG.fusionReactorVolume.get());
        updateVolume(MI.id("furnace"), MISAConfig.CONFIG.furnaceVolume.get());
        updateVolume(MI.id("heat_exchanger"), MISAConfig.CONFIG.heatExchangerVolume.get());
        updateVolume(MI.id("implosion_compressor"), MISAConfig.CONFIG.implosionCompressorVolume.get());
        updateVolume(MI.id("macerator"), MISAConfig.CONFIG.maceratorVolume.get());
        updateVolume(MI.id("mixer"), MISAConfig.CONFIG.mixerVolume.get());
        updateVolume(MI.id("oil_drilling_rig"), MISAConfig.CONFIG.oilDrillingRigVolume.get());
        updateVolume(MI.id("packer"), MISAConfig.CONFIG.packerVolume.get());
        updateVolume(MI.id("polarizer"), MISAConfig.CONFIG.polarizerVolume.get());
        updateVolume(MI.id("pressurizer"), MISAConfig.CONFIG.pressurizerVolume.get());
        updateVolume(MI.id("quarry"), MISAConfig.CONFIG.quarryVolume.get());
        updateVolume(MI.id("unpacker"), MISAConfig.CONFIG.unpackerVolume.get());
        updateVolume(MI.id("vacuum_freezer"), MISAConfig.CONFIG.vacuumFreezerVolume.get());
        updateVolume(MI.id("wiremill"), MISAConfig.CONFIG.wiremillVolume.get());
        updateVolume(MI.id("wrench"), MISAConfig.CONFIG.wrenchVolume.get());
        updateVolume(MI.id("config_card"), MISAConfig.CONFIG.configCardVolume.get());
        updateVolume(MI.id("replicator"), MISAConfig.CONFIG.replicatorVolume.get());
        updateVolume(MI.id("boiler"), MISAConfig.CONFIG.boilerVolume.get());
        updateVolume(MI.id("turbine"), MISAConfig.CONFIG.turbineVolume.get());
        updateVolume(MI.id("diesel"), MISAConfig.CONFIG.dieselVolume.get());

        // -- EXTENDED INDUSTRIALIZATION -- //
        if (MISAUtil.checkModIsLoaded(MISAUtil.EIID)) {
            updateVolume(EI.id("composter"), MISAConfig.CONFIG.composterVolume.get());
            updateVolume(EI.id("alloy_smelter"), MISAConfig.CONFIG.alloySmelterVolume.get());
            updateVolume(EI.id("bending_machine"), MISAConfig.CONFIG.bendingMachineVolume.get());
            updateVolume(EI.id("canning_machine"), MISAConfig.CONFIG.canningMachineVolume.get());
            updateVolume(EI.id("tesla.loop"), MISAConfig.CONFIG.teslaCoilVolume.get());
            updateVolume(EI.id("tesla_tower"), MISAConfig.CONFIG.teslaTowerVolume.get());
            updateVolume(EI.id("solar_boiler"), MISAConfig.CONFIG.solarBoilerVolume.get());
        }

        // -- INDUSTRIALIZATION OVERDRIVE -- //
        if (MISAUtil.checkModIsLoaded(MISAUtil.IOID)) {
            updateVolume(IO.id("pyrolyse_oven"), MISAConfig.CONFIG.pyrolyseOvenVolume.get());
        }

        // -- YET ANOTHER INDUSTRIALIZATION -- //
        if (MISAUtil.checkModIsLoaded(MISAUtil.YAIID)) {
            updateVolume(MISAUtil.YAIResource("arboreous_greenhouse"), MISAConfig.CONFIG.arboreousGreenhouseVolume.get());
            updateVolume(MISAUtil.YAIResource("cryogenic_precipitator"), MISAConfig.CONFIG.cryogenicPrecipitatorVolume.get());
            updateVolume(MISAUtil.YAIResource("dragon_egg_energy_siphon"), MISAConfig.CONFIG.dragonEggEnergySiphonVolume.get());
        }
    }

    private static void updateVolume(ResourceLocation location, double value) {
        MISASound.setVolume(MISASound.createFormattedResourceLocation(location), (float) value);
    }
}
