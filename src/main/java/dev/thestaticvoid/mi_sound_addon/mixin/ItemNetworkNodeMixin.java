package dev.thestaticvoid.mi_sound_addon.mixin;

import aztech.modern_industrialization.MI;
import aztech.modern_industrialization.pipes.impl.PipeBlockEntity;
import aztech.modern_industrialization.pipes.item.ItemNetworkNode;
import dev.thestaticvoid.mi_sound_addon.sound.ModSoundEventInfo;
import dev.thestaticvoid.mi_sound_addon.sound.ModSounds;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemNetworkNode.class)
public class ItemNetworkNodeMixin {
    @Inject(method = "customUse", at = @At(value = "RETURN"), remap = false)
    private static void customUseMixin(PipeBlockEntity pipe, Player player, InteractionHand hand, Direction hitDirection, CallbackInfoReturnable<Boolean> cir) {
        if (cir.getReturnValue()) {
            // customUse returns true if something actually changed, meaning the card did something
            ModSoundEventInfo configCardEvent = ModSounds.SOUND_EVENTS.get(MI.id("config_card"));
            // This method is server-side, leave player null
            pipe.getLevel().playSound(null, pipe.getBlockPos(), configCardEvent.getSoundEvent().get(), SoundSource.BLOCKS, configCardEvent.getVolume(), 1.0F);
        }
    }
}
