package dev.thestaticvoid.mi_sound_addon;

import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class MISAConfig {
    public static final MISAConfig CONFIG;
    public static final ModConfigSpec CONFIG_SPEC;

    public final ModConfigSpec.ConfigValue<Boolean> wrenchSoundsEnabled;
    public final ModConfigSpec.ConfigValue<Double> wrenchVolume;

    private MISAConfig(ModConfigSpec.Builder builder) {
        wrenchSoundsEnabled = builder
                .comment("Should wrench sounds be enabled")
                .define("wrench_sounds", true);
        wrenchVolume = builder
                .comment("Wrench Volume")
                .defineInRange("wrench_volume", 1.0, 0.0, 5.0);

        builder.build();
    }

    static {
        Pair<MISAConfig, ModConfigSpec> pair =
                new ModConfigSpec.Builder().configure(MISAConfig::new);
        CONFIG = pair.getLeft();
        CONFIG_SPEC = pair.getRight();
    }
}
