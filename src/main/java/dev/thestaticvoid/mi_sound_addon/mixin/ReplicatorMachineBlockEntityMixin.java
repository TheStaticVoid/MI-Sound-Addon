package dev.thestaticvoid.mi_sound_addon.mixin;

import aztech.modern_industrialization.machines.BEP;
import aztech.modern_industrialization.machines.MachineBlockEntity;
import aztech.modern_industrialization.machines.blockentities.ReplicatorMachineBlockEntity;
import aztech.modern_industrialization.machines.components.OrientationComponent;
import aztech.modern_industrialization.machines.gui.MachineGuiParameters;
import aztech.modern_industrialization.util.Tickable;
import dev.thestaticvoid.mi_sound_addon.MISoundAddonConfig;
import dev.thestaticvoid.mi_sound_addon.sound.ModSounds;
import dev.thestaticvoid.mi_sound_addon.util.SilencedComponent;
import dev.thestaticvoid.mi_sound_addon.util.SilencedComponentInterface;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(ReplicatorMachineBlockEntity.class)
public abstract class ReplicatorMachineBlockEntityMixin extends MachineBlockEntity implements Tickable {
    public ReplicatorMachineBlockEntityMixin(BEP bep, MachineGuiParameters guiParams, OrientationComponent.Params orientationParams) {
        super(bep, guiParams, orientationParams);
    }

    @Unique
    public long lastSoundTime = 0;

    @Inject(method = "tick()V", at = @At(value = "INVOKE",
            target = "Laztech/modern_industrialization/machines/components/IsActiveComponent;updateActive(ZLaztech/modern_industrialization/machines/MachineBlockEntity;)V",
            ordinal = 0, shift = At.Shift.BEFORE), remap = false)
    private void tickMixin(CallbackInfo ci) {
        if (MISoundAddonConfig.getConfig().enableSounds) {
            MachineBlockEntity blockEntity = ((ReplicatorMachineBlockEntity)(Object)this);
            SilencedComponent silencedState = ((SilencedComponentInterface)blockEntity).mISoundAddon$getSilencedState();
            if (silencedState.silenced) return;
            long currentGameTime = Objects.requireNonNull(blockEntity.getLevel()).getGameTime();

            if (currentGameTime > lastSoundTime + ModSounds.getDurationFromString("replicator")) {
                lastSoundTime = currentGameTime;
                ModSounds.playSoundNoRecipe(blockEntity, "replicator");
            }
        }
    }
}