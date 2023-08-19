package dev.thestaticvoid.mi_sound_addon.compat.kubejs;

import dev.thestaticvoid.mi_sound_addon.compat.kubejs.sound.AddSoundsEventJS;
import dev.thestaticvoid.mi_sound_addon.compat.kubejs.sound.MISoundAddonSoundKubeJSEvents;

public class LoadedKubeJSProxy extends KubeJSProxy {

    @Override
    public void fireRegisterSoundsEvent() {
        MISoundAddonSoundKubeJSEvents.ADD_SOUNDS.post(new AddSoundsEventJS());
    }
}
