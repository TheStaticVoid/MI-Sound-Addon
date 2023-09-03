package dev.thestaticvoid.mi_sound_addon.mixin;

import aztech.modern_industrialization.blocks.forgehammer.ForgeHammerScreenHandler;
import dev.thestaticvoid.mi_sound_addon.MISoundAddonConfig;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.MenuType;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ForgeHammerScreenHandler.class)
public abstract class ForgeHammerScreenHandlerMixin extends AbstractContainerMenu {
    public ForgeHammerScreenHandlerMixin(@Nullable MenuType<?> menuType, int i) {
        super(menuType, i);
    }
    @Shadow(remap = false) @Final private ContainerLevelAccess context;

    @Inject(method = "onCraft", at = @At("HEAD"), remap = false)
    private void onCraftMixin(CallbackInfo ci) {
        context.execute((world, pos) -> {
            world.playSound(null, pos, SoundEvents.SMITHING_TABLE_USE, SoundSource.BLOCKS, MISoundAddonConfig.getConfig().forgeHammerVolume, 1.0f);
        });
    }
}
