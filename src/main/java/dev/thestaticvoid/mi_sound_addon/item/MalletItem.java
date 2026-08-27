package dev.thestaticvoid.mi_sound_addon.item;

import aztech.modern_industrialization.machines.MachineBlockEntity;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.NonNull;

import java.util.List;

public class MalletItem extends Item {
    public MalletItem(Properties properties) {
        super(properties.stacksTo(1));
    }

    @Override
    public void appendHoverText(
            @NonNull ItemStack stack,
            @NonNull TooltipContext context,
            List<Component> tooltipComponents,
            @NonNull TooltipFlag isAdvanced) {

        tooltipComponents.add(Component.translatable("message.mi_sound_addon.tooltip").withStyle(ChatFormatting.AQUA));
        super.appendHoverText(stack, context, tooltipComponents, isAdvanced);
    }
}
