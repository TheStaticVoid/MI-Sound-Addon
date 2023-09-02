package dev.thestaticvoid.mi_sound_addon.util;

import aztech.modern_industrialization.machines.IComponent;
import net.minecraft.nbt.CompoundTag;

public class SilencedComponent implements IComponent {
    public boolean silenced = false;

    @Override
    public void writeNbt(CompoundTag compoundTag) {
        compoundTag.putBoolean("silenced", silenced);
    }

    @Override
    public void readNbt(CompoundTag compoundTag) {
        silenced = compoundTag.getBoolean("silenced");
    }

    public void onMalletUse() {
        silenced = !silenced;
    }
}
