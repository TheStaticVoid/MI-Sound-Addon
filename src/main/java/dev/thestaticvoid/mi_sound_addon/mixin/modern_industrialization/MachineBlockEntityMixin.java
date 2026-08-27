package dev.thestaticvoid.mi_sound_addon.mixin.modern_industrialization;

import aztech.modern_industrialization.machines.BEP;
import aztech.modern_industrialization.machines.MachineBlockEntity;
import aztech.modern_industrialization.machines.MachineComponent;
import aztech.modern_industrialization.machines.components.OrientationComponent;
import aztech.modern_industrialization.machines.gui.MachineGuiParameters;
import dev.thestaticvoid.mi_sound_addon.component.SilencedComponent;
import dev.thestaticvoid.mi_sound_addon.component.SilencedComponentInterface;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MachineBlockEntity.class)
public abstract class MachineBlockEntityMixin implements SilencedComponentInterface {
    @Unique
    public SilencedComponent mI_Sound_Addon$silencedComp;

    @Shadow(remap = false)
    protected abstract void registerComponents(MachineComponent... components);

    @Inject(method="<init>", at = @At("TAIL"), remap = false)
    private void constructorMixin(
            BEP bep,
            MachineGuiParameters guiParams,
            OrientationComponent.Params orientationParams,
            CallbackInfo ci) {

        mI_Sound_Addon$silencedComp = new SilencedComponent();
        registerComponents(mI_Sound_Addon$silencedComp);
    }

    @Override
    public void mISoundAddon$toggleSilencedState() {
        mI_Sound_Addon$silencedComp.onMalletUse();
    }

    @Override
    public SilencedComponent mISoundAddon$getSilencedState() {
        return mI_Sound_Addon$silencedComp;
    }
}
