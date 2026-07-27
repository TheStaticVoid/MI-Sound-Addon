package dev.thestaticvoid.mi_sound_addon.mixin;

import dev.thestaticvoid.mi_sound_addon.MISoundAddon;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.LoadingModList;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;

public class MISoundAddonMixinPlugin implements IMixinConfigPlugin {
    @Override
    public void onLoad(String mixinPackage) {

    }

    @Override
    public String getRefMapperConfig() {
        return "";
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        if (mixinClassName.equals("dev.thestaticvoid.mi_sound_addon.mixin.compat.tesseract_api.AbstractModularCrafterComponentMixin")) {
            return MISoundAddon.checkModIsLoaded("tesseract_api");
        }

        if (mixinClassName.equals("dev.thestaticvoid.mi_sound_addon.mixin.compat.extended_industrialization.TeslaTowerBlockEntityMixin")) {
            return MISoundAddon.checkModIsLoaded("extended_industrialization");
        }

        if (mixinClassName.equals("dev.thestaticvoid.mi_sound_addon.mixin.compat.extended_industrialization.TeslaCoilMachineBlockEntityMixin")) {
            return MISoundAddon.checkModIsLoaded("extended_industrialization");
        }
        return true;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {

    }

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }
}
