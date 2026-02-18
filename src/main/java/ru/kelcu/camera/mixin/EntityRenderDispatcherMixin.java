package ru.kelcu.camera.mixin;

import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;

//#if MC >= 12109
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.state.CameraRenderState;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
//#endif
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.kelcu.camera.CameraHead;
import ru.kelcuprum.alinlib.AlinLib;
import ru.kelcu.camera.CameraManager;

@Mixin(EntityRenderDispatcher.class)
public class EntityRenderDispatcherMixin {

    //#if MC >= 12109
    @Inject(method = "submit", at=@At("HEAD"), cancellable = true)
    public void disableStuckFeatureLayer(EntityRenderState entityRenderState, CameraRenderState cameraRenderState, double d, double e, double f, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CallbackInfo ci) {

    }
    //#endif
    @Inject(method = "shouldRender", at = @At("HEAD"), cancellable = true)
    private void onShouldRender(Entity entity, Frustum frustum, double x, double y, double z, CallbackInfoReturnable<Boolean> cir) {
        if ((entity == AlinLib.MINECRAFT.getCameraEntity() || entity == AlinLib.MINECRAFT.player) && CameraManager.isCameraMode) cir.setReturnValue(true);
//        if(entity == CameraHead.freeCamera) cir.setReturnValue(false);
    }
}
