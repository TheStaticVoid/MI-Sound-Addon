package dev.thestaticvoid.mi_sound_addon.compat.kubejs.sound;

import dev.latvian.mods.kubejs.event.EventGroup;
import dev.latvian.mods.kubejs.event.EventHandler;

public interface MISoundKubeJSEvents {
    EventGroup EVENT_GROUP = EventGroup.of("MISoundAddons");

    EventHandler MODIFY_SOUNDS = EVENT_GROUP.startup("modifySounds", () -> ModifySoundEventsJS.class);
}
