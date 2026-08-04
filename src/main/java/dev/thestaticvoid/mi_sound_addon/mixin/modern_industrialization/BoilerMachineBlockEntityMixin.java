package dev.thestaticvoid.mi_sound_addon.mixin.modern_industrialization;

import aztech.modern_industrialization.MI;
import aztech.modern_industrialization.machines.MachineBlockEntity;
import aztech.modern_industrialization.machines.blockentities.BoilerMachineBlockEntity;
import aztech.modern_industrialization.machines.components.FuelBurningComponent;
import dev.thestaticvoid.mi_sound_addon.MISoundAddon;
import dev.thestaticvoid.mi_sound_addon.MISoundAddonConfig;
import dev.thestaticvoid.mi_sound_addon.sound.ModSoundEventInfo;
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

@Mixin(BoilerMachineBlockEntity.class)
public class BoilerMachineBlockEntityMixin {
    @Final
    @Shadow(remap = false)
    private FuelBurningComponent fuelBurning;

    @Unique
    private long mI_Sound_Addon$lastSoundTime = 0;

    @Inject(method = "tick", at = @At(value = "TAIL"), remap = false)
    public void tickMixin(CallbackInfo ci) {
        if (this.fuelBurning.isBurning() && MISoundAddonConfig.CONFIG.generatorSoundsEnabled.get()) {
            MachineBlockEntity blockEntity = ((MachineBlockEntity)(Object)this);

            SilencedComponent silencedState = ((SilencedComponentInterface) blockEntity).mISoundAddon$getSilencedState();
            if (silencedState.silenced) return;

            ModSoundEventInfo boilerSoundEvent = ModSounds.SOUND_EVENTS.get(MI.id("boiler"));
            long currentGameTime = Objects.requireNonNull(blockEntity.getLevel()).getGameTime();

            if (currentGameTime > mI_Sound_Addon$lastSoundTime + boilerSoundEvent.getSoundDuration()) {
                mI_Sound_Addon$lastSoundTime = currentGameTime;
                ModSounds.playSoundNoRecipe(blockEntity, MI.id("boiler"));
            }
        }
    }
}
