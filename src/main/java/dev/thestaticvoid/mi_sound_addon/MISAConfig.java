package dev.thestaticvoid.mi_sound_addon;

import io.netty.util.Attribute;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;
import org.jspecify.annotations.Nullable;

public class MISAConfig {
    public static final MISAConfig CONFIG;
    public static final ModConfigSpec CONFIG_SPEC;

    // -- SOUND TOGGLES -- //
    public final ModConfigSpec.ConfigValue<Boolean> generatorSoundsEnabled;
    public final ModConfigSpec.ConfigValue<Boolean> machineSoundsEnabled;
    public final ModConfigSpec.ConfigValue<Boolean> wrenchSoundsEnabled;

    // -- TOOL SOUNDS -- //
    public final ModConfigSpec.ConfigValue<Double> wrenchVolume;
    public final ModConfigSpec.ConfigValue<Double> configCardVolume;

    // -- MACHINE SOUNDS -- //
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
    public final ModConfigSpec.ConfigValue<Double> replicatorVolume;
    public final ModConfigSpec.ConfigValue<Double> composterVolume;
    public final ModConfigSpec.ConfigValue<Double> alloySmelterVolume;
    public final ModConfigSpec.ConfigValue<Double> bendingMachineVolume;
    public final ModConfigSpec.ConfigValue<Double> canningMachineVolume;
    public final ModConfigSpec.ConfigValue<Double> teslaCoilVolume;
    public final ModConfigSpec.ConfigValue<Double> teslaTowerVolume;
    public final ModConfigSpec.ConfigValue<Double> pyrolyseOvenVolume;
    public final ModConfigSpec.ConfigValue<Double> arboreousGreenhouseVolume;
    public final ModConfigSpec.ConfigValue<Double> cryogenicPrecipitatorVolume;

    // -- GENERATOR SOUNDS -- //
    public final ModConfigSpec.ConfigValue<Double> turbineVolume;
    public final ModConfigSpec.ConfigValue<Double> dieselVolume;
    public final ModConfigSpec.ConfigValue<Double> boilerVolume;
    public final ModConfigSpec.ConfigValue<Double> solarBoilerVolume;
    public final ModConfigSpec.ConfigValue<Double> dragonEggEnergySiphonVolume;

    private MISAConfig(ModConfigSpec.Builder builder) {
        // -- ITEM SOUNDS -- //
        wrenchSoundsEnabled = builder
                .comment("Should wrench sounds be enabled")
                .define("wrench_sounds", true);

        wrenchVolume = builder
                .comment("Wrench Volume")
                .defineInRange("wrench_volume", 0.5, 0.0, 1.0);
        
        configCardVolume = createVolumeConfig(builder, "Config Card");

        // -- MACHINE SOUNDS -- //
        machineSoundsEnabled = builder
                .comment("Should machine sounds be enabled")
                .define("machine_sounds", true);

        assemblerVolume = createVolumeConfig(builder, "Assembler");
        blastFurnaceVolume = createVolumeConfig(builder, "Blast Furnace");
        centrifugeVolume = createVolumeConfig(builder, "Centrifuge");
        chemicalReactorVolume = createVolumeConfig(builder, "Chemical Reactor");
        cokeOvenVolume = createVolumeConfig(builder, "Coke Oven");
        compressorVolume = createVolumeConfig(builder, "Compressor");
        cuttingMachineVolume = createVolumeConfig(builder, "Cutting Machine");
        distillationTowerVolume = createVolumeConfig(builder, "Distillation Tower");
        distilleryVolume = createVolumeConfig(builder, "Distillery");
        electricBlastFurnaceVolume = createVolumeConfig(builder, "Electric Blast Furnace");
        electrolyzerVolume = createVolumeConfig(builder, "Electrolyzer Volume");
        fissionReactorVolume = createVolumeConfig(builder, "Fission Reactor");
        fusionReactorVolume = createVolumeConfig(builder, "Fusion Reactor");
        furnaceVolume = createVolumeConfig(builder, "Furnace");
        heatExchangerVolume = createVolumeConfig(builder, "Heat Exchanger");
        implosionCompressorVolume = createVolumeConfig(builder, "Implosion Compressor");
        maceratorVolume = createVolumeConfig(builder, "Macerator");
        mixerVolume = createVolumeConfig(builder, "Mixer");
        oilDrillingRigVolume = createVolumeConfig(builder, "Oil Drilling Rig");
        packerVolume = createVolumeConfig(builder, "Packer");
        polarizerVolume = createVolumeConfig(builder, "Polarizer");
        pressurizerVolume = createVolumeConfig(builder, "Pressurizer");
        quarryVolume = createVolumeConfig(builder,"Quarry");
        unpackerVolume = createVolumeConfig(builder, "Unpacker");
        vacuumFreezerVolume = createVolumeConfig(builder, "Vacuum Freezer");
        wiremillVolume = createVolumeConfig(builder, "Wiremill");
        replicatorVolume = createVolumeConfig(builder, "Replicator");
        composterVolume = createVolumeConfig(builder, "Compostor", "EI");
        alloySmelterVolume = createVolumeConfig(builder, "Alloy Smelter", "EI");
        bendingMachineVolume = createVolumeConfig(builder, "Bending Machine", "EI");
        canningMachineVolume = createVolumeConfig(builder, "Canning Machine", "EI");
        teslaCoilVolume = createVolumeConfig(builder, "Tesla Coil", "EI");
        teslaTowerVolume = createVolumeConfig(builder, "Tesla Tower", "EI");
        pyrolyseOvenVolume = createVolumeConfig(builder, "Pyrolyse Oven", "IO");
        arboreousGreenhouseVolume = createVolumeConfig(builder, "Arboreous Greenhouse", "YAI");
        cryogenicPrecipitatorVolume = createVolumeConfig(builder, "Cryogenic Precipitator", "YAI");

        // -- GENERATOR SOUNDS -- //
        generatorSoundsEnabled = builder
                .comment("Should generator sounds be enabled")
                .define("generator_sounds", true);

        turbineVolume = createVolumeConfig(builder, "Turbine");
        dieselVolume = createVolumeConfig(builder, "Diesel");
        boilerVolume = createVolumeConfig(builder, "Boiler");
        solarBoilerVolume = createVolumeConfig(builder, "Solar Boiler", "EI");
        dragonEggEnergySiphonVolume = createVolumeConfig(builder, "Dragon Egg Energy Siphon", "YAI");

        builder.build();
    }

    private ModConfigSpec.ConfigValue<Double> createVolumeConfig(ModConfigSpec.Builder builder, String machineName) {
        return createVolumeConfig(builder, machineName, null);
    }

    private ModConfigSpec.ConfigValue<Double> createVolumeConfig(ModConfigSpec.Builder builder, String machineName, String mod) {
        String snakeCase = machineName.toLowerCase().replace(" ", "_");
        String comment;
        if (mod != null) {
            comment = String.format("%s Volume (%s)", machineName, mod);
        } else {
            comment = String.format("%s Volume", machineName);
        }

        return builder.comment(comment).gameRestart().defineInRange(snakeCase, 1.0, 0.0, 1.0);
    }

    static {
        Pair<MISAConfig, ModConfigSpec> pair =
                new ModConfigSpec.Builder().configure(MISAConfig::new);
        CONFIG = pair.getLeft();
        CONFIG_SPEC = pair.getRight();
    }
}
