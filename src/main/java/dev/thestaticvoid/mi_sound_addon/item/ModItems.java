package dev.thestaticvoid.mi_sound_addon.item;

import dev.thestaticvoid.mi_sound_addon.MISoundAddon;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;

public class ModItems {
    public static ResourceKey<CreativeModeTab> TAB_KEY = ResourceKey.create(Registries.CREATIVE_MODE_TAB,
            new ResourceLocation(MISoundAddon.MOD_ID, "creative_tab"));
    private static final String CREATIVE_TAB_TRANSLATION = "message.mi_sound_addon.creative_tab";

    public static final Item MALLET = registerItem("mallet",
            new MalletItem(new FabricItemSettings()));

    private static Item registerItem(String name, Item item) {
        return Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(MISoundAddon.MOD_ID, name), item);
    }

    public static void registerModItems() {
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, TAB_KEY, FabricItemGroup.builder()
                        .title(Component.translatable(CREATIVE_TAB_TRANSLATION))
                        .icon(() -> MALLET.asItem().getDefaultInstance())
                        .displayItems((params, output) -> output.accept(MALLET))
                        .build());
        MISoundAddon.LOGGER.debug("Registering Mod Items for: " + MISoundAddon.MOD_ID);
    }
}
