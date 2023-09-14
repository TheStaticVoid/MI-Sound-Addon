package dev.thestaticvoid.mi_sound_addon;

import aztech.modern_industrialization.datagen.translation.EnglishTranslation;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;

@Config(name = MISoundAddonConfig.NAME)
public class MISoundAddonConfig implements ConfigData {
    @ConfigEntry.Gui.Excluded
    public static final transient String NAME = "mi_sound_addon";

    @ConfigEntry.Gui.RequiresRestart
    @EnglishTranslation(value = "Should the machine sounds be enabled? Requires restart")
    public boolean enableSounds = true;
    @ConfigEntry.Gui.RequiresRestart
    @EnglishTranslation(value = "Assembler Volume")
    public float assemblerVolume = 1.0f;
    @ConfigEntry.Gui.RequiresRestart
    @EnglishTranslation(value = "Steam Blast Furnace Volume")
    public float blastFurnaceVolume = 1.0f;
    @ConfigEntry.Gui.RequiresRestart
    @EnglishTranslation(value = "Centrifuge Volume")
    public float centrifugeVolume = 1.0f;
    @ConfigEntry.Gui.RequiresRestart
    @EnglishTranslation(value = "Chemical Reactor Volume")
    public float chemicalReactorVolume = 1.0f;
    @ConfigEntry.Gui.RequiresRestart
    @EnglishTranslation(value = "Coke Oven Volume")
    public float cokeOvenVolume = 1.0f;
    @ConfigEntry.Gui.RequiresRestart
    @EnglishTranslation(value = "Compressor Volume")
    public float compressorVolume = 1.0f;
    @ConfigEntry.Gui.RequiresRestart
    @EnglishTranslation(value = "Cutting Machine Volume")
    public float cuttingMachineVolume = 1.0f;
    @ConfigEntry.Gui.RequiresRestart
    @EnglishTranslation(value = "Distillation Tower Volume")
    public float distillationTowerVolume = 1.0f;
    @ConfigEntry.Gui.RequiresRestart
    @EnglishTranslation(value = "Distillery Volume")
    public float distilleryVolume = 1.0f;
    @ConfigEntry.Gui.RequiresRestart
    @EnglishTranslation(value = "Electric Blast Furnace Volume")
    public float electricBlastFurnaceVolume = 1.0f;
    @ConfigEntry.Gui.RequiresRestart
    @EnglishTranslation(value = "Electrolyzer Volume")
    public float electrolyzerVolume = 1.0f;
    @ConfigEntry.Gui.RequiresRestart
    @EnglishTranslation(value = "Fission Reactor Volume")
    public float fissionReactorVolume = 1.0f;
    @ConfigEntry.Gui.RequiresRestart
    @EnglishTranslation(value = "Fusion Reactor Volume")
    public float fusionReactorVolume = 1.0f;
    @ConfigEntry.Gui.RequiresRestart
    @EnglishTranslation(value = "Furnace Volume")
    public float furnaceVolume = 1.0f;
    @ConfigEntry.Gui.RequiresRestart
    @EnglishTranslation(value = "Heat Exchanger Volume")
    public float heatExchangerVolume = 1.0f;
    @ConfigEntry.Gui.RequiresRestart
    @EnglishTranslation(value = "Implosion Compressor Volume")
    public float implosionCompressorVolume = 1.0f;
    @ConfigEntry.Gui.RequiresRestart
    @EnglishTranslation(value = "Macerator Volume")
    public float maceratorVolume = 1.0f;
    @ConfigEntry.Gui.RequiresRestart
    @EnglishTranslation(value = "Mixer Volume")
    public float mixerVolume = 1.0f;
    @ConfigEntry.Gui.RequiresRestart
    @EnglishTranslation(value = "Oil Drilling Rig Volume")
    public float oilDrillingRigVolume = 1.0f;
    @ConfigEntry.Gui.RequiresRestart
    @EnglishTranslation(value = "Packer Volume")
    public float packerVolume = 1.0f;
    @ConfigEntry.Gui.RequiresRestart
    @EnglishTranslation(value = "Polarizer Volume")
    public float polarizerVolume = 1.0f;
    @ConfigEntry.Gui.RequiresRestart
    @EnglishTranslation(value = "Pressurizer Volume")
    public float pressurizerVolume = 1.0f;
    @ConfigEntry.Gui.RequiresRestart
    @EnglishTranslation(value = "Quarry Volume")
    public float quarryVolume = 1.0f;
    @ConfigEntry.Gui.RequiresRestart
    @EnglishTranslation(value = "Unpacker Volume")
    public float unpackerVolume = 1.0f;
    @ConfigEntry.Gui.RequiresRestart
    @EnglishTranslation(value = "Vacuum Freezer Volume")
    public float vacuumFreezerVolume = 1.0f;
    @ConfigEntry.Gui.RequiresRestart
    @EnglishTranslation(value = "Wiremill Volume")
    public float wiremillVolume = 1.0f;
    @ConfigEntry.Gui.RequiresRestart
    @EnglishTranslation(value = "Wrench Volume")
    public float wrenchVolume = 1.0f;
    @ConfigEntry.Gui.RequiresRestart
    @EnglishTranslation(value = "Replicator Volume")
    public float replicatorVolume = 1.0f;

    @ConfigEntry.Gui.Excluded
    private transient static boolean registered = false;
    public static synchronized MISoundAddonConfig getConfig() {
        if (!registered) {
            AutoConfig.register(MISoundAddonConfig.class, Toml4jConfigSerializer::new);
            registered = true;
        }

        return AutoConfig.getConfigHolder(MISoundAddonConfig.class).getConfig();
    }
}
