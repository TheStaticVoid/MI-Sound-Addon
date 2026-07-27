package dev.thestaticvoid.mi_sound_addon.util;

import aztech.modern_industrialization.machines.MachineComponent;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import org.jspecify.annotations.NonNull;

public class SilencedComponent implements MachineComponent {
    public boolean silenced = false;

    @Override
    public void writeNbt(CompoundTag compoundTag, HolderLookup.@NonNull Provider provider) {
        compoundTag.putBoolean("silenced", silenced);
    }

    @Override
    public void readNbt(CompoundTag compoundTag, HolderLookup.@NonNull Provider provider, boolean b) {
        silenced = compoundTag.getBoolean("silenced");
    }

    public void onMalletUse() {
        silenced = !silenced;
    }
}
