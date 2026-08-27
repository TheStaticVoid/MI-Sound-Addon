package dev.thestaticvoid.mi_sound_addon.component;

import aztech.modern_industrialization.machines.MachineComponent;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import org.jspecify.annotations.NonNull;

public class SilencedComponent implements MachineComponent {
    public boolean silenced = false;

    @Override
    public void writeNbt(CompoundTag tag, HolderLookup.@NonNull Provider registries) {
        tag.putBoolean("silenced", silenced);
    }

    @Override
    public void readNbt(CompoundTag tag, HolderLookup.@NonNull Provider registries, boolean isUpgradingMachine) {
        silenced = tag.getBoolean("silenced");
    }

    public void onMalletUse() {
        silenced = !silenced;
    }
}
