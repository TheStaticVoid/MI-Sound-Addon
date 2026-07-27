package dev.thestaticvoid.mi_sound_addon.mixin.compat.extended_industrialization;

import aztech.modern_industrialization.machines.MachineBlockEntity;
import com.llamalad7.mixinextras.sugar.Local;
import dev.thestaticvoid.mi_sound_addon.MISoundAddonConfig;
import dev.thestaticvoid.mi_sound_addon.sound.ModSoundEventInfo;
import dev.thestaticvoid.mi_sound_addon.sound.ModSounds;
import dev.thestaticvoid.mi_sound_addon.util.SilencedComponent;
import dev.thestaticvoid.mi_sound_addon.util.SilencedComponentInterface;
import net.swedz.extended_industrialization.EI;
import net.swedz.extended_industrialization.machines.blockentity.multiblock.teslatower.TeslaTowerBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(TeslaTowerBlockEntity.class)
public abstract class TeslaTowerBlockEntityMixin {
    @Unique
    private long mI_Sound_Addon$lastSoundTime = 0;

    @Inject(method = "tick", at = @At("TAIL"), remap = false)
    private void tickMixin(CallbackInfo ci, @Local(name = "active") boolean active) {
        if (active && MISoundAddonConfig.CONFIG.machineSoundsEnabled.get()) {
            MachineBlockEntity blockEntity = ((MachineBlockEntity)(Object)this);

            SilencedComponent silencedState = ((SilencedComponentInterface) blockEntity).mISoundAddon$getSilencedState();
            if (silencedState.silenced) return;

            ModSoundEventInfo teslaSoundEvent = ModSounds.SOUND_EVENTS.get(EI.id("tesla_tower"));
            long currentGameTime = Objects.requireNonNull(blockEntity.getLevel()).getGameTime();

            if (currentGameTime > mI_Sound_Addon$lastSoundTime + teslaSoundEvent.getSoundDuration()) {
                mI_Sound_Addon$lastSoundTime = currentGameTime;
                ModSounds.playSoundNoRecipe(blockEntity, EI.id("tesla_tower"));
            }
        }
    }
}
