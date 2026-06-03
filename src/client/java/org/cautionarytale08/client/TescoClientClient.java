package org.cautionarytale08.client;

import com.mojang.brigadier.arguments.StringArgumentType;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;


public class TescoClientClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, environment) -> {
            dispatcher.register(ClientCommandManager.literal("find")
                    .then(ClientCommandManager.argument("username", StringArgumentType.string())
                            .executes(new findPlayer()::findPlayerCommand)));
        });
	}
}