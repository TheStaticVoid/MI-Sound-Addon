package dev.thestaticvoid.mi_sound_addon.mixin.modern_industrialization;

import aztech.modern_industrialization.MI;
import aztech.modern_industrialization.machines.MachineBlockEntity;
import aztech.modern_industrialization.machines.blockentities.multiblocks.GeneratorMultiblockBlockEntity;
import aztech.modern_industrialization.machines.components.IsActiveComponent;
import dev.thestaticvoid.mi_sound_addon.MISoundAddonConfig;
import dev.thestaticvoid.mi_sound_addon.sound.ModSoundEventInfo;
import dev.thestaticvoid.mi_sound_addon.sound.ModSounds;
import dev.thestaticvoid.mi_sound_addon.util.SilencedComponent;
import dev.thestaticvoid.mi_sound_addon.util.SilencedComponentInterface;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;
import java.util.Objects;

@Mixin(GeneratorMultiblockBlockEntity.class)
public class GeneratorMultiblockBlockEntityMixin {
    @Unique
    Map<String, String> mI_Sound_Addon$defaultGenerators = Map.ofEntries(
            Map.entry("large_diesel_generator", "diesel"),
            Map.entry("large_steam_turbine", "turbine"),
            Map.entry("plasma_turbine", "turbine")
    );

    @Final
    @Shadow
    private IsActiveComponent isActiveComponent;

    @Unique
    private long mI_Sound_Addon$lastSoundTime = 0;

    @Inject(method = "tick", at = @At(value = "TAIL"), remap = false)
    public void tickMixin(CallbackInfo ci) {
        if (this.isActiveComponent.isActive && MISoundAddonConfig.CONFIG.generatorSoundsEnabled.get()) {
            MachineBlockEntity blockEntity = ((MachineBlockEntity)(Object)this);

            SilencedComponent silencedState = ((SilencedComponentInterface) blockEntity).mISoundAddon$getSilencedState();
            if (silencedState.silenced) return;

            // probably not the best way to get the blockId, but it's quick
            ResourceLocation machineId = blockEntity.guiParams.blockId;
            if (mI_Sound_Addon$defaultGenerators.containsKey(machineId.getPath())) {
                ModSoundEventInfo soundEvent = ModSounds.SOUND_EVENTS.get(MI.id(mI_Sound_Addon$defaultGenerators.get(machineId.getPath())));
                long currentGameTime = Objects.requireNonNull(blockEntity.getLevel()).getGameTime();

                if (currentGameTime > mI_Sound_Addon$lastSoundTime + soundEvent.getSoundDuration()) {
                    mI_Sound_Addon$lastSoundTime = currentGameTime;
                    ModSounds.playSoundNoRecipe(blockEntity, MI.id(mI_Sound_Addon$defaultGenerators.get(machineId.getPath())));
                }
            }
        }
    }
}
