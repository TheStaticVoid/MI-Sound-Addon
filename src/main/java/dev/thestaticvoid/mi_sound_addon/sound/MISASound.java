package dev.thestaticvoid.mi_sound_addon.sound;

import aztech.modern_industrialization.MI;
import aztech.modern_industrialization.machines.recipe.MachineRecipeType;
import dev.thestaticvoid.mi_sound_addon.MISA;
import dev.thestaticvoid.mi_sound_addon.MISAConfig;
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

    public static void addSoundEvent(ResourceLocation location) {
        addSoundEvent(location, DEFAULT_SOUND_VOLUME);
    }

    public static void addSoundEvent(@NotNull ResourceLocation location, float volume) {
        ResourceLocation formattedLocation = createFormattedResourceLocation(location);
        Supplier<SoundEvent> event = SOUND_EVENTS_REGISTRY.register(
                formattedLocation.getPath(),
                () -> SoundEvent.createVariableRangeEvent(formattedLocation)
        );

        SOUND_EVENTS.put(formattedLocation, new ModSoundEvent(event, volume));
    }

    public static ModSoundEvent getSoundEventByRecipeType(MachineRecipeType machineRecipeType) {
        if (machineRecipeType == null) return null;
        ResourceLocation location = createFormattedResourceLocation(machineRecipeType.getId());
        return SOUND_EVENTS.getOrDefault(location, null);
    }

    public static void setVolume(ResourceLocation location, float volume) {
        if (SOUND_EVENTS.containsKey(location)) {
            ModSoundEvent soundEvent = SOUND_EVENTS.get(location);
            if (soundEvent.volume() != volume) {
                SOUND_EVENTS.remove(location);
                SOUND_EVENTS.put(location, new ModSoundEvent(soundEvent.event(), volume));
            }
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

    public static void playConfigCardSound(Level level, BlockPos pos) {
        ModSoundEvent mse = SOUND_EVENTS.getOrDefault(createFormattedResourceLocation(MI.id("conifg_card")), null);
        level.playSound(
                null,
                pos,
                mse.event().get(),
                SoundSource.BLOCKS,
                mse.volume(),
                1.0f);
    }

    public static ModSoundEvent getBoilerEvent() {
        ResourceLocation location = createFormattedResourceLocation(MI.id("boiler"));
        return SOUND_EVENTS.getOrDefault(location, null);
    }

    public static ModSoundEvent getGeneratorEvent(String name) {
        if (name.equals("lv_diesel_generator") ||
                name.equals("mv_diesel_generator") ||
                name.equals("hv_diesel_generator") ||
                name.equals("large_diesel_generator")) {

            return SOUND_EVENTS.getOrDefault(createFormattedResourceLocation(MI.id("diesel")), null);
        }

        if (name.equals("lv_steam_turbine") ||
                name.equals("mv_steam_turbine") ||
                name.equals("hv_steam_turbine") ||
                name.equals("large_steam_turbine") ||
                name.equals("plasma_turbine")) {

            return SOUND_EVENTS.getOrDefault(createFormattedResourceLocation(MI.id("turbine")), null);
        }

        return SOUND_EVENTS.getOrDefault(createFormattedResourceLocation(MI.id(name)), null);
    }

    public static ModSoundEvent getReplicatorEvent() {
        return SOUND_EVENTS.getOrDefault(createFormattedResourceLocation(MI.id("replicator")), null);
    }

    public static ModSoundEvent getFissionReactorEvent() {
        return SOUND_EVENTS.getOrDefault(createFormattedResourceLocation(MI.id("fission_reactor")), null);
    }

    public static ModSoundEvent getElectricBlastFurnaceEvent() {
        return SOUND_EVENTS.getOrDefault(createFormattedResourceLocation(MI.id("electric_blast_furnace")), null);
    }

    public static ResourceLocation createFormattedResourceLocation(ResourceLocation id) {
        // This formats the output to be in the following format:
        // mi_sound_addon:mod_namespace/mod_path
        // An input of "modern_industrialization:assembler" would return
        // "mi_sound_addon:modern_industrialization/assembler"
        return ResourceLocation.fromNamespaceAndPath(MISA.ID, String.format("%s/%s", id.getNamespace(), id.getPath()));
    }
}
