package dev.thestaticvoid.mi_sound_addon.mixin.compat.tesseract_api;

import aztech.modern_industrialization.machines.IComponent;
import aztech.modern_industrialization.machines.MachineBlockEntity;
import aztech.modern_industrialization.machines.recipe.MachineRecipe;
import aztech.modern_industrialization.machines.recipe.condition.MachineProcessCondition;
import dev.thestaticvoid.mi_sound_addon.MISoundAddonConfig;
import dev.thestaticvoid.mi_sound_addon.sound.ModSounds;
import dev.thestaticvoid.mi_sound_addon.util.SilencedComponent;
import dev.thestaticvoid.mi_sound_addon.util.SilencedComponentInterface;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.swedz.tesseract.neoforge.compat.mi.component.craft.AbstractModularCrafterComponent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Objects;

@Mixin(AbstractModularCrafterComponent.class)
public abstract class AbstractModularCrafterComponentMixin<R> implements IComponent.ServerOnly {
    @Unique
    private long mISoundAddon$lastSoundTime = 0;

    @Shadow(remap = false)
    @Final
    protected MachineProcessCondition.Context conditionContext;

    @Shadow(remap = false)
    protected R activeRecipe;

    @Inject(method = "tickRecipe", at = @At("RETURN"), locals = LocalCapture.CAPTURE_FAILHARD, remap = false)
    private void tickRecipeInject(CallbackInfoReturnable<Boolean> cir, boolean isActive) {
        if (MISoundAddonConfig.machineSoundsEnabled) {
            MachineBlockEntity blockEntity = this.conditionContext.getBlockEntity();
            SilencedComponent silencedState = ((SilencedComponentInterface) blockEntity).mISoundAddon$getSilencedState();
            if (silencedState.silenced) return;
            long currentGameTime = Objects.requireNonNull(blockEntity.getLevel()).getGameTime();

            if (isActive && this.activeRecipe != null) {
                @SuppressWarnings("unchecked")
                RecipeHolder<MachineRecipe> recipeHolder = (RecipeHolder<MachineRecipe>) this.activeRecipe;

                if (currentGameTime > mISoundAddon$lastSoundTime + ModSounds.getDuration(recipeHolder.value())) {
                    mISoundAddon$lastSoundTime = currentGameTime;
                    ModSounds.playSound(blockEntity, recipeHolder.value());
                }
            }
        }
    }
}
