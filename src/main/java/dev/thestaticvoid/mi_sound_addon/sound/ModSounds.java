package dev.thestaticvoid.mi_sound_addon.sound;

import aztech.modern_industrialization.MI;
import aztech.modern_industrialization.machines.MachineBlockEntity;
import aztech.modern_industrialization.machines.init.MIMachineRecipeTypes;
import aztech.modern_industrialization.machines.recipe.MachineRecipe;
import aztech.modern_industrialization.machines.recipe.MachineRecipeType;
import dev.thestaticvoid.mi_sound_addon.MISoundAddon;
import dev.thestaticvoid.mi_sound_addon.MISoundAddonConfig;
import dev.wp.industrialization_overdrive.IO;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.swedz.extended_industrialization.EI;
import net.swedz.extended_industrialization.EISounds;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.function.Supplier;

public class ModSounds {
    private static final DeferredRegister<SoundEvent> SOUND_EVENTS_REGISTRY = DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, MISoundAddon.MOD_ID);
    public static HashMap<ResourceLocation, ModSoundEventInfo> SOUND_EVENTS = new HashMap<>();

    private static final int DEFAULT_SOUND_DURATION = 60; // measured in ticks

    public static void init(IEventBus bus) {
        MISoundAddon.LOGGER.debug("Registering sounds for " + MISoundAddon.MOD_ID);
        populateDefaultRecipeTypes(MIMachineRecipeTypes.getRecipeTypes());
        SOUND_EVENTS_REGISTRY.register(bus);
    }

    public static void populateDefaultRecipeTypes(List<MachineRecipeType> machineRecipeTypes) {
        for (MachineRecipeType mrt : machineRecipeTypes) {
            ResourceLocation type = mrt.getId();
            if (type.equals(MI.id("forge_hammer"))) continue;
            addSoundEvent(type);
        }

        addSoundEvent(MI.id("electric_blast_furnace"));
        addSoundEvent(MI.id("fission_reactor"));
        addSoundEvent(MI.id("wrench"));
        addSoundEvent(MI.id("config_card"));
        addSoundEvent(MI.id("replicator"));
        addSoundEvent(MI.id("boiler"));
        addSoundEvent(MI.id("turbine"));
        addSoundEvent(MI.id("diesel"));
        if (MISoundAddon.checkModIsLoaded("extended_industrialization")) {
            // The tesla.loop sound effect exists in EI already, so adding it to this mod's sound registry
            // is slightly different from others.
            SOUND_EVENTS.put(EI.id("tesla.loop"), new ModSoundEventInfo(EISounds.TESLA_COIL_LOOP, 46, 1.0F));
            addSoundEvent(EI.id("tesla_tower"));
            addSoundEvent(EI.id("solar_boiler"));
        }
        updateDurations();
    }

    public static void addSoundEvent(ResourceLocation type) {
        addSoundEvent(type, 1.0F);
    }

    public static void addSoundEvent(ResourceLocation type, float volume) {
        addSoundEvent(type, volume, DEFAULT_SOUND_DURATION);
    }

    public static void addSoundEvent(ResourceLocation type, float volume, int duration) {
        ResourceLocation identifier = MISoundAddon.id(type.getNamespace() + "/" + type.getPath());
        Supplier<SoundEvent> soundEvent = SOUND_EVENTS_REGISTRY.register(identifier.getPath(), () -> SoundEvent.createVariableRangeEvent(identifier));
        SOUND_EVENTS.put(type, new ModSoundEventInfo(soundEvent, duration, volume));
        MISoundAddon.LOGGER.debug("Sound registered: {}", identifier);
    }

    private static ResourceLocation getRecipeType(@NotNull MachineRecipe activeRecipe) {
        return ((MachineRecipeType) activeRecipe.getType()).getId();
    }

    public static int getDuration(MachineRecipe activeRecipe) {
        // getSoundDuration will cause a crash if the recipe is added via addon
        if (SOUND_EVENTS.get(getRecipeType(activeRecipe)) != null) {
            return SOUND_EVENTS.get(getRecipeType(activeRecipe)).getSoundDuration();
        } else {
            return DEFAULT_SOUND_DURATION;
        }
    }

    public static int getDurationFromString(ResourceLocation type) {
        return SOUND_EVENTS.get(type).getSoundDuration();
    }

    public static void setDuration(ResourceLocation type, int duration) {
        if (ModSounds.SOUND_EVENTS.containsKey(type)) {
            ModSounds.SOUND_EVENTS.get(type).setSoundDuration(duration);
        } else {
            throw new IllegalStateException("Tried to set duration of non-existent recipe type: " + type);
        }
    }

    public static void setVolume(ResourceLocation type, float volume) {
        if (ModSounds.SOUND_EVENTS.containsKey(type)) {
            ModSounds.SOUND_EVENTS.get(type).setVolume(volume);
        } else {
            throw new IllegalStateException("Tried to set volume of non-existent recipe type: " + type);
        }
    }

    public static void playSound(@NotNull MachineBlockEntity blockEntity, MachineRecipe activeRecipe) {
        Level world = blockEntity.getLevel();
        if (world == null) { return; }
        if (activeRecipe == null) { return; }

        ModSoundEventInfo soundEventInfo;

        if (!blockEntity.guiParams.blockId.equals(MI.id("electric_blast_furnace"))) {
            soundEventInfo = SOUND_EVENTS.get(getRecipeType(activeRecipe));
        } else {
            soundEventInfo = SOUND_EVENTS.get(MI.id("electric_blast_furnace"));
        }

        if (soundEventInfo != null && soundEventInfo.getSoundEvent() != null) {
            world.playSound(null, blockEntity.getBlockPos().getX(), blockEntity.getBlockPos().getY(),
                    blockEntity.getBlockPos().getZ(), soundEventInfo.getSoundEvent().get(), SoundSource.BLOCKS,
                    soundEventInfo.getVolume(), 1.0F);
        }
    }

    public static void playSoundNoRecipe(@NotNull BlockEntity blockEntity, ResourceLocation type) {
        ModSoundEventInfo soundEventInfo = SOUND_EVENTS.get(type);
        playSoundEvent(blockEntity, soundEventInfo);
    }

    public static void playSoundEvent(@NotNull BlockEntity blockEntity, ModSoundEventInfo soundEventInfo) {
        Level world = blockEntity.getLevel();
        if (world == null) { return; }

        if (soundEventInfo.getSoundEvent() != null) {
            world.playSound(null, blockEntity.getBlockPos(), soundEventInfo.getSoundEvent().get(), SoundSource.BLOCKS,
                    soundEventInfo.getVolume(), 1.0F);
        }
    }

    public static void playSoundForPlayer(@NotNull BlockEntity blockEntity, ResourceLocation type, Player player) {
        ModSoundEventInfo soundEventInfo = SOUND_EVENTS.get(type);
        Level world = blockEntity.getLevel();
        if (world == null) { return; }

        if (soundEventInfo.getSoundEvent() != null) {
            world.playSound(player, blockEntity.getBlockPos(), soundEventInfo.getSoundEvent().get(), SoundSource.BLOCKS,
                    soundEventInfo.getVolume(), 1.0F);
        }
    }

    private static void updateDurations() {
        setDuration(MI.id("assembler"), 39);
        setDuration(MI.id("blast_furnace"), 31);
        setDuration(MI.id("centrifuge"), 39);
        setDuration(MI.id("chemical_reactor"), 80);
        setDuration(MI.id("coke_oven"), 34);
        setDuration(MI.id("compressor"), 30);
        setDuration(MI.id("cutting_machine"), 68);
        setDuration(MI.id("distillation_tower"), 109);
        setDuration(MI.id("distillery"), 71);
        setDuration(MI.id("electric_blast_furnace"), 61);
        setDuration(MI.id("electrolyzer"), 7);
        setDuration(MI.id("fission_reactor"), 60);
        setDuration(MI.id("fusion_reactor"), 67);
        setDuration(MI.id("furnace"), 38);
        setDuration(MI.id("heat_exchanger"), 71);
        setDuration(MI.id("implosion_compressor"), 70);
        setDuration(MI.id("macerator"), 16);
        setDuration(MI.id("mixer"), 27);
        setDuration(MI.id("oil_drilling_rig"), 29);
        setDuration(MI.id("packer"), 23);
        setDuration(MI.id("polarizer"), 29);
        setDuration(MI.id("pressurizer"), 89);
        setDuration(MI.id("quarry"), 61);
        setDuration(MI.id("unpacker"), 23);
        setDuration(MI.id("vacuum_freezer"), 62);
        setDuration(MI.id("wiremill"), 45);
        setDuration(MI.id("replicator"), 20);
        setDuration(MI.id("boiler"), 66);
        setDuration(MI.id("turbine"), 36);
        setDuration(MI.id("diesel"), 9);

        if (MISoundAddon.checkModIsLoaded("extended_industrialization")) {
            setDuration(EI.id("composter"), 97);
            setDuration(EI.id("alloy_smelter"), 38);
            setDuration(EI.id("bending_machine"), 30);
            setDuration(EI.id("canning_machine"), 69); // nice
            setDuration(EI.id("brewery"), 80);
            setDuration(EI.id("tesla.loop"), 45);
            setDuration(EI.id("tesla_tower"), 89);
            setDuration(EI.id("solar_boiler"), 105);
        }
        if (MISoundAddon.checkModIsLoaded("industrialization_overdrive")) {
            setDuration(IO.id("pyrolyse_oven"), 80);
        }
    }

    public static void updateVolumes() {
        setVolume(MI.id("assembler"), (float)MISoundAddonConfig.CONFIG.assemblerVolume.get().doubleValue());
        setVolume(MI.id("blast_furnace"), (float)MISoundAddonConfig.CONFIG.blastFurnaceVolume.get().doubleValue());
        setVolume(MI.id("centrifuge"), (float)MISoundAddonConfig.CONFIG.centrifugeVolume.get().doubleValue());
        setVolume(MI.id("chemical_reactor"), (float)MISoundAddonConfig.CONFIG.chemicalReactorVolume.get().doubleValue());
        setVolume(MI.id("coke_oven"), (float) MISoundAddonConfig.CONFIG.cokeOvenVolume.get().doubleValue());
        setVolume(MI.id("compressor"), (float)MISoundAddonConfig.CONFIG.compressorVolume.get().doubleValue());
        setVolume(MI.id("cutting_machine"), (float)MISoundAddonConfig.CONFIG.cuttingMachineVolume.get().doubleValue());
        setVolume(MI.id("distillation_tower"), (float)MISoundAddonConfig.CONFIG.distillationTowerVolume.get().doubleValue());
        setVolume(MI.id("distillery"), (float)MISoundAddonConfig.CONFIG.distilleryVolume.get().doubleValue());
        setVolume(MI.id("electric_blast_furnace"), (float)MISoundAddonConfig.CONFIG.electricBlastFurnaceVolume.get().doubleValue());
        setVolume(MI.id("electrolyzer"), (float)MISoundAddonConfig.CONFIG.electrolyzerVolume.get().doubleValue());
        setVolume(MI.id("fission_reactor"), (float)MISoundAddonConfig.CONFIG.fissionReactorVolume.get().doubleValue());
        setVolume(MI.id("fusion_reactor"), (float)MISoundAddonConfig.CONFIG.fusionReactorVolume.get().doubleValue());
        setVolume(MI.id("furnace"), (float)MISoundAddonConfig.CONFIG.furnaceVolume.get().doubleValue());
        setVolume(MI.id("heat_exchanger"), (float)MISoundAddonConfig.CONFIG.heatExchangerVolume.get().doubleValue());
        setVolume(MI.id("implosion_compressor"), (float)MISoundAddonConfig.CONFIG.implosionCompressorVolume.get().doubleValue());
        setVolume(MI.id("macerator"), (float)MISoundAddonConfig.CONFIG.maceratorVolume.get().doubleValue());
        setVolume(MI.id("mixer"), (float)MISoundAddonConfig.CONFIG.mixerVolume.get().doubleValue());
        setVolume(MI.id("oil_drilling_rig"), (float)MISoundAddonConfig.CONFIG.oilDrillingRigVolume.get().doubleValue());
        setVolume(MI.id("packer"), (float)MISoundAddonConfig.CONFIG.packerVolume.get().doubleValue());
        setVolume(MI.id("polarizer"), (float)MISoundAddonConfig.CONFIG.polarizerVolume.get().doubleValue());
        setVolume(MI.id("pressurizer"), (float)MISoundAddonConfig.CONFIG.pressurizerVolume.get().doubleValue());
        setVolume(MI.id("quarry"), (float)MISoundAddonConfig.CONFIG.quarryVolume.get().doubleValue());
        setVolume(MI.id("unpacker"), (float)MISoundAddonConfig.CONFIG.unpackerVolume.get().doubleValue());
        setVolume(MI.id("vacuum_freezer"), (float)MISoundAddonConfig.CONFIG.vacuumFreezerVolume.get().doubleValue());
        setVolume(MI.id("wiremill"), (float)MISoundAddonConfig.CONFIG.wiremillVolume.get().doubleValue());
        setVolume(MI.id("wrench"), (float)MISoundAddonConfig.CONFIG.wrenchVolume.get().doubleValue());
        setVolume(MI.id("config_card"), (float)MISoundAddonConfig.CONFIG.configCardVolume.get().doubleValue());
        setVolume(MI.id("replicator"), (float)MISoundAddonConfig.CONFIG.replicatorVolume.get().doubleValue());
        setVolume(MI.id("boiler"), (float)MISoundAddonConfig.CONFIG.boilerVolume.get().doubleValue());
        setVolume(MI.id("turbine"), (float)MISoundAddonConfig.CONFIG.turbineVolume.get().doubleValue());
        setVolume(MI.id("diesel"), (float)MISoundAddonConfig.CONFIG.dieselVolume.get().doubleValue());
        if (MISoundAddon.checkModIsLoaded("extended_industrialization")) {
            setVolume(EI.id("composter"), (float)MISoundAddonConfig.CONFIG.composterVolume.get().doubleValue());
            setVolume(EI.id("alloy_smelter"), (float)MISoundAddonConfig.CONFIG.alloySmelterVolume.get().doubleValue());
            setVolume(EI.id("bending_machine"), (float)MISoundAddonConfig.CONFIG.bendingMachineVolume.get().doubleValue());
            setVolume(EI.id("canning_machine"), (float)MISoundAddonConfig.CONFIG.canningMachineVolume.get().doubleValue());
            setVolume(EI.id("tesla.loop"), (float)MISoundAddonConfig.CONFIG.teslaCoilVolume.get().doubleValue());
            setVolume(EI.id("tesla_tower"), (float)MISoundAddonConfig.CONFIG.teslaTowerVolume.get().doubleValue());
            setVolume(EI.id("solar_boiler"), (float)MISoundAddonConfig.CONFIG.solarBoilerVolume.get().doubleValue());
        }

        if (MISoundAddon.checkModIsLoaded("industrialization_overdrive")) {
            setVolume(IO.id("pyrolyse_oven"), (float)MISoundAddonConfig.CONFIG.pyrolyseOvenVolume.get().doubleValue());
        }
    }
}
