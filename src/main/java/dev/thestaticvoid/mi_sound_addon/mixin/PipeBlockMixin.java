package dev.thestaticvoid.mi_sound_addon.mixin;

import aztech.modern_industrialization.pipes.impl.PipeBlock;
import aztech.modern_industrialization.pipes.impl.PipeBlockEntity;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PipeBlock.class)
public class PipeBlockMixin {
    @Inject(method = "useWrench", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/world/level/Level;playSound(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/core/BlockPos;Lnet/minecraft/sounds/SoundEvent;Lnet/minecraft/sounds/SoundSource;FF)V",
            remap = true), remap = false, cancellable = true)
    private static void useWrenchMixin(PipeBlockEntity pipe, Player player, InteractionHand hand, BlockHitResult hit, CallbackInfoReturnable<Boolean> cir) {
        // This should be fine as the method will return true if it reaches the point it would play sounds anyway
        // However if MI updates and adds extra stuff beyond the last if statement, this could break
        cir.setReturnValue(true);
    }
}
