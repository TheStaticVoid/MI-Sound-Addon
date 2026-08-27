package dev.thestaticvoid.mi_sound_addon.client.sound;

import dev.thestaticvoid.mi_sound_addon.sound.ModSoundEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.function.Supplier;

@OnlyIn(Dist.CLIENT)
public class MISAClientSound {
    public static void startMachineLoopingSound(BlockPos origin, ModSoundEvent event, Supplier<Boolean> shouldStop, Runnable onStop) {
        Minecraft.getInstance().getSoundManager().queueTickingSound(new MachineLoopSound(origin, event, shouldStop, onStop));
    }
}
