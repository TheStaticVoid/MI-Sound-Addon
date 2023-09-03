package dev.thestaticvoid.mi_sound_addon.compat.kubejs;

import dev.latvian.mods.kubejs.KubeJSPlugin;
import dev.latvian.mods.kubejs.event.EventGroup;
import dev.latvian.mods.kubejs.event.EventHandler;
import dev.thestaticvoid.mi_sound_addon.MISoundAddon;
import dev.thestaticvoid.mi_sound_addon.compat.kubejs.sound.ModifySoundEventsJS;

public class MISoundAddonKubeJSPlugin extends KubeJSPlugin {
    public final EventGroup EVENT_GROUP = EventGroup.of("MISoundAddons");
    public final EventHandler MODIFY_SOUNDS = EVENT_GROUP.startup("modifySounds", () -> ModifySoundEventsJS.class);
    @Override
    public void registerEvents() {
        EVENT_GROUP.register();
        MODIFY_SOUNDS.post(new ModifySoundEventsJS());
    }

    @Override
    public void initStartup() {
        MISoundAddon.onKubeJSPluginLoaded();
    }
}