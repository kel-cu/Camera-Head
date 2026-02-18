package ru.kelcu.camera.mixin;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(LivingEntityRenderState.class)
public interface LivingEntityRenderStateAccess {
    @Accessor()
    void setIsInvisibleToPlayer(boolean value);

}