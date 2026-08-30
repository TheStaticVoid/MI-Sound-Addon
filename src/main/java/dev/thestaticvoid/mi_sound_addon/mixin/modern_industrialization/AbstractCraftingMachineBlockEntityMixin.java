package dev.thestaticvoid.mi_sound_addon.mixin.modern_industrialization;

import aztech.modern_industrialization.machines.BEP;
import aztech.modern_industrialization.machines.MachineBlockEntity;
import aztech.modern_industrialization.machines.blockentities.AbstractCraftingMachineBlockEntity;
import aztech.modern_industrialization.machines.components.CrafterComponent;
import aztech.modern_industrialization.machines.components.IsActiveComponent;
import aztech.modern_industrialization.machines.components.MachineInventoryComponent;
import aztech.modern_industrialization.machines.components.OrientationComponent;
import aztech.modern_industrialization.machines.gui.MachineGuiParameters;
import aztech.modern_industrialization.machines.guicomponents.ProgressBar;
import aztech.modern_industrialization.machines.init.MachineTier;
import aztech.modern_industrialization.machines.recipe.MachineRecipeType;
import dev.thestaticvoid.mi_sound_addon.MISAConfig;
import dev.thestaticvoid.mi_sound_addon.client.component.MachineSoundComponent;
import dev.thestaticvoid.mi_sound_addon.component.SilencedComponent;
import dev.thestaticvoid.mi_sound_addon.sound.MISASound;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractCraftingMachineBlockEntity.class)
public abstract class AbstractCraftingMachineBlockEntityMixin extends MachineBlockEntity {
    @Shadow(remap = false)
    @Final
    protected CrafterComponent crafter;

    @Shadow(remap = false)
    protected IsActiveComponent isActiveComponent;

    @Unique
    public MachineSoundComponent mI_Sound_Addon$machineSoundComponent;

    public AbstractCraftingMachineBlockEntityMixin(
            BEP bep,
            MachineGuiParameters guiParams,
            OrientationComponent.Params orientationParams) {
        super(bep, guiParams, orientationParams);
    }

    @Inject(method = "<init>", at = @At("TAIL"), remap = false)
    private void constructorMixin(
            BEP bep,
            MachineRecipeType recipeType,
            MachineInventoryComponent inventory,
            MachineGuiParameters guiParams,
            ProgressBar.Params progressBarParams,
            MachineTier tier,
            CallbackInfo ci) {

        mI_Sound_Addon$machineSoundComponent = new MachineSoundComponent(
                this,
                () -> this.crafter.getBehavior().recipeType(),
                () -> this.isActiveComponent.isActive
        );
        this.registerComponents(mI_Sound_Addon$machineSoundComponent);
    }

    @Inject(method = "tick", at = @At("HEAD"), remap = false)
    private void tickMixin(CallbackInfo ci) {
        if (level.isClientSide() && MISAConfig.CONFIG.machineSoundsEnabled.get()) {
            mI_Sound_Addon$machineSoundComponent.tick();
        }
    }
}
