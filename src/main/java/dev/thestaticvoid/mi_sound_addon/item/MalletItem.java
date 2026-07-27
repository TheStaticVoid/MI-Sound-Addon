package dev.thestaticvoid.mi_sound_addon.item;

import aztech.modern_industrialization.machines.MachineBlockEntity;
import dev.thestaticvoid.mi_sound_addon.MISoundAddonConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.jspecify.annotations.NonNull;

import java.util.List;
import java.util.Objects;

public class MalletItem extends Item {
    public static final String TOOLTIP_TEXT = "message.mi_sound_addon.tooltip";
    public static final String MACHINE_SILENCED = "message.mi_sound_addon.machine_silenced";
    public static final String MACHINE_UNSILENCED = "message.mi_sound_addon.machine_unsilenced";

    public MalletItem(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(@NonNull ItemStack stack, @NonNull TooltipContext context, List<Component> tooltipComponents, @NonNull TooltipFlag isAdvanced) {
        tooltipComponents.add(Component.translatable(TOOLTIP_TEXT).withStyle(ChatFormatting.AQUA));
        super.appendHoverText(stack, context, tooltipComponents, isAdvanced);
    }

    public static ItemInteractionResult onUse(MachineBlockEntity be, Player player, InteractionHand hand) {
        ItemStack stackInHand = player.getItemInHand(hand);
        if (stackInHand.isEmpty()) {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }

        if (stackInHand.getItem() instanceof MalletItem) {
            Objects.requireNonNull(be.getLevel()).playSound(null, be.getBlockPos(), SoundEvents.ANVIL_USE, SoundSource.BLOCKS, (float)MISoundAddonConfig.CONFIG.wrenchVolume.get().doubleValue(), 1.0f);
            return ItemInteractionResult.SUCCESS;
        }
        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }
}
