package dev.thestaticvoid.mi_sound_addon.client;

import dev.thestaticvoid.mi_sound_addon.MISA;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(value = MISA.ID, dist = Dist.CLIENT)
public class MISAClient {
    public MISAClient(IEventBus bus, ModContainer container) {

        // This will use NeoForge's ConfigurationScreen to display the mod's configs
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }
}
