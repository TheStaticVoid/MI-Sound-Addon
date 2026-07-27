package dev.thestaticvoid.mi_sound_addon.mixin.compat.extended_industrialization;

import aztech.modern_industrialization.MI;
import aztech.modern_industrialization.machines.MachineBlockEntity;
import dev.thestaticvoid.mi_sound_addon.MISoundAddonConfig;
import dev.thestaticvoid.mi_sound_addon.sound.ModSounds;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.swedz.extended_industrialization.machines.component.itemslot.TeslaTowerUpgradeComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TeslaTowerUpgradeComponent.class)
public class TeslaTowerUpgradeComponentMixin {
    @Inject(method = "onUse", at = @At("RETURN"), remap = false)
    private void onUseMixin(MachineBlockEntity blockEntity, Player player, InteractionHand hand, CallbackInfoReturnable<ItemInteractionResult> cir) {
        if (cir.getReturnValue() != ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION && MISoundAddonConfig.CONFIG.configCardSoundsEnabled.get()) {
            ModSounds.playSoundNoRecipe(blockEntity, MI.id("config_card"));
        }
    }
}
