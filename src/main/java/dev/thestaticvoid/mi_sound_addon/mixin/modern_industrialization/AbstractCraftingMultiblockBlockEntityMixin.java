package dev.thestaticvoid.mi_sound_addon.mixin.modern_industrialization;

import aztech.modern_industrialization.machines.BEP;
import aztech.modern_industrialization.machines.blockentities.multiblocks.AbstractCraftingMultiblockBlockEntity;
import aztech.modern_industrialization.machines.components.CrafterComponent;
import aztech.modern_industrialization.machines.components.IsActiveComponent;
import aztech.modern_industrialization.machines.components.OrientationComponent;
import aztech.modern_industrialization.machines.gui.MachineGuiParameters;
import aztech.modern_industrialization.machines.multiblocks.MultiblockMachineBlockEntity;
import aztech.modern_industrialization.machines.multiblocks.ShapeTemplate;
import dev.thestaticvoid.mi_sound_addon.MISA;
import dev.thestaticvoid.mi_sound_addon.MISAConfig;
import dev.thestaticvoid.mi_sound_addon.client.component.MachineSoundComponent;
import dev.thestaticvoid.mi_sound_addon.sound.MISASound;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractCraftingMultiblockBlockEntity.class)
public abstract class AbstractCraftingMultiblockBlockEntityMixin extends MultiblockMachineBlockEntity {
    @Shadow(remap = false)
    @Final
    protected CrafterComponent crafter;

    @Shadow(remap = false)
    @Final
    private IsActiveComponent isActive;

    @Unique
    private MachineSoundComponent mI_Sound_Addon$machineSoundComponent;

    public AbstractCraftingMultiblockBlockEntityMixin(
            BEP bep,
            MachineGuiParameters guiParams,
            OrientationComponent.Params orientationParams) {
        super(bep, guiParams, orientationParams);
    }

    @Inject(method = "<init>(Laztech/modern_industrialization/machines/BEP;Lnet/minecraft/resources/ResourceLocation;Laztech/modern_industrialization/machines/components/OrientationComponent$Params;[Laztech/modern_industrialization/machines/multiblocks/ShapeTemplate;)V", at = @At("TAIL"), remap = false)
    private void constructorMixin(
            BEP bep,
            ResourceLocation blockId,
            OrientationComponent.Params orientationParams,
            ShapeTemplate[] shapeTemplates,
            CallbackInfo ci) {

        mI_Sound_Addon$machineSoundComponent = new MachineSoundComponent(
                this,
                () -> this.crafter.getBehavior().recipeType(),
                () -> this.isActive.isActive
        );
        this.registerComponents(mI_Sound_Addon$machineSoundComponent);
    }

    @Inject(method = "tick", at = @At("HEAD"), remap = false)
    private void tickMixin(CallbackInfo ci) {
        if (level.isClientSide() && MISAConfig.CONFIG.machineSoundsEnabled.get()) {
            this.components.getOrThrow(MachineSoundComponent.class).tick();
        }
    }
}
