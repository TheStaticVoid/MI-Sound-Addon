package dev.thestaticvoid.mi_sound_addon.event;

import aztech.modern_industrialization.MI;
import aztech.modern_industrialization.machines.MachineBlockEntity;
import dev.thestaticvoid.mi_sound_addon.MISoundAddon;
import dev.thestaticvoid.mi_sound_addon.MISoundAddonConfig;
import dev.thestaticvoid.mi_sound_addon.sound.ModSounds;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.swedz.extended_industrialization.EIComponents;

@EventBusSubscriber(modid = MISoundAddon.MOD_ID)
public class EntityPlaceEventHandler {
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onEntityPlaceEvent(BlockEvent.EntityPlaceEvent event) {
        if (!MISoundAddon.checkModIsLoaded("extended_industrialization")) return;
        if (!MISoundAddonConfig.CONFIG.configCardSoundsEnabled.get()) return;

        if (event.getEntity() instanceof Player player) {
            ItemStack offhand = player.getItemInHand(InteractionHand.OFF_HAND);
            if (offhand.has(EIComponents.MACHINE_CONFIG)) {
                BlockEntity blockEntity = event.getLevel().getBlockEntity(event.getPos());
                if (blockEntity instanceof MachineBlockEntity) {
                    if (MISoundAddonConfig.CONFIG.configCardSoundsEnabled.get()) {
                        ModSounds.playSoundNoRecipe(blockEntity, MI.id("config_card"));
                    }
                }
            }
        }
    }
}
