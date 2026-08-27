package dev.thestaticvoid.mi_sound_addon.client.component;

import aztech.modern_industrialization.machines.MachineBlockEntity;
import aztech.modern_industrialization.machines.MachineComponent;
import dev.thestaticvoid.mi_sound_addon.MISA;
import dev.thestaticvoid.mi_sound_addon.client.sound.MISAClientSound;
import dev.thestaticvoid.mi_sound_addon.component.SilencedComponent;
import dev.thestaticvoid.mi_sound_addon.sound.ModSoundEvent;
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
            MISAClientSound.startMachineLoopingSound(
                    this.machine.getBlockPos(),
                    this.soundEvent,
                    () -> machine.isRemoved() || !this.shouldPlay.get() ||
                            this.machine.components.getOrThrow(SilencedComponent.class).silenced,
                    () -> isPlaying = false
            );
        }
    }

    @Override
    public void writeClientNbt(CompoundTag tag, HolderLookup.Provider registries) { }

    @Override
    public void readClientNbt(CompoundTag tag, HolderLookup.Provider registries) { }
}
