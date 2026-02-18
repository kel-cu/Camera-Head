package ru.kelcu.camera.mixin;

import net.minecraft.client.KeyboardHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.kelcuprum.alinlib.AlinLib;
import ru.kelcu.camera.CameraManager;

@Mixin(LivingEntityRenderer.class)
public class LivingEntityRendererMixin {

    @Inject(
            at = @At("HEAD"),
            method = "shouldShowName(Lnet/minecraft/world/entity/LivingEntity;D)Z",
            cancellable = true
    )
    private void viewOwnLabel(LivingEntity ent, double d, CallbackInfoReturnable<Boolean> ci) {
        if (ent == AlinLib.MINECRAFT.getCameraEntity() && CameraManager.isCameraMode) ci.setReturnValue(Minecraft.renderNames());
    }
}
