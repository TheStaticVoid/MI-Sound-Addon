package dev.thestaticvoid.mi_sound_addon;

import com.mojang.logging.LogUtils;
import dev.thestaticvoid.mi_sound_addon.compat.kubejs.KubeJSProxy;
import dev.thestaticvoid.mi_sound_addon.item.ModItems;
import dev.thestaticvoid.mi_sound_addon.sound.ModSounds;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.fml.event.lifecycle.FMLConstructModEvent;
import net.neoforged.fml.loading.LoadingModList;
import org.slf4j.Logger;

@Mod(MISoundAddon.MOD_ID)
public class MISoundAddon {
    public static final String MOD_ID = "mi_sound_addon";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }

    public MISoundAddon(IEventBus modEventBus, ModContainer modContainer) {
        KubeJSProxy.checkThatKubeJsIsLoaded();

        ModItems.init(modEventBus);

        modContainer.registerConfig(ModConfig.Type.COMMON, MISoundAddonConfig.CONFIG_SPEC);
        modEventBus.addListener(this::configLoading);
        // Must be done after all mods are constructed so that sounds for machines added by addons can be registered too
        modEventBus.addListener(FMLConstructModEvent.class, (event) -> event.enqueueWork(() -> {
            ModSounds.init(modEventBus);
            KubeJSProxy.instance.fireSoundModificationsEvent();
        }));

        LOGGER.info("Modern Industrialization Sound Addon initialized.");
    }

    public static boolean checkModIsLoaded(String modId) {
        return modId != null && !modId.isEmpty() && ModList.get() != null ?
                ModList.get().isLoaded(modId) :
                LoadingModList.get().getModFileById(modId) != null;
    }

    private void configLoading(ModConfigEvent.Loading event) {
        ModSounds.updateVolumes();
    }
}
