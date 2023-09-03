package dev.thestaticvoid.mi_sound_addon.compat.kubejs;

import dev.latvian.mods.kubejs.KubeJSPlugin;
import dev.thestaticvoid.mi_sound_addon.compat.kubejs.sound.MISoundKubeJSEvents;
import dev.thestaticvoid.mi_sound_addon.compat.kubejs.sound.ModifySoundEventsJS;

public class MISoundAddonKubeJSPlugin extends KubeJSPlugin {
    @Override
    public void registerEvents() {
        MISoundKubeJSEvents.EVENT_GROUP.register();
    }

    @Override
    public void initStartup() {
    }
}