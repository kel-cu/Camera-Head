package ru.kelcu.camera.mixin;

import net.minecraft.client.CameraType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.kelcu.camera.CameraManager;
import ru.kelcuprum.alinlib.AlinLib;

@Mixin(CameraType.class)
public class CameraTypeMixin {
    @Inject(method = "isFirstPerson", at = @At("HEAD"), cancellable = true)
    public void isFirstPerson(CallbackInfoReturnable<Boolean> cir){
        if (CameraManager.isCameraMode) cir.setReturnValue(false);
    }
}
