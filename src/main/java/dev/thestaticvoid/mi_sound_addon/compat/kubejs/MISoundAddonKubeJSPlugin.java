package dev.thestaticvoid.mi_sound_addon.compat.kubejs;

import dev.latvian.mods.kubejs.KubeJSPlugin;
import dev.thestaticvoid.mi_sound_addon.compat.kubejs.sound.MISoundKubeJSEvents;

public class MISoundAddonKubeJSPlugin extends KubeJSPlugin {
    @Override
    public void registerEvents() {
        MISoundKubeJSEvents.EVENT_GROUP.register();
    }

    @Override
    public void initStartup() {
        // Disabled while MI has issues with custom machines
        // KubeJSProxy.instance = new LoadedKubeJSProxy();
    }
}