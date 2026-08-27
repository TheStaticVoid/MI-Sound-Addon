package dev.thestaticvoid.mi_sound_addon.item;

import dev.thestaticvoid.mi_sound_addon.MISA;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class MISAItem {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MISA.ID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MISA.ID);

    public static final DeferredItem<Item> MALLET = ITEMS.register(
            "mallet", () -> new MalletItem(new Item.Properties())
    );
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MISA_TAB = CREATIVE_MODE_TAB.register(
            "mi_sound_addon",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(MALLET.get()))
                    .title(Component.translatable("itemGroup.mi_sound_addon"))
                    .displayItems(((itemDisplayParameters, output) -> {
                        output.accept(MALLET);
                    }))
                    .build());

    public static void init(IEventBus bus) {
        ITEMS.register(bus);
        CREATIVE_MODE_TAB.register(bus);
    }
}
