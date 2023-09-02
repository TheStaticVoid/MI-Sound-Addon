package dev.thestaticvoid.mi_sound_addon.mixin;

import aztech.modern_industrialization.machines.IComponent;
import aztech.modern_industrialization.machines.MachineBlockEntity;
import aztech.modern_industrialization.machines.components.CrafterComponent;
import aztech.modern_industrialization.machines.recipe.MachineRecipe;
import aztech.modern_industrialization.machines.recipe.condition.MachineProcessCondition;
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
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Objects;

@Mixin(CrafterComponent.class)
public abstract class TickRecipeMixin implements IComponent.ServerOnly {
    @Unique
    public long lastSoundTime = 0;
    @Unique
    private long cachedWorldTime = 0;

    @Shadow(remap = false) @Final private MachineProcessCondition.Context conditionContext;
    @Shadow(remap = false) private MachineRecipe activeRecipe;

    @Inject(method = "tickRecipe", at = @At("RETURN"), locals = LocalCapture.CAPTURE_FAILHARD, remap = false)
    private void tickRecipeInjection(CallbackInfoReturnable<Boolean> cir, boolean isActive) {
        if (MISoundAddonConfig.getConfig().enableSounds) {
            MachineBlockEntity blockEntity = this.conditionContext.getBlockEntity();
            SilencedComponent silencedState = ((SilencedComponentInterface)blockEntity).mISoundAddon$getSilencedState();
            if (silencedState.silenced) return;
            if (cachedWorldTime == 0) {
                cachedWorldTime = Objects.requireNonNull(blockEntity.getLevel()).getGameTime();
            }
            cachedWorldTime++;

            if (isActive && this.activeRecipe != null) {
                if (cachedWorldTime > lastSoundTime + ModSounds.getDuration(this.activeRecipe)) {
                    lastSoundTime = cachedWorldTime;
                    ModSounds.playSound(blockEntity, this.activeRecipe);
                }
            }
        }
    }
}
