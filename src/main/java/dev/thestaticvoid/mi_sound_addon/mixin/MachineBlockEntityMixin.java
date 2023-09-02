package dev.thestaticvoid.mi_sound_addon.mixin;

import aztech.modern_industrialization.api.FastBlockEntity;
import aztech.modern_industrialization.api.WrenchableBlockEntity;
import aztech.modern_industrialization.machines.BEP;
import aztech.modern_industrialization.machines.IComponent;
import aztech.modern_industrialization.machines.MachineBlockEntity;
import aztech.modern_industrialization.machines.components.OrientationComponent;
import aztech.modern_industrialization.machines.gui.MachineGuiParameters;
import dev.thestaticvoid.mi_sound_addon.MISoundAddon;
import dev.thestaticvoid.mi_sound_addon.item.MalletItem;
import dev.thestaticvoid.mi_sound_addon.util.SilencedComponent;
import dev.thestaticvoid.mi_sound_addon.util.SilencedComponentInterface;
import net.fabricmc.fabric.api.rendering.data.v1.RenderAttachmentBlockEntity;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MachineBlockEntity.class)
public abstract class MachineBlockEntityMixin extends FastBlockEntity
        implements ExtendedScreenHandlerFactory, RenderAttachmentBlockEntity, WrenchableBlockEntity, SilencedComponentInterface {

    public MachineBlockEntityMixin(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Shadow(remap = false)
    protected abstract void registerComponents(IComponent... components);

    @Unique
    public SilencedComponent silencedComp;

    @Inject(at = @At("TAIL"), method = "<init>", remap = false)
    private void constructorMixin(BEP bep, MachineGuiParameters guiParams, OrientationComponent.Params orientationParams, CallbackInfo ci) {
        silencedComp = new SilencedComponent();
        registerComponents(silencedComp);
    }

    @Inject(method = "onUse", at = @At("RETURN"), remap = false, cancellable = true)
    private void onUseMixin(Player player, InteractionHand hand, Direction face, CallbackInfoReturnable<InteractionResult> cir) {
        InteractionResult result = MalletItem.onUse((MachineBlockEntity)(Object)this, player, hand);
        if (result.consumesAction()) {
            mISoundAddon$toggleSilencedState();
            if (silencedComp.silenced) {
                player.displayClientMessage(Component.translatable(MalletItem.MACHINE_SILENCED), true);
            } else {
                player.displayClientMessage(Component.translatable(MalletItem.MACHINE_UNSILENCED), true);
            }
        }
        cir.setReturnValue(result);
    }

    @Override
    public void mISoundAddon$toggleSilencedState() {
        silencedComp.onMalletUse();
    }

    @Override
    public SilencedComponent mISoundAddon$getSilencedState() {
        return silencedComp;
    }
}
