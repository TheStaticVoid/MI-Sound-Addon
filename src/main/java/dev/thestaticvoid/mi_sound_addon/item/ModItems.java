package dev.thestaticvoid.mi_sound_addon.item;

import dev.thestaticvoid.mi_sound_addon.MISoundAddon;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ModItems {
    public static CreativeModeTab SOUND_ADDON_GROUP = FabricItemGroupBuilder.build(
            new ResourceLocation(MISoundAddon.MOD_ID, "creative_tab"), () -> new ItemStack(ModItems.MUFFLER));
    public static final Item MUFFLER = registerItem("muffler",
            new MufflerItem(new FabricItemSettings().group(SOUND_ADDON_GROUP)));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registry.ITEM, new ResourceLocation(MISoundAddon.MOD_ID, name), item);
    }

    public static void registerModItems() {
        MISoundAddon.LOGGER.debug("Registering Mod Items for: " + MISoundAddon.MOD_ID);
    }
}
