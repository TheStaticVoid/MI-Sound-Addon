package dev.thestaticvoid.mi_sound_addon.client.component;

import aztech.modern_industrialization.machines.MachineBlockEntity;
import aztech.modern_industrialization.machines.MachineComponent;
import dev.thestaticvoid.mi_sound_addon.MISA;
import dev.thestaticvoid.mi_sound_addon.client.sound.MISAClientSound;
import dev.thestaticvoid.mi_sound_addon.sound.ModSoundEvent;
import dev.thestaticvoid.mi_sound_addon.sound.MISASound;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;

import java.util.function.Supplier;

public class MachineSoundComponent implements MachineComponent.ClientOnly {
    private final MachineBlockEntity machine;
    private final ModSoundEvent soundEvent;
    private final Supplier<Boolean> shouldPlay;
    private boolean isPlaying;

    public MachineSoundComponent(
            MachineBlockEntity machine,
            ModSoundEvent event,
            Supplier<Boolean> shouldPlay
    ) {
        this.machine = machine;
        this.soundEvent = event;
        this.shouldPlay = shouldPlay;
    }

    public void tick() {
        if (!isPlaying && shouldPlay.get() && soundEvent != null) {
            isPlaying = true;
            MISA.LOGGER.info(String.format("Machine tick: %s, %s, %s, %s", machine, soundEvent, shouldPlay, isPlaying));
            MISAClientSound.startMachineLoopingSound(
                    this.machine.getBlockPos(),
                    this.soundEvent,
                    () -> machine.isRemoved() || !this.shouldPlay.get(),
                    () -> isPlaying = false
            );
        }
    }

    @Override
    public void writeClientNbt(CompoundTag tag, HolderLookup.Provider registries) { }

    @Override
    public void readClientNbt(CompoundTag tag, HolderLookup.Provider registries) { }
}
