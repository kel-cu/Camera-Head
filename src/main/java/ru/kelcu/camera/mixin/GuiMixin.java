package ru.kelcu.camera.mixin;

import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.kelcu.camera.CameraManager;

@Mixin(Gui.class)
public abstract class GuiMixin {
    @Shadow
    protected abstract void renderVignette(GuiGraphics guiGraphics, @Nullable Entity entity);

    @Shadow
    @Final
    private Minecraft minecraft;

    @Inject(method = "render", at=@At("HEAD"), cancellable = true)
    public void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker, CallbackInfo ci){
        if(!CameraManager.isCameraMode) return;
        if (
                //#if MC >= 12111
                this.minecraft.options.vignette().get()
                //#else
                //$$Minecraft.useFancyGraphics()
                //#endif
        ) {
            this.renderVignette(guiGraphics, this.minecraft.getCameraEntity());
        }
        ci.cancel();
    }
}
