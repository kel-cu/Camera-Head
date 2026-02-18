package ru.kelcu.camera;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.client.CameraType;
import net.minecraft.commands.Commands;
import ru.kelcuprum.alinlib.AlinLogger;

import static net.minecraft.commands.Commands.argument;
import static net.minecraft.commands.Commands.literal;

public class CameraHead implements ClientModInitializer {
    public static final AlinLogger LOG = new AlinLogger("Camera Head");
    public static CameraType rememberedF5;
    @Override
    public void onInitializeClient() {
    }
}
