package dev.thestaticvoid.mi_sound_addon;

import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class MISAConfig {
    public static final MISAConfig CONFIG;
    public static final ModConfigSpec CONFIG_SPEC;

    private MISAConfig(ModConfigSpec.Builder builder) {

        builder.build();
    }

    static {
        Pair<MISAConfig, ModConfigSpec> pair =
                new ModConfigSpec.Builder().configure(MISAConfig::new);
        CONFIG = pair.getLeft();
        CONFIG_SPEC = pair.getRight();
    }
}
