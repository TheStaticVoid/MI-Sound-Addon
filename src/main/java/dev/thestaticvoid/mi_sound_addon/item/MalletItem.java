package dev.thestaticvoid.mi_sound_addon.item;

import aztech.modern_industrialization.machines.MachineBlockEntity;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

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
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltipComponents, TooltipFlag isAdvanced) {
        tooltipComponents.add(Component.translatable(TOOLTIP_TEXT).withStyle(ChatFormatting.AQUA));
        super.appendHoverText(stack, level, tooltipComponents, isAdvanced);
    }

    public static InteractionResult onUse(MachineBlockEntity be, Player player, InteractionHand hand) {
        ItemStack stackInHand = player.getItemInHand(hand);
        if (stackInHand.isEmpty()) {
            return InteractionResult.PASS;
        }

        if (stackInHand.getItem() instanceof MalletItem) {
            Objects.requireNonNull(be.getLevel()).playSound(null, be.getBlockPos(), SoundEvents.ANVIL_USE, SoundSource.BLOCKS, 0.25f, 1.0f);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }
}
