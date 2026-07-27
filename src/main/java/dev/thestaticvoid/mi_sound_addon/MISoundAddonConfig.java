package dev.thestaticvoid.mi_sound_addon;

import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public final class MISoundAddonConfig {
    public static final MISoundAddonConfig CONFIG;
    public static final ModConfigSpec CONFIG_SPEC;

    static {
        Pair<MISoundAddonConfig, ModConfigSpec> pair = new ModConfigSpec.Builder().configure(MISoundAddonConfig::new);

        CONFIG = pair.getLeft();
        CONFIG_SPEC = pair.getRight();
    }

    public final ModConfigSpec.ConfigValue<Boolean> machineSoundsEnabled;
    public final ModConfigSpec.ConfigValue<Boolean> wrenchSoundsEnabled;
    public final ModConfigSpec.ConfigValue<Boolean> configCardSoundsEnabled;
    public final ModConfigSpec.ConfigValue<Double> assemblerVolume;
    public final ModConfigSpec.ConfigValue<Double> blastFurnaceVolume;
    public final ModConfigSpec.ConfigValue<Double> centrifugeVolume;
    public final ModConfigSpec.ConfigValue<Double> chemicalReactorVolume;
    public final ModConfigSpec.ConfigValue<Double> cokeOvenVolume;
    public final ModConfigSpec.ConfigValue<Double> compressorVolume;
    public final ModConfigSpec.ConfigValue<Double> cuttingMachineVolume;
    public final ModConfigSpec.ConfigValue<Double> distillationTowerVolume;
    public final ModConfigSpec.ConfigValue<Double> distilleryVolume;
    public final ModConfigSpec.ConfigValue<Double> electricBlastFurnaceVolume;
    public final ModConfigSpec.ConfigValue<Double> electrolyzerVolume;
    public final ModConfigSpec.ConfigValue<Double> fissionReactorVolume;
    public final ModConfigSpec.ConfigValue<Double> fusionReactorVolume;
    public final ModConfigSpec.ConfigValue<Double> furnaceVolume;
    public final ModConfigSpec.ConfigValue<Double> heatExchangerVolume;
    public final ModConfigSpec.ConfigValue<Double> implosionCompressorVolume;
    public final ModConfigSpec.ConfigValue<Double> maceratorVolume;
    public final ModConfigSpec.ConfigValue<Double> mixerVolume;
    public final ModConfigSpec.ConfigValue<Double> oilDrillingRigVolume;
    public final ModConfigSpec.ConfigValue<Double> packerVolume;
    public final ModConfigSpec.ConfigValue<Double> polarizerVolume;
    public final ModConfigSpec.ConfigValue<Double> pressurizerVolume;
    public final ModConfigSpec.ConfigValue<Double> quarryVolume;
    public final ModConfigSpec.ConfigValue<Double> unpackerVolume;
    public final ModConfigSpec.ConfigValue<Double> vacuumFreezerVolume;
    public final ModConfigSpec.ConfigValue<Double> wiremillVolume;
    public final ModConfigSpec.ConfigValue<Double> wrenchVolume;
    public final ModConfigSpec.ConfigValue<Double> configCardVolume;
    public final ModConfigSpec.ConfigValue<Double> replicatorVolume;
    public final ModConfigSpec.ConfigValue<Double> composterVolume;
    public final ModConfigSpec.ConfigValue<Double> alloySmelterVolume;
    public final ModConfigSpec.ConfigValue<Double> bendingMachineVolume;
    public final ModConfigSpec.ConfigValue<Double> canningMachineVolume;
    public final ModConfigSpec.ConfigValue<Double> teslaCoilVolume;
    public final ModConfigSpec.ConfigValue<Double> teslaTowerVolume;
    public final ModConfigSpec.ConfigValue<Double> pyrolyseOvenVolume;

    private MISoundAddonConfig(ModConfigSpec.Builder builder) {
        machineSoundsEnabled = builder.define("machine_sounds_enabled", true);
        wrenchSoundsEnabled = builder.define("wrench_sounds_enabled", true);
        configCardSoundsEnabled = builder.define("config_card_sounds_enabled", true);
        assemblerVolume = builder
                .comment("Assembler Volume")
                .defineInRange("assembler_volume", 1.0, 0.0, 5.0);
        blastFurnaceVolume = builder
                .comment("Blast Furnace Volume")
                .defineInRange("blast_furnace_volume", 1.0, 0.0, 5.0);
        centrifugeVolume = builder
                .comment("Centrifuge Volume")
                .defineInRange("centrifuge_volume", 1.0, 0.0, 5.0);
        chemicalReactorVolume = builder
                .comment("Chemical Reactor Volume")
                .defineInRange("chemical_reactor_volume", 1.0, 0.0 ,5.0);
        cokeOvenVolume = builder
                .comment("Coke Oven Volume")
                .defineInRange("coke_oven_volume", 1.0, 0.0, 5.0);
        compressorVolume = builder
                .comment("Compressor Volume")
                .defineInRange("compressor_volume", 1.0, 0.0, 5.0);
        cuttingMachineVolume = builder
                .comment("Cutting Machine Volume")
                .defineInRange("cutting_machine_volume", 1.0, 0.0, 5.0);
        distillationTowerVolume = builder
                .comment("Distillation Tower Volume")
                .defineInRange("distillation_tower_volume", 1.0, 0.0, 5.0);
        distilleryVolume = builder
                .comment("Distillery Volume")
                .defineInRange("distillery_volume", 1.0, 0.0, 5.0);
        electricBlastFurnaceVolume = builder
                .comment("EBF Volume")
                .defineInRange("electric_blast_furnace_volume", 1.0, 0.0, 5.0);
        electrolyzerVolume = builder
                .comment("Electrolyzer Volume")
                .defineInRange("electrolyzer_volume", 1.0, 0.0, 5.0);
        fissionReactorVolume = builder
                .comment("Fission Reactor Volume")
                .defineInRange("fission_reactor_volume", 1.0, 0.0, 5.0);
        fusionReactorVolume = builder
                .comment("Fusion Reactor Volume")
                .defineInRange("fusion_reactor_volume", 1.0, 0.0, 5.0);
        furnaceVolume = builder
                .comment("Furnace Volume")
                .defineInRange("furnace_volume", 1.0, 0.0, 5.0);
        heatExchangerVolume = builder
                .comment("Heat Exchanger Volume")
                .defineInRange("heat_exchanger_volume", 1.0, 0.0, 5.0);
        implosionCompressorVolume = builder
                .comment("Implosion Compressor Volume")
                .defineInRange("implosion_compressor_volume", 1.0, 0.0, 5.0);
        maceratorVolume = builder
                .comment("Macerator Volume")
                .defineInRange("macerator_volume", 1.0, 0.0, 5.0);
        mixerVolume = builder
                .comment("Mixer Volume")
                .defineInRange("mixer_volume", 1.0, 0.0, 5.0);
        oilDrillingRigVolume = builder
                .comment("Oil Drilling Rig Volume")
                .defineInRange("oil_drilling_rig_volume", 1.0, 0.0, 5.0);
        packerVolume = builder
                .comment("Packer Volume")
                .defineInRange("packer_volume", 1.0, 0.0, 5.0);
        polarizerVolume = builder
                .comment("Polarizer Volume")
                .defineInRange("polarizer_volume", 1.0, 0.0, 5.0);
        pressurizerVolume = builder
                .comment("Pressurizer Volume")
                .defineInRange("pressurizer_volume", 1.0, 0.0, 5.0);
        quarryVolume = builder
                .comment("Quarry Volume")
                .defineInRange("quarry_volume", 1.0, 0.0, 5.0);
        unpackerVolume = builder
                .comment("Unpacker Volume")
                .defineInRange("unpacker_volume", 1.0, 0.0, 5.0);
        vacuumFreezerVolume = builder
                .comment("Vacuum Freezer Volume")
                .defineInRange("vacuum_freezer_volume", 1.0, 0.0, 5.0);
        wiremillVolume = builder
                .comment("Wiremill Volume")
                .defineInRange("wiremill_volume", 1.0, 0.0, 5.0);
        wrenchVolume = builder
                .comment("Wrench Volume")
                .defineInRange("wrench_volume", 1.0, 0.0, 5.0);
        configCardVolume = builder
                .comment("Config Card Volume")
                .defineInRange("config_card_volume", 1.0, 0.0, 5.0);
        replicatorVolume = builder
                .comment("Replicator Volume")
                .defineInRange("replicator_volume", 1.0, 0.0, 5.0);
        composterVolume = builder
                .comment("Composter Volume (EI)")
                .defineInRange("composter_volume", 1.0, 0.0, 5.0);
        alloySmelterVolume = builder
                .comment("Alloy Smelter Volume (EI)")
                .defineInRange("alloy_smelter", 1.0, 0.0, 5.0);
        bendingMachineVolume = builder
                .comment("Bending Machine Volume (EI)")
                .defineInRange("bending_machine", 1.0, 0.0, 5.0);
        canningMachineVolume = builder
                .comment("Canning Machine Volume (EI)")
                .defineInRange("canning_machine", 1.0, 0.0, 5.0);
        teslaCoilVolume = builder
                .comment("Tesla Coil Volume (EI)")
                .defineInRange("tesla_coil", 1.0, 0.0, 5.0);
        teslaTowerVolume = builder
                .comment("Tesla Tower Volume (EI)")
                .defineInRange("tesla_tower", 1.0, 0.0, 5.0);
        pyrolyseOvenVolume = builder
                .comment("Pyrolyse Oven Volume (IO)")
                .defineInRange("pyrolyse_oven", 1.0, 0.0, 5.0);

        builder.build();
    }
}
