package dev.thestaticvoid.mi_sound_addon.mixin.modern_industrialization;

import aztech.modern_industrialization.machines.BEP;
import aztech.modern_industrialization.machines.blockentities.multiblocks.SteamBoilerMultiblockBlockEntity;
import aztech.modern_industrialization.machines.components.IsActiveComponent;
import aztech.modern_industrialization.machines.components.OrientationComponent;
import aztech.modern_industrialization.machines.gui.MachineGuiParameters;
import aztech.modern_industrialization.machines.multiblocks.MultiblockMachineBlockEntity;
import aztech.modern_industrialization.machines.multiblocks.ShapeTemplate;
import dev.thestaticvoid.mi_sound_addon.MISAConfig;
import dev.thestaticvoid.mi_sound_addon.client.component.MachineSoundComponent;
import dev.thestaticvoid.mi_sound_addon.sound.MISASound;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SteamBoilerMultiblockBlockEntity.class)
public abstract class SteamBoilerMultiblockBlockEntityMixin extends MultiblockMachineBlockEntity {
    @Final
    @Shadow(remap = false)
    private IsActiveComponent isActiveComponent;

    @Unique
    public MachineSoundComponent mI_Sound_Addon$machineSoundComponent;

    public SteamBoilerMultiblockBlockEntityMixin(
            BEP bep,
            MachineGuiParameters guiParams,
            OrientationComponent.Params orientationParams) {
        super(bep, guiParams, orientationParams);
    }

    @Inject(method = "<init>", at = @At("TAIL"), remap = false)
    private void constructorMixin(
            BEP bep,
            ShapeTemplate shapeTemplate,
            String name,
            long maxEuProduction,
            boolean highPressure,
            CallbackInfo ci) {

        mI_Sound_Addon$machineSoundComponent = new MachineSoundComponent(
                this,
                MISASound.getBoilerEvent(),
                () -> this.isActiveComponent.isActive
        );
        this.registerComponents(mI_Sound_Addon$machineSoundComponent);
    }

    @Inject(method = "tick", at = @At("HEAD"), remap = false)
    private void tickMixin(CallbackInfo ci) {
        if (level.isClientSide() && MISAConfig.CONFIG.generatorSoundsEnabled.get()) {
            mI_Sound_Addon$machineSoundComponent.tick();
        }
    }

}
