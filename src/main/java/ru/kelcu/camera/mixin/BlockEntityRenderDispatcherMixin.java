package ru.kelcu.camera.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
//#if MC >= 12109
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.state.CameraRenderState;
//#endif
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.kelcu.camera.CameraManager;

@Mixin(BlockEntityRenderDispatcher.class)
public class BlockEntityRenderDispatcherMixin {
    //#if MC < 12109
    //$$ @Inject(method = "render", at=@At("HEAD"), cancellable = true)
    //$$ public void render(BlockEntity blockEntity, float f, PoseStack poseStack, MultiBufferSource multiBufferSource, CallbackInfo ci){
    //$$     if(CameraManager.isCameraMode && CameraManager.currentCamera != null){
    //$$         if(blockEntity == CameraManager.currentCamera.blockEntity()) ci.cancel();
    //$$     }
    //$$ }
    //#else
    @Inject(method = "submit", at=@At("HEAD"), cancellable = true)
    public void render(BlockEntityRenderState blockEntityRenderState, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState cameraRenderState, CallbackInfo ci){
        if(CameraManager.isCameraMode && CameraManager.currentCamera != null){
            if(blockEntityRenderState.blockPos == CameraManager.currentCamera.blockEntity().getBlockPos()) ci.cancel();
        }
    }
    //#endif
}
