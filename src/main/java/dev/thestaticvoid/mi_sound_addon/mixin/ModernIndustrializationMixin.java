package dev.thestaticvoid.mi_sound_addon.mixin;

import aztech.modern_industrialization.ModernIndustrialization;
import dev.thestaticvoid.mi_sound_addon.MISoundAddon;
import dev.thestaticvoid.mi_sound_addon.sound.ModSoundEventInfo;
import dev.thestaticvoid.mi_sound_addon.sound.ModSounds;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ModernIndustrialization.class)
public class ModernIndustrializationMixin {
    @Inject(method = "lambda$setupWrench$6", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/world/InteractionResult;sidedSuccess(Z)Lnet/minecraft/world/InteractionResult;", shift = At.Shift.BEFORE, remap = true), remap = false)
    private static void setupWrenchMixin(Player player, Level world, InteractionHand hand, BlockHitResult hitResult, CallbackInfoReturnable<InteractionResult> cir) {
         Level level = player.getLevel();
         ModSoundEventInfo wrenchEvent = ModSounds.SOUND_EVENTS.get("wrench");
         level.playSound(null, hitResult.getBlockPos(), wrenchEvent.getSoundEvent(), SoundSource.BLOCKS, wrenchEvent.getVolume(), 1.0f);
    }

    @Inject(method = "initialize", at = @At(value = "TAIL"), remap = false)
    private static void initializeMixin(CallbackInfo ci) {
        MISoundAddon.initialize();
    }
}
