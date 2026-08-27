package dev.thestaticvoid.mi_sound_addon;

import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import org.slf4j.Logger;

// Meesa jar jar binks
@Mod(MISA.ID)
public class MISA {
    public static final String ID = "mi_sound_addon";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(ID, path);
    }

    public MISA(IEventBus bus, ModContainer container) {

        // Register the config
        container.registerConfig(ModConfig.Type.COMMON, MISAConfig.CONFIG_SPEC);

    }
}
