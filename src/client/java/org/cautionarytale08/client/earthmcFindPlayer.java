package org.cautionarytale08.client;

import com.google.gson.JsonObject;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.shapes.MinecartCollisionContext;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

import static net.minecraft.world.entity.EntityReference.getPlayer;

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


        MessageFormat messageFormat = new MessageFormat("{0}\n- world = {1}\n- x = {2}\n- y = {3}\n- z = {4}\n- yaw = {5}");
        String result = messageFormat.format(new Object[]{playerArgs, playerCoords.get("world"), playerCoords.get("x"), playerCoords.get("y"), playerCoords.get("z"), playerCoords.get("yaw")});

        minecraft.execute(() -> {context.getSource().sendFeedback(Component.literal(result));});
        return 1;

    }
}
