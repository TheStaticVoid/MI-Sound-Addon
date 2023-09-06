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
        // KubeJS compat is disabled until MI officially supports it on this version
        // KubeJSProxy.instance = new LoadedKubeJSProxy();
    }
}