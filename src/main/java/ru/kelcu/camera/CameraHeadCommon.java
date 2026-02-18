package ru.kelcu.camera;

import com.google.common.collect.LinkedHashMultimap;
import com.google.gson.JsonObject;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.commands.Commands;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
//#if MC >= 12111
import net.minecraft.server.permissions.PermissionCheck;
import net.minecraft.server.permissions.Permissions;
//#endif
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.ResolvableProfile;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.UUID;

import static com.mojang.brigadier.arguments.StringArgumentType.getString;
import static com.mojang.brigadier.arguments.StringArgumentType.greedyString;
import static net.minecraft.commands.Commands.argument;
import static net.minecraft.commands.Commands.literal;

public class CameraHeadCommon implements ModInitializer {
    @Override
    public void onInitialize() {
        //#if MC >= 12109
        CommandRegistrationCallback.EVENT.register((dispatcher, s1, s2) -> {
            //#if MC >= 12111
            PermissionCheck.Require
                    //#else
                    //$$int
                    //#endif
            permissionCheck =
                    //#if MC >= 12111
                    new PermissionCheck.Require(Permissions.COMMANDS_ADMIN);
            //#else
            //$$ 4;
            //#endif
            dispatcher.register(literal("camheads")
                    .then(literal("camera").requires(Commands.hasPermission(permissionCheck)).then(argument("name", greedyString()).executes((s) -> {
                        ItemStack itemStack = Items.PLAYER_HEAD.getDefaultInstance();
                        itemStack.set(DataComponents.PROFILE, ResolvableProfile.createResolved(getCameraGameProfile()));
                        itemStack.set(DataComponents.CUSTOM_NAME, Component.literal(String.format("camera:%s", getString(s, "name"))));
                        s.getSource().getPlayer().getInventory().add(itemStack);
                        return 0;
                    })).executes((s) -> {
                        ItemStack itemStack = Items.PLAYER_HEAD.getDefaultInstance();
                        itemStack.set(DataComponents.PROFILE, ResolvableProfile.createResolved(getCameraGameProfile()));
                        itemStack.set(DataComponents.CUSTOM_NAME, Component.literal("camera:default"));
                        s.getSource().getPlayer().getInventory().add(itemStack);
                        return 0;
                    }))
                    .then(literal("monitor").requires(Commands.hasPermission(permissionCheck)).then(argument("name", greedyString()).executes((s) -> {
                        ItemStack itemStack = Items.PLAYER_HEAD.getDefaultInstance();
                        itemStack.set(DataComponents.PROFILE, ResolvableProfile.createResolved(getPCGameProfile()));
                        itemStack.set(DataComponents.CUSTOM_NAME, Component.literal(String.format("monitor:%s", getString(s, "name"))));
                        s.getSource().getPlayer().getInventory().add(itemStack);
                        return 0;
                    })).executes((s) -> {
                        ItemStack itemStack = Items.PLAYER_HEAD.getDefaultInstance();
                        itemStack.set(DataComponents.PROFILE, ResolvableProfile.createResolved(getPCGameProfile()));
                        itemStack.set(DataComponents.CUSTOM_NAME, Component.literal("monitor:default"));
                        s.getSource().getPlayer().getInventory().add(itemStack);
                        return 0;
                    }))
            );
        });
        //#endif
    }

    //#if MC >= 12109
    public static UUID pcUUID = UUID.randomUUID();

    public static GameProfile getPCGameProfile() {
        GameProfile gameProfile = new GameProfile(pcUUID, "PC");
        var propertiesMap = gameProfile.properties();
        var newProperties = LinkedHashMultimap.create(propertiesMap);
        newProperties.removeAll("textures");
        JsonObject textures = new JsonObject();
        JsonObject skin = new JsonObject();
        JsonObject playerData = new JsonObject();

        skin.addProperty("url", "http://textures.minecraft.net/texture/6f368a86cffd29ea25b1ab7e55ac1d0494f726a853fdb4042e7c71cd06b96d37");
        textures.add("SKIN", skin);
        playerData.add("textures", textures);

        String base = Base64.getEncoder().encodeToString(playerData.toString().getBytes(StandardCharsets.UTF_8));
        newProperties.put("textures", new Property("textures", base));
        gameProfile = new GameProfile(gameProfile.id(), gameProfile.name(), new PropertyMap(newProperties));
        return gameProfile;
    }

    public static UUID cameraUUID = UUID.randomUUID();

    public static GameProfile getCameraGameProfile() {
        GameProfile gameProfile = new GameProfile(cameraUUID, "Camera");
        var propertiesMap = gameProfile.properties();
        var newProperties = LinkedHashMultimap.create(propertiesMap);
        newProperties.removeAll("textures");
        JsonObject textures = new JsonObject();
        JsonObject skin = new JsonObject();
        JsonObject playerData = new JsonObject();

        skin.addProperty("url", "http://textures.minecraft.net/texture/1730350b694c1cd6d04f2d7c1bfc8f210697ab47d83437902abb111269bc12d0");
        textures.add("SKIN", skin);
        playerData.add("textures", textures);

        String base = Base64.getEncoder().encodeToString(playerData.toString().getBytes(StandardCharsets.UTF_8));
        newProperties.put("textures", new Property("textures", base));
        gameProfile = new GameProfile(gameProfile.id(), gameProfile.name(), new PropertyMap(newProperties));
        return gameProfile;
    }
    //#endif
}
