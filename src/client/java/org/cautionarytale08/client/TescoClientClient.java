package org.cautionarytale08.client;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements;
import net.minecraft.resources.Identifier;
import org.cautionarytale08.TescoClient;


public class TescoClientClient implements ClientModInitializer {

    public void onInitializeClient() {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, environment) -> {
            dispatcher.register(ClientCommandManager.literal("find")
                    .then(ClientCommandManager.argument("username", StringArgumentType.string())
                        .executes(new findPlayer()::findPlayerThreadStarter)));
        });

        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            dispatcher.register(ClientCommandManager.literal("nearesttown")
                    .then(ClientCommandManager.argument("x", IntegerArgumentType.integer())
                                    .then(ClientCommandManager.argument("z", IntegerArgumentType.integer())
                                        .executes(new nearestTownsCommand()::nearestTownsCommandThreadStarter))));
        });
        HudElementRegistry.attachElementAfter(VanillaHudElements.CHAT, Identifier.fromNamespaceAndPath(TescoClient.MOD_ID, ""), HudWidget::extract);
    }
}
