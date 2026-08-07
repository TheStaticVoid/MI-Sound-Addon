package dev.thestaticvoid.mi_sound_addon.event;

import aztech.modern_industrialization.MI;
import aztech.modern_industrialization.machines.MachineBlockEntity;
import dev.thestaticvoid.mi_sound_addon.MISoundAddon;
import dev.thestaticvoid.mi_sound_addon.MISoundAddonConfig;
import dev.thestaticvoid.mi_sound_addon.sound.ModSounds;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.UseItemOnBlockEvent;
import net.swedz.extended_industrialization.EIComponents;

@EventBusSubscriber(modid = MISoundAddon.MOD_ID)
public class UseItemOnBlockHandler {
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onPlayerUseItem(UseItemOnBlockEvent event) {
        if (!MISoundAddon.checkModIsLoaded("extended_industrialization")) return;
        if (!MISoundAddonConfig.CONFIG.configCardSoundsEnabled.get()) return;


        UseOnContext context = event.getUseOnContext();
        if (!context.getLevel().isClientSide() && context.getPlayer() != null) {
            BlockEntity hitBlockEntity = context.getLevel().getBlockEntity(context.getClickedPos());
            if (hitBlockEntity instanceof MachineBlockEntity) {
                if (context.getPlayer().isShiftKeyDown()) {
                    ModSounds.playSoundNoRecipe(hitBlockEntity, MI.id("config_card"));
                } else if (context.getPlayer().getItemInHand(context.getHand()).has(EIComponents.MACHINE_CONFIG)) {
                    ModSounds.playSoundNoRecipe(hitBlockEntity, MI.id("config_card"));
                }
            }
        }
    }
}
