package dev.thestaticvoid.mi_sound_addon.sound;

import aztech.modern_industrialization.machines.recipe.MachineRecipeType;
import dev.thestaticvoid.mi_sound_addon.MISA;
import dev.thestaticvoid.mi_sound_addon.MISAConfig;
import dev.thestaticvoid.mi_sound_addon.client.sound.MachineLoopSound;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.function.Supplier;

public class MISASound {
    public static final HashMap<ResourceLocation, ModSoundEvent> SOUND_EVENTS = new HashMap<>();

    private static final float DEFAULT_SOUND_VOLUME = 1.0f;
    private static final DeferredRegister<SoundEvent> SOUND_EVENTS_REGISTRY = DeferredRegister.create(
            BuiltInRegistries.SOUND_EVENT,
            MISA.ID
    );

    public static void init(IEventBus bus) {
        DefaultSoundRegistry.register();
        SOUND_EVENTS_REGISTRY.register(bus);
    }

    public static void addSoundEvent(ResourceLocation type) {
        addSoundEvent(type, DEFAULT_SOUND_VOLUME);
    }

    public static void addSoundEvent(@NotNull ResourceLocation type, float volume) {
        ResourceLocation location = createFormattedResourceLocation(type);
        Supplier<SoundEvent> event = SOUND_EVENTS_REGISTRY.register(
                location.getPath(),
                () -> SoundEvent.createVariableRangeEvent(location)
        );

        SOUND_EVENTS.put(location, new ModSoundEvent(event, volume));
    }

    public static ModSoundEvent getSoundEventByRecipeType(MachineRecipeType machineRecipeType) {
        ResourceLocation location = createFormattedResourceLocation(machineRecipeType.getId());
        return SOUND_EVENTS.getOrDefault(location, null);
    }

    public static void setVolume(ResourceLocation location, float volume) {
        if (SOUND_EVENTS.containsKey(location)) {
            SOUND_EVENTS.remove(location);
            addSoundEvent(location, volume);
        } else {
            throw new IllegalStateException("Tried to set volume of non-existent recipe type: %s".formatted(location));
        }
    }

    public static void playMalletSound(Level level, BlockPos pos) {
        level.playSound(
                null,
                pos,
                SoundEvents.ANVIL_USE,
                SoundSource.BLOCKS,
                (float) MISAConfig.CONFIG.wrenchVolume.get().doubleValue(),
                1.0f);
    }

    private static ResourceLocation createFormattedResourceLocation(ResourceLocation id) {
        // This formats the output to be in the following format:
        // mi_sound_addon:mod_namespace/mod_path
        // An input of "modern_industrialization:assembler" would return
        // "mi_sound_addon:modern_industrialization/assembler"
        return ResourceLocation.fromNamespaceAndPath(MISA.ID, String.format("%s/%s", id.getNamespace(), id.getPath()));
    }
}
