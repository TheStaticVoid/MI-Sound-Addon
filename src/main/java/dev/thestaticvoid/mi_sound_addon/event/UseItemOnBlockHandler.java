package dev.thestaticvoid.mi_sound_addon.event;

import aztech.modern_industrialization.MITags;
import aztech.modern_industrialization.machines.MachineBlockEntity;
import dev.thestaticvoid.mi_sound_addon.MISA;
import dev.thestaticvoid.mi_sound_addon.MISAConfig;
import dev.thestaticvoid.mi_sound_addon.MISATag;
import dev.thestaticvoid.mi_sound_addon.client.component.MachineSoundComponent;
import dev.thestaticvoid.mi_sound_addon.component.SilencedComponent;
import dev.thestaticvoid.mi_sound_addon.item.MISAItem;
import dev.thestaticvoid.mi_sound_addon.sound.MISASound;
import net.minecraft.network.chat.Component;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.UseItemOnBlockEvent;

@EventBusSubscriber(modid = MISA.ID)
public class UseItemOnBlockHandler {

    @SubscribeEvent
    public static void onPlayerUseItem(UseItemOnBlockEvent event) {
        if (!event.getLevel().isClientSide()) {
            UseOnContext context = event.getUseOnContext();

            if (context.getItemInHand().is(MISAItem.MALLET)) {
                handleUseMallet(event);
                return;
            }

            if (context.getItemInHand().is(MITags.WRENCHES)) {
                handleUseWrench(event);
                return;
            }

            if (context.getItemInHand().is(MISATag.CONFIG_CARDS)) {
                handleUseConfigCard(event);
                return;
            }

            if (context.getItemInHand().is(MISATag.UPGRADES)) {
                handleUseUpgrade(event);
                return;
            }
        }
    }

    private static void handleUseMallet(UseItemOnBlockEvent event) {
        Level level = event.getLevel();
        UseOnContext context = event.getUseOnContext();

        if (level.getBlockEntity(context.getClickedPos()) instanceof MachineBlockEntity machineEntity) {
            var comp = machineEntity.components.getOrThrow(SilencedComponent.class);
            comp.onMalletUse(machineEntity);

            if (MISAConfig.CONFIG.wrenchSoundsEnabled.get()) {
                MISASound.playMalletSound(level, context.getClickedPos());
            }

            if (context.getPlayer() == null) return;
            Component message = comp.silenced ?
                    Component.translatable("message.mi_sound_addon.machine_silenced") :
                    Component.translatable("message.mi_sound_addon.machine_unsilenced");

            context.getPlayer().displayClientMessage(message, true);

            event.cancelWithResult(ItemInteractionResult.SUCCESS);
        }
    }

    private static void handleUseWrench(UseItemOnBlockEvent event) {

    }

    private static void handleUseConfigCard(UseItemOnBlockEvent event) {

    }

    private static void handleUseUpgrade(UseItemOnBlockEvent event) {

    }
}
