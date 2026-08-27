package dev.thestaticvoid.mi_sound_addon.client.sound;

import dev.thestaticvoid.mi_sound_addon.MISA;
import dev.thestaticvoid.mi_sound_addon.sound.ModSoundEvent;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.function.Supplier;

@OnlyIn(Dist.CLIENT)
public class MachineLoopSound extends AbstractTickableSoundInstance {
    private final Supplier<Boolean> shouldStop;
    private final Runnable onStop;

    public MachineLoopSound(BlockPos position, ModSoundEvent event, Supplier<Boolean> shouldStop, Runnable onStop) {
        super(event.event().get(), SoundSource.BLOCKS, SoundInstance.createUnseededRandom());

        var centerPos = position.getCenter();
        this.x = centerPos.x();
        this.y = centerPos.y();
        this.z = centerPos.z();

        this.volume = 0.0f;
        this.looping = true;
        this.shouldStop = shouldStop;
        this.onStop = onStop;
        MISA.LOGGER.info("MachineLoopSound initialized");
    }

    @Override
    public void tick() {
        MISA.LOGGER.info("MachineSoundLoop");
        MISA.LOGGER.info("isStopped: %s\tshouldStop: %s".formatted(this.isStopped(), shouldStop.get()));
        if (this.isStopped()) {
            return;
        }

        if (shouldStop.get()) {
            this.stop();
            onStop.run();
            return;
        }

        // TODO
        // Change volume based on config value
        this.volume = 1.0f;
        this.pitch = 1.0f;
    }

    @Override
    public boolean canStartSilent() {
        return true;
    }
}
