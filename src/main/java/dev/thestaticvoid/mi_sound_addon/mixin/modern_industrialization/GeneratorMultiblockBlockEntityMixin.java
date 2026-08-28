package dev.thestaticvoid.mi_sound_addon.mixin.modern_industrialization;

import aztech.modern_industrialization.machines.BEP;
import aztech.modern_industrialization.machines.blockentities.multiblocks.GeneratorMultiblockBlockEntity;
import aztech.modern_industrialization.machines.components.FluidItemConsumerComponent;
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

@Mixin(GeneratorMultiblockBlockEntity.class)
public abstract class GeneratorMultiblockBlockEntityMixin extends MultiblockMachineBlockEntity {
    @Final
    @Shadow(remap = false)
    private IsActiveComponent isActiveComponent;

    @Unique
    public MachineSoundComponent mI_Sound_Addon$machineSoundComponent;

    public GeneratorMultiblockBlockEntityMixin(
            BEP bep,
            MachineGuiParameters guiParams,
            OrientationComponent.Params orientationParams) {
        super(bep, guiParams, orientationParams);
    }

    @Inject(method = "<init>", at = @At("TAIL"), remap = false)
    private void constructorMixin(
            BEP bep,
            String name,
            ShapeTemplate shapeTemplate,
            FluidItemConsumerComponent fluidConsumer,
            CallbackInfo ci) {

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
