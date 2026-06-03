package org.cautionarytale08.client;

import com.google.gson.JsonObject;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.phys.shapes.MinecartCollisionContext;

import java.util.List;
import java.util.Map;

public class earthmcFindPlayer {

    static int findPlayerCommandEarthmc(SquaremapInstance squaremapInstance, CommandContext<FabricClientCommandSource> context){String playerArgs = StringArgumentType.getString(context, "username");
        Minecraft minecraft = Minecraft.getInstance();
        if (!CheckOnline.checkPlayerOnline(playerArgs)){
            context.getSource().sendError(Component.literal(playerArgs + " is not online"));
            return 1;
        }

        List playersList = squaremapInstance.players.getPlayersList();
        String argsUsername = StringArgumentType.getString(context, "username");
        Map playerCoords = squaremapInstance.players.findPlayerByUsername(argsUsername, playersList);

        if (playerCoords == null){
            context.getSource().sendError(Component.literal(playerArgs + " is not visible on the map"));
            return 1;
        }

        String response = argsUsername;
        for (Object element : playerCoords.keySet()){
            response = response + "\n - " + element + " = " + playerCoords.get(element);
        }
        String finalResponse = response;
        minecraft.execute(() -> {context.getSource().sendFeedback(Component.literal(finalResponse));});
        return 1;

    }
}
