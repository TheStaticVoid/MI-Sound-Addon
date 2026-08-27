package dev.thestaticvoid.mi_sound_addon.component;

import aztech.modern_industrialization.machines.MachineBlockEntity;

public interface SilencedComponentInterface {
    void mISoundAddon$toggleSilencedState(MachineBlockEntity be);
    SilencedComponent mISoundAddon$getSilencedState();
}
