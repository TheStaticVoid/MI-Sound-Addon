package dev.thestaticvoid.mi_sound_addon.mixin;

import aztech.modern_industrialization.machines.MachineComponent;
import aztech.modern_industrialization.machines.MachineBlockEntity;
import aztech.modern_industrialization.machines.components.CrafterComponent;
import aztech.modern_industrialization.machines.recipe.MachineRecipe;
import aztech.modern_industrialization.machines.recipe.condition.MachineProcessCondition;
import dev.thestaticvoid.mi_sound_addon.MISoundAddonConfig;
import dev.thestaticvoid.mi_sound_addon.sound.ModSounds;
import dev.thestaticvoid.mi_sound_addon.util.SilencedComponent;
import dev.thestaticvoid.mi_sound_addon.util.SilencedComponentInterface;
import net.minecraft.world.item.crafting.RecipeHolder;
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
public abstract class TickRecipeMixin implements MachineComponent.ServerOnly {
    @Unique
    public long lastSoundTime = 0;

    @Shadow(remap = false) @Final private MachineProcessCondition.Context conditionContext;
    @Shadow(remap = false) private RecipeHolder<MachineRecipe> activeRecipe;

    @Inject(method = "tickRecipe", at = @At("RETURN"), locals = LocalCapture.CAPTURE_FAILHARD, remap = false)
    private void tickRecipeInjection(CallbackInfoReturnable<Boolean> cir, boolean isActive) {
        if (MISoundAddonConfig.CONFIG.machineSoundsEnabled.get()) {
            MachineBlockEntity blockEntity = this.conditionContext.getBlockEntity();
            SilencedComponent silencedState = ((SilencedComponentInterface)blockEntity).mISoundAddon$getSilencedState();
            if (silencedState.silenced) return;
            long currentGameTime = Objects.requireNonNull(blockEntity.getLevel()).getGameTime();

            if (isActive && this.activeRecipe != null) {
                if (currentGameTime > lastSoundTime + ModSounds.getDuration((MachineRecipe) this.activeRecipe.value())) {
                    lastSoundTime = currentGameTime;
                    ModSounds.playSound(blockEntity, (MachineRecipe) this.activeRecipe.value());
                }
            }
        }
    }
}