package dev.thestaticvoid.mi_sound_addon.mixin;

import aztech.modern_industrialization.machines.MachineComponent;
import aztech.modern_industrialization.machines.MachineBlockEntity;
import aztech.modern_industrialization.machines.components.CrafterComponent;
import aztech.modern_industrialization.machines.recipe.MachineRecipe;
import aztech.modern_industrialization.machines.recipe.condition.MachineProcessCondition;
import com.llamalad7.mixinextras.sugar.Local;
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

import java.util.Objects;

@Mixin(CrafterComponent.class)
public abstract class TickRecipeMixin implements MachineComponent.ServerOnly {
    @Unique
    public long mI_Sound_Addon$lastSoundTime = 0;

    @Shadow(remap = false) @Final private MachineProcessCondition.Context conditionContext;
    @Shadow(remap = false) private RecipeHolder<MachineRecipe> activeRecipe;

    @Inject(method = "tickRecipe", at = @At("RETURN"), remap = false)
    private void tickRecipeInjection(CallbackInfoReturnable<Boolean> cir, @Local(name = "isActive") boolean isActive) {
        if (MISoundAddonConfig.CONFIG.machineSoundsEnabled.get()) {
            MachineBlockEntity blockEntity = this.conditionContext.getBlockEntity();
            SilencedComponent silencedState = ((SilencedComponentInterface)blockEntity).mISoundAddon$getSilencedState();
            if (silencedState.silenced) return;
            long currentGameTime = Objects.requireNonNull(blockEntity.getLevel()).getGameTime();

            if (isActive && this.activeRecipe != null) {
                if (currentGameTime > mI_Sound_Addon$lastSoundTime + ModSounds.getDuration(this.activeRecipe.value())) {
                    mI_Sound_Addon$lastSoundTime = currentGameTime;
                    ModSounds.playSound(blockEntity, this.activeRecipe.value());
                }
            }
        }
    }
}