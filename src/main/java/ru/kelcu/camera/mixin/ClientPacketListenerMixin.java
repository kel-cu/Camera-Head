package ru.kelcu.camera.mixin;

import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.protocol.game.ClientboundRespawnPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.kelcu.camera.CameraManager;

@Mixin(ClientPacketListener.class)
public class ClientPacketListenerMixin {
    @Inject(method = "handleRespawn", at=@At("HEAD"))
    public void respawn(ClientboundRespawnPacket clientboundRespawnPacket, CallbackInfo ci){
        if(CameraManager.isCameraMode) CameraManager.setCamera(null);
    }
}
