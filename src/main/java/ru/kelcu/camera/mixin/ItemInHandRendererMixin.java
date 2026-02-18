package ru.kelcu.camera.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
//#if MC < 12109
import net.minecraft.client.renderer.MultiBufferSource;
//#else
import net.minecraft.client.renderer.SubmitNodeCollector;
//#endif
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.kelcu.camera.CameraManager;

@Mixin(ItemInHandRenderer.class)
public class ItemInHandRendererMixin {

    @Inject(method = "renderMapHand", at = @At("HEAD"), cancellable = true)
    public void renderItem(PoseStack poseStack,
                           //#if MC < 12109
                           //$$ MultiBufferSource
                           //#else
                           SubmitNodeCollector
                                       //#endif
                                       buffer, int combinedLight, HumanoidArm side, CallbackInfo ci) {
        if(CameraManager.isCameraMode) ci.cancel();
    }
    @Inject(method = "renderOneHandedMap", at = @At("HEAD"), cancellable = true)
    public void renderItem(PoseStack poseStack,
                           //#if MC < 12109
                           //$$ MultiBufferSource
                           //#else
                           SubmitNodeCollector
                                       //#endif
                                       buffer, int combinedLight, float equippedProgress, HumanoidArm hand, float swingProgress, ItemStack stack, CallbackInfo ci) {
        if(CameraManager.isCameraMode) ci.cancel();
    }
    @Inject(method = "renderTwoHandedMap", at = @At("HEAD"), cancellable = true)
    public void renderItem(PoseStack poseStack,
                           //#if MC < 12109
                           //$$ MultiBufferSource
                           //#else
                           SubmitNodeCollector
                                       //#endif
                                       buffer, int combinedLight, float pitch, float equippedProgress, float swingProgress, CallbackInfo ci) {
        if(CameraManager.isCameraMode) ci.cancel();
    }
    @Inject(method = "renderMap", at = @At("HEAD"), cancellable = true)
    public void renderItem(PoseStack poseStack,
                           //#if MC < 12109
                           //$$ MultiBufferSource
                           //#else
                           SubmitNodeCollector
                                       //#endif
                                       buffer, int combinedLight, ItemStack stac, CallbackInfo ci) {
        if(CameraManager.isCameraMode) ci.cancel();
    }
    @Inject(method = "renderPlayerArm", at = @At("HEAD"), cancellable = true)
    public void renderItem(PoseStack poseStack,
                           //#if MC < 12109
                           //$$ MultiBufferSource
                           //#else
                           SubmitNodeCollector
                                       //#endif
                                       buffer, int combinedLight, float equippedProgress, float swingProgress, HumanoidArm side, CallbackInfo ci) {
        if(CameraManager.isCameraMode) ci.cancel();
    }
    @Inject(method = "renderHandsWithItems", at = @At("HEAD"), cancellable = true)
    public void renderItem(float partialTicks, PoseStack poseStack,
                           //#if MC < 12109
                           //$$ MultiBufferSource.BufferSource
                           //#else
                           SubmitNodeCollector
                                       //#endif
                                       buffer, LocalPlayer playerEntity, int combinedLight, CallbackInfo ci) {
        if(CameraManager.isCameraMode) ci.cancel();
    }
    @Inject(method = "renderArmWithItem", at = @At("HEAD"), cancellable = true)
    public void renderItem(AbstractClientPlayer player, float partialTicks, float pitch, InteractionHand hand, float swingProgress, ItemStack stack, float equippedProgress, PoseStack poseStack,
                           //#if MC < 12109
                           //$$ MultiBufferSource
                           //#else
                           SubmitNodeCollector
                                   //#endif
                                   buffer, int combinedLight, CallbackInfo ci) {
        if(CameraManager.isCameraMode) ci.cancel();
    }
}
