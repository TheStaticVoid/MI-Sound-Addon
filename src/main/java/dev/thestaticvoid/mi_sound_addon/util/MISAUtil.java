package dev.thestaticvoid.mi_sound_addon.util;

import net.minecraft.resources.ResourceLocation;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.LoadingModList;

public class MISAUtil {
    public static final String EIID = "extended_industrialization";
    public static final String IOID = "industrialization_overdrive";
    public static final String YAIID = "yet_another_industrialization";

    public static boolean checkModIsLoaded(String modId) {
        return modId != null && !modId.isEmpty() && ModList.get() != null ?
                ModList.get().isLoaded(modId) :
                LoadingModList.get().getModFileById(modId) != null;
    }

    public static ResourceLocation YAIResource(String path) {
        return ResourceLocation.fromNamespaceAndPath(YAIID, path);
    }
}
