package dev.thestaticvoid.mi_sound_addon.sound;

import aztech.modern_industrialization.MI;
import aztech.modern_industrialization.machines.init.MIMachineRecipeTypes;
import dev.thestaticvoid.mi_sound_addon.util.MISAUtil;
import net.minecraft.resources.ResourceLocation;
import net.swedz.extended_industrialization.EI;
import net.swedz.extended_industrialization.EISounds;

public class DefaultSoundRegistry {
    private static void populateRecipeTypes() {
        var types = MIMachineRecipeTypes.getRecipeTypes();
        for (var type : types) {
            ResourceLocation location = type.getId();
            if (location.equals(MI.id("forge_hammer"))) continue;
            if (location.equals(MISAUtil.YAIResource("pulse_detonation_generator"))) continue;
            MISASound.addSoundEvent(location);
        }
    }

    private static void populateExtraTypes() {
        MISASound.addSoundEvent(MI.id("electric_blast_furnace"));
        MISASound.addSoundEvent(MI.id("fission_reactor"));
        MISASound.addSoundEvent(MI.id("wrench"));
        MISASound.addSoundEvent(MI.id("config_card"));
        MISASound.addSoundEvent(MI.id("replicator"));
        MISASound.addSoundEvent(MI.id("boiler"));
        MISASound.addSoundEvent(MI.id("turbine"));
        MISASound.addSoundEvent(MI.id("diesel"));
    }

    private static void populateEISounds() {
        MISASound.SOUND_EVENTS.put(
                EI.id("tesla.loop"),
                new ModSoundEvent(EISounds.TESLA_COIL_LOOP, 1.0f)
        );
        MISASound.addSoundEvent(EI.id("tesla_tower"));
        MISASound.addSoundEvent(EI.id("solar_boiler"));
    }

    private static void populateIOSounds() {

    }

    private static void populateYAISounds() {

    }

    public static void register() {
        populateRecipeTypes();
        populateExtraTypes();

        if (MISAUtil.checkModIsLoaded(MISAUtil.EIID)) {
            populateEISounds();
        }

        if (MISAUtil.checkModIsLoaded(MISAUtil.IOID)) {
            populateIOSounds();
        }

        if (MISAUtil.checkModIsLoaded(MISAUtil.YAIID)) {
            populateYAISounds();
        }
    }
}
