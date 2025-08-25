package ru.kelcu.camera.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.kelcu.camera.CameraManager;
import ru.kelcu.camera.gui.CameraScreen;

@Mixin(Minecraft.class)
public class MinecraftMixin {
    @Inject(method = {"setScreen"}, at = @At("HEAD"))
    private void setScreen(Screen screen, CallbackInfo ci) {
        if(CameraManager.isCameraMode && !(screen instanceof CameraScreen)) CameraManager.setCamera(null);
    }
    @Inject(method = "disconnect", at=@At("HEAD"))
    public void disconnect(Screen screen, boolean bl, CallbackInfo ci){
        if(CameraManager.isCameraMode) CameraManager.setCamera(null);
    }
}
