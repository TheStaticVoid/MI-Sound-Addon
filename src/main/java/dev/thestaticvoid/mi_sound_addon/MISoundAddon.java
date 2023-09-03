package dev.thestaticvoid.mi_sound_addon;

import dev.thestaticvoid.mi_sound_addon.compat.kubejs.sound.MISoundKubeJSEvents;
import dev.thestaticvoid.mi_sound_addon.compat.kubejs.sound.ModifySoundEventsJS;
import dev.thestaticvoid.mi_sound_addon.item.ModItems;
import dev.thestaticvoid.mi_sound_addon.sound.ModSounds;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MISoundAddon implements ModInitializer {
    public static final String MOD_ID = "mi_sound_addon";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        ModSounds.initializeSounds();
        ModItems.registerModItems();
        LOGGER.debug("Initialized mod: " + MOD_ID);
        if (FabricLoader.getInstance().isModLoaded("kubejs")) {
            MISoundKubeJSEvents.MODIFY_SOUNDS.post(new ModifySoundEventsJS());
        }
    }
}
