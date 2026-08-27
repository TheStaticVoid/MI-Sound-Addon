package dev.thestaticvoid.mi_sound_addon.compat.kubejs;

import dev.thestaticvoid.mi_sound_addon.util.MISAUtil;

public class KubeJSProxy {
    public static KubeJSProxy instance = new KubeJSProxy();

    public static void checkThatKubeJSIsLoaded() {
        if (MISAUtil.checkModIsLoaded("kubejs") && instance.getClass() == KubeJSProxy.class) {
            throw new IllegalStateException("KubeJS should have initialized before MI Sound Addon");
        }
    }

    public void fireSoundModificationsEvent() { }
}
