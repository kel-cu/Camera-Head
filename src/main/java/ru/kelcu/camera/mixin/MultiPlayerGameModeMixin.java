package ru.kelcu.camera.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.client.multiplayer.prediction.PredictiveAction;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.Tag;
import net.minecraft.network.protocol.game.ServerboundUseItemOnPacket;
import net.minecraft.network.protocol.game.ServerboundUseItemPacket;
import net.minecraft.server.commands.data.BlockDataAccessor;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.kelcuprum.alinlib.AlinLib;
import ru.kelcu.camera.CameraManager;

import static java.lang.Integer.MIN_VALUE;

@Mixin(value = MultiPlayerGameMode.class, priority = MIN_VALUE)
public abstract class MultiPlayerGameModeMixin {
    @Shadow
    protected abstract void startPrediction(ClientLevel clientLevel, PredictiveAction predictiveAction);

    @Shadow
    @Final
    private Minecraft minecraft;
    @Unique
    private static boolean isFucking = false;
    @Unique
    private static long lastFucking = 0;

    @Inject(method = "useItemOn", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/MultiPlayerGameMode;startPrediction(Lnet/minecraft/client/multiplayer/ClientLevel;Lnet/minecraft/client/multiplayer/prediction/PredictiveAction;)V"), cancellable = true)
    public void useItemOn(LocalPlayer localPlayer, InteractionHand interactionHand, BlockHitResult blockHitResult, CallbackInfoReturnable<InteractionResult> cir) {
        if (localPlayer != AlinLib.MINECRAFT.player || !localPlayer.level().isClientSide) return;
        if (localPlayer.isSpectator()) return;
        BlockPos blockPos = blockHitResult.getBlockPos();
        BlockState blockState = localPlayer.level().getBlockState(blockPos);
        if (blockState.is(Blocks.PLAYER_HEAD) || blockState.is(Blocks.PLAYER_WALL_HEAD)) {
            BlockEntity blockEntity = localPlayer.level().getBlockEntity(blockPos);
            assert blockEntity != null;
            BlockDataAccessor dataAccessor = new BlockDataAccessor(blockEntity, blockPos);
            Tag tag = dataAccessor.getData().get("custom_name");
            if (tag != null) {
                String name = tag.asString().get();
                if (name.startsWith("monitor")) {
                    if (isFucking || System.currentTimeMillis() - lastFucking < 750) {
                        isFucking = false;
                        return;
                    }
                    isFucking = true;
                    lastFucking = System.currentTimeMillis();
                    String[] args = name.split(":");
                    if (args.length == 2) {
                        new Thread(() -> CameraManager.openMonitor(args[1], blockEntity, blockPos, localPlayer.level())).start();
                        startPrediction(localPlayer.clientLevel, id -> new ServerboundUseItemOnPacket(interactionHand, blockHitResult, id));
                        cir.setReturnValue(InteractionResult.SUCCESS);
                    }
                }
            }
        }
    }


    @Inject(method = "useItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/MultiPlayerGameMode;startPrediction(Lnet/minecraft/client/multiplayer/ClientLevel;Lnet/minecraft/client/multiplayer/prediction/PredictiveAction;)V"), cancellable = true)
    public void useItemOn(Player player, InteractionHand interactionHand, CallbackInfoReturnable<InteractionResult> cir) {
        if (player != AlinLib.MINECRAFT.player || !player.level().isClientSide) return;
        if (player.isSpectator()) return;
        if (player.getItemInHand(interactionHand).is(Items.PLAYER_HEAD)) {
            ItemStack item = player.getItemInHand(interactionHand);
            String name = item.getCustomName() != null ?  item.getCustomName().getString() : item.getItemName().getString();
            if (name.startsWith("monitor")) {
                if (isFucking || System.currentTimeMillis() - lastFucking < 500) {
                    isFucking = false;
                    cir.setReturnValue(InteractionResult.PASS);
                    return;
                }
                isFucking = true;
                lastFucking = System.currentTimeMillis();
                String[] args = name.split(":");
                if (args.length == 2) {
                    new Thread(() -> CameraManager.openMonitor(args[1], null, player.getOnPos(), player.level(), 50)).start();
                    startPrediction(this.minecraft.level, id -> new ServerboundUseItemPacket(interactionHand, id, player.getYRot(), player.getXRot()));
                    cir.setReturnValue(InteractionResult.SUCCESS);
                }
            }

        }
    }
}
