package dev.thestaticvoid.mi_sound_addon.compat.kubejs;

import dev.thestaticvoid.mi_sound_addon.compat.kubejs.sound.MISAKubeJSEvents;
import dev.thestaticvoid.mi_sound_addon.compat.kubejs.sound.ModifySoundEventsJS;

public class LoadedKubeJSProxy extends KubeJSProxy {
    @Override
    public void fireSoundModificationsEvent() {
        MISAKubeJSEvents.MODIFY_SOUNDS.post(new ModifySoundEventsJS());
    }
}
