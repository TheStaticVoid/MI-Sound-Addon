package dev.thestaticvoid.mi_sound_addon.client.component;

import aztech.modern_industrialization.MI;
import aztech.modern_industrialization.machines.MachineBlockEntity;
import aztech.modern_industrialization.machines.MachineComponent;
import aztech.modern_industrialization.machines.recipe.MachineRecipeType;
import dev.thestaticvoid.mi_sound_addon.MISA;
import dev.thestaticvoid.mi_sound_addon.client.sound.MachineLoopSound;
import dev.thestaticvoid.mi_sound_addon.component.SilencedComponent;
import dev.thestaticvoid.mi_sound_addon.sound.MISASound;
import dev.thestaticvoid.mi_sound_addon.sound.ModSoundEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;

import java.util.function.Supplier;

public class MachineSoundComponent implements MachineComponent.ClientOnly {
    private final MachineBlockEntity machine;
    private final Supplier<MachineRecipeType> recipeType;
    private final ModSoundEvent soundEvent;
    private final Supplier<Boolean> shouldPlay;
    private boolean isPlaying;

    public MachineSoundComponent(
            MachineBlockEntity machine,
            Supplier<MachineRecipeType> recipeType,
            Supplier<Boolean> shouldPlay) {
        this(machine, null, recipeType, shouldPlay);
    }

    public MachineSoundComponent(
            MachineBlockEntity machine,
            ModSoundEvent event,
            Supplier<Boolean> shouldPlay) {
        this(machine, event, null, shouldPlay);
    }

    private MachineSoundComponent(
            MachineBlockEntity machine,
            ModSoundEvent event,
            Supplier<MachineRecipeType> recipeType,
            Supplier<Boolean> shouldPlay) {

        this.machine = machine;
        this.soundEvent = event;
        this.recipeType = recipeType;
        this.shouldPlay = shouldPlay;
    }

    public void tick() {
        if (!isPlaying && shouldPlay.get()) {
            isPlaying = true;
            startLoopingSound();
        }
    }

    private void startLoopingSound() {
        ModSoundEvent mse;
        if (this.soundEvent != null) {
            mse = this.soundEvent;
        } else {
            // The Electric Blast Furnace has its own unique sound
            if (!this.recipeType.get().getId().equals(MI.id("blast_furnace"))) {
                mse = MISASound.getSoundEventByRecipeType(this.recipeType.get());
            } else {
                mse = MISASound.getElectricBlastFurnaceEvent();
            }
        }

        Minecraft.getInstance().getSoundManager().queueTickingSound(new MachineLoopSound(
                this.machine.getBlockPos(),
                mse,
                () -> machine.isRemoved() || !this.shouldPlay.get() ||
                    this.machine.components.getOrThrow(SilencedComponent.class).silenced,
                () -> isPlaying = false));
    }

    @Override
    public void writeClientNbt(CompoundTag tag, HolderLookup.Provider registries) { }

    @Override
    public void readClientNbt(CompoundTag tag, HolderLookup.Provider registries) { }
}
