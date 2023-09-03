package dev.thestaticvoid.mi_sound_addon.compat.kubejs;

import dev.thestaticvoid.mi_sound_addon.compat.kubejs.sound.MISoundKubeJSEvents;
import dev.thestaticvoid.mi_sound_addon.compat.kubejs.sound.ModifySoundEventsJS;

public class LoadedKubeJSProxy extends KubeJSProxy {
    @Override
    public void fireSoundModificationsEvent() {
        MISoundKubeJSEvents.MODIFY_SOUNDS.post(new ModifySoundEventsJS());
    }
}
