package dev.thestaticvoid.mi_sound_addon.item;

import dev.thestaticvoid.mi_sound_addon.MISoundAddon;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.HashSet;
import java.util.Set;


public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MISoundAddon.MOD_ID);
    public static DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MISoundAddon.MOD_ID);
    public static final String CREATIVE_MODE_TAB_TITLE = "message.mi_sound_addon.creative_tab";
    public static final DeferredItem<Item> MALLET_ITEM = ITEMS.register("mallet", () -> new MalletItem(new Item.Properties()));

    public static void init(IEventBus bus) {
        ITEMS.register(bus);
        CREATIVE_MODE_TABS.register(bus);
        CREATIVE_MODE_TABS.register("misoundaddon_tab", () -> {
            CreativeModeTab.Builder builder = CreativeModeTab.builder();
            builder.displayItems((itemDisplay, output) -> {
                Set<Item> addedItems = new HashSet<>();

                ModItems.ITEMS.getEntries()
                        .stream()
                        .map((item) -> item.get().asItem())
                        .filter(addedItems::add)
                        .forEach(output::accept);

            });
            builder.icon(() -> new ItemStack(ModItems.MALLET_ITEM.get()));
            builder.title(Component.translatable(CREATIVE_MODE_TAB_TITLE));

            return builder.build();
        });
    }
}
