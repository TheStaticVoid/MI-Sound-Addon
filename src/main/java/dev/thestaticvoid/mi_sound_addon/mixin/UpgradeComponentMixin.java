package dev.thestaticvoid.mi_sound_addon.mixin;

import aztech.modern_industrialization.MI;
import aztech.modern_industrialization.machines.MachineBlockEntity;
import aztech.modern_industrialization.machines.components.UpgradeComponent;
import dev.thestaticvoid.mi_sound_addon.sound.ModSounds;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(UpgradeComponent.class)
public class UpgradeComponentMixin {
    @Inject(method = "onUse", at = @At("RETURN"), remap = false)
    private void onUseMixin(MachineBlockEntity be, Player player, InteractionHand hand, CallbackInfoReturnable<ItemInteractionResult> cir) {
        if (cir.getReturnValue() != ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION) {
            ModSounds.playSoundNoRecipe(be, MI.id("config_card"));
        }
    }
}
