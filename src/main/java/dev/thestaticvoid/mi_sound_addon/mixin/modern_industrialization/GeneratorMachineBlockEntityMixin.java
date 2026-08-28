package dev.thestaticvoid.mi_sound_addon.mixin.modern_industrialization;

import aztech.modern_industrialization.api.energy.CableTier;
import aztech.modern_industrialization.definition.FluidDefinition;
import aztech.modern_industrialization.machines.BEP;
import aztech.modern_industrialization.machines.MachineBlockEntity;
import aztech.modern_industrialization.machines.blockentities.GeneratorMachineBlockEntity;
import aztech.modern_industrialization.machines.components.IsActiveComponent;
import aztech.modern_industrialization.machines.components.OrientationComponent;
import aztech.modern_industrialization.machines.gui.MachineGuiParameters;
import dev.thestaticvoid.mi_sound_addon.MISAConfig;
import dev.thestaticvoid.mi_sound_addon.client.component.MachineSoundComponent;
import dev.thestaticvoid.mi_sound_addon.sound.MISASound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GeneratorMachineBlockEntity.class)
public abstract class GeneratorMachineBlockEntityMixin extends MachineBlockEntity {
    @Shadow(remap = false)
    protected IsActiveComponent isActiveComponent;

    @Unique
    public MachineSoundComponent mI_Sound_Addon$machineSoundComponent;

    public GeneratorMachineBlockEntityMixin(
            BEP bep,
            MachineGuiParameters guiParams,
            OrientationComponent.Params orientationParams) {
        super(bep, guiParams, orientationParams);
    }

    @Inject(method = "<init>(Laztech/modern_industrialization/machines/BEP;Ljava/lang/String;ZLaztech/modern_industrialization/api/energy/CableTier;JJJLaztech/modern_industrialization/definition/FluidDefinition;J)V", at = @At("TAIL"), remap = false)
    private void constructorMixin(
            BEP bep,
            String name,
            boolean hasFacing,
            CableTier outputTier,
            long energyCapacity,
            long fluidCapacity,
            long maxEnergyOutput,
            FluidDefinition acceptedFluid,
            long fluidEUperMb, CallbackInfo ci) {

        mI_Sound_Addon$machineSoundComponent = new MachineSoundComponent(
                this,
                MISASound.getGeneratorEvent(name),
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
