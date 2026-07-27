package dev.thestaticvoid.mi_sound_addon.mixin;

import aztech.modern_industrialization.MI;
import aztech.modern_industrialization.blocks.FastBlockEntity;
import aztech.modern_industrialization.blocks.WrenchableBlockEntity;
import aztech.modern_industrialization.machines.BEP;
import aztech.modern_industrialization.machines.MachineComponent;
import aztech.modern_industrialization.machines.MachineBlockEntity;
import aztech.modern_industrialization.machines.components.OrientationComponent;
import aztech.modern_industrialization.machines.gui.MachineGuiParameters;
import dev.thestaticvoid.mi_sound_addon.MISoundAddonConfig;
import dev.thestaticvoid.mi_sound_addon.item.MalletItem;
import dev.thestaticvoid.mi_sound_addon.sound.ModSounds;
import dev.thestaticvoid.mi_sound_addon.util.SilencedComponent;
import dev.thestaticvoid.mi_sound_addon.util.SilencedComponentInterface;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MachineBlockEntity.class)
public abstract class MachineBlockEntityMixin extends FastBlockEntity
        implements WrenchableBlockEntity, SilencedComponentInterface, MenuProvider {
    public MachineBlockEntityMixin(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Shadow(remap = false)
    protected abstract void registerComponents(MachineComponent... components);

    @Unique
    public SilencedComponent mI_Sound_Addon$silencedComp;

    @Inject(at = @At("TAIL"), method = "<init>", remap = false)
    private void constructorMixin(BEP bep, MachineGuiParameters guiParams, OrientationComponent.Params orientationParams, CallbackInfo ci) {
        mI_Sound_Addon$silencedComp = new SilencedComponent();
        registerComponents(mI_Sound_Addon$silencedComp);
    }

    @Inject(method = "useItemOn", at = @At("RETURN"), remap = false, cancellable = true)
    private void onUseMixin(Player player, InteractionHand hand, Direction face, CallbackInfoReturnable<ItemInteractionResult> cir) {
        ItemInteractionResult result = MalletItem.onUse((MachineBlockEntity)(Object)this, player, hand);
        if (result.consumesAction()) {
            mISoundAddon$toggleSilencedState();
            if (mI_Sound_Addon$silencedComp.silenced) {
                player.displayClientMessage(Component.translatable(MalletItem.MACHINE_SILENCED), true);
            } else {
                player.displayClientMessage(Component.translatable(MalletItem.MACHINE_UNSILENCED), true);
            }
        }
        cir.setReturnValue(result);
    }

    @Inject(method = "useWrench", at = @At("HEAD"), remap = false)
    private void useWrenchMixin(Player player, InteractionHand hand, BlockHitResult hitResult, CallbackInfoReturnable<Boolean> cir) {
        if (MISoundAddonConfig.CONFIG.wrenchSoundsEnabled.get()) {
            MachineBlockEntity blockEntity = ((MachineBlockEntity)(Object)this);
            ModSounds.playSoundNoRecipe(blockEntity, MI.id("wrench"));
        }
    }

    @Override
    public void mISoundAddon$toggleSilencedState() {
        mI_Sound_Addon$silencedComp.onMalletUse();
    }

    @Override
    public SilencedComponent mISoundAddon$getSilencedState() {
        return mI_Sound_Addon$silencedComp;
    }
}
