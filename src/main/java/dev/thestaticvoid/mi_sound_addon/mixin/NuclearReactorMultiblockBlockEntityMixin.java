package dev.thestaticvoid.mi_sound_addon.mixin;

import aztech.modern_industrialization.machines.BEP;
import aztech.modern_industrialization.machines.MachineBlockEntity;
import aztech.modern_industrialization.machines.blockentities.multiblocks.NuclearReactorMultiblockBlockEntity;
import aztech.modern_industrialization.machines.components.IsActiveComponent;
import aztech.modern_industrialization.machines.components.OrientationComponent;
import aztech.modern_industrialization.machines.gui.MachineGuiParameters;
import aztech.modern_industrialization.machines.multiblocks.MultiblockMachineBlockEntity;
import aztech.modern_industrialization.util.Tickable;
import dev.thestaticvoid.mi_sound_addon.MISoundAddonConfig;
import dev.thestaticvoid.mi_sound_addon.sound.ModSounds;
import dev.thestaticvoid.mi_sound_addon.util.SilencedComponent;
import dev.thestaticvoid.mi_sound_addon.util.SilencedComponentInterface;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(NuclearReactorMultiblockBlockEntity.class)
public abstract class NuclearReactorMultiblockBlockEntityMixin extends MultiblockMachineBlockEntity implements Tickable {
    public NuclearReactorMultiblockBlockEntityMixin(BEP bep, MachineGuiParameters guiParams, OrientationComponent.Params orientationParams) {
        super(bep, guiParams, orientationParams);
    }

    @Unique
    public long lastSoundTime = 0;
    @Unique
    public long cachedWorldTime = 0;

    @Shadow(remap = false) @Final private IsActiveComponent isActive;

    @Inject(method = "tick", at = @At(value = "INVOKE",
            target = "Laztech/modern_industrialization/machines/components/IsActiveComponent;updateActive(ZLaztech/modern_industrialization/machines/MachineBlockEntity;)V",
            shift = At.Shift.AFTER), remap = false)
    private void tickMixin(CallbackInfo ci) {
        if (isActive.isActive && MISoundAddonConfig.getConfig().enableSounds) {
            MachineBlockEntity blockEntity = this;
            SilencedComponent silencedState = ((SilencedComponentInterface)blockEntity).mISoundAddon$getSilencedState();
            if (silencedState.silenced) return;
            if (cachedWorldTime == 0) {
                cachedWorldTime = Objects.requireNonNull(blockEntity.getLevel()).getGameTime();
            }
            cachedWorldTime++;

            if (cachedWorldTime > lastSoundTime + ModSounds.getNuclearDuration()) {
                lastSoundTime = cachedWorldTime;
                ModSounds.playNuclearSound(blockEntity);
            }
        }
    }
}
