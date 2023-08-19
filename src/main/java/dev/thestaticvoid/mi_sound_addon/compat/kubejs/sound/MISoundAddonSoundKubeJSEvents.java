package dev.thestaticvoid.mi_sound_addon.compat.kubejs.sound;

import dev.latvian.mods.kubejs.event.EventGroup;
import dev.latvian.mods.kubejs.event.EventHandler;

public interface MISoundAddonSoundKubeJSEvents {
    EventGroup EVENT_GROUP = EventGroup.of("MISoundAddons");

    EventHandler ADD_SOUNDS = EVENT_GROUP.startup("addSounds", () -> AddSoundsEventJS.class);
}
