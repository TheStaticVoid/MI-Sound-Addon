package dev.thestaticvoid.mi_sound_addon.util;

import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.LoadingModList;

public class MISAUtil {
    public static boolean checkModIsLoaded(String modId) {
        return modId != null && !modId.isEmpty() && ModList.get() != null ?
                ModList.get().isLoaded(modId) :
                LoadingModList.get().getModFileById(modId) != null;
    }
}
