package dev.thestaticvoid.mi_sound_addon.compat.kubejs;

import dev.latvian.mods.kubejs.KubeJSPlugin;
import dev.thestaticvoid.mi_sound_addon.MISoundAddon;
import dev.thestaticvoid.mi_sound_addon.compat.kubejs.sound.MISoundAddonSoundKubeJSEvents;

public class MISoundAddonKubeJSPlugin extends KubeJSPlugin {
    @Override
    public void init() {}

    @Override
    public void registerEvents() {
        MISoundAddonSoundKubeJSEvents.EVENT_GROUP.register();
    }

    @Override
    public void initStartup() {
        KubeJSProxy.instance = new LoadedKubeJSProxy();
        MISoundAddon.onKubeJSPluginLoaded();
    }
}
