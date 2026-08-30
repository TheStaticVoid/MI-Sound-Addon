package dev.thestaticvoid.mi_sound_addon;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class MISATag {
    public static final TagKey<Item> CONFIG_CARDS = item("tools/config_cards");
    public static final TagKey<Item> UPGRADES = item("upgrades");
    public static final TagKey<Item> DRILLS = item("tools/drills");
    public static final TagKey<Item> CHAINSAWS = item("tools/chainsaws");

    public static TagKey<Item> item(String path) {
        return TagKey.create(BuiltInRegistries.ITEM.key(), MISA.id(path));
    }
}
