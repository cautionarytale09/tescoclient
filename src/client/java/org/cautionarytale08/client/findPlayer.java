package org.cautionarytale08.client;

import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.network.chat.Component;

public class findPlayer {

    public int findPlayerCommand(CommandContext<FabricClientCommandSource> context) {
        SquaremapInstance squaremapInstance = new SquaremapInstance();
        ServerData serverData = Minecraft.getInstance().getCurrentServer();
        String host = Http.createUri(serverData.ip).toString();
        if (host.contains("earthmc.net")) {
            squaremapInstance.url = "https://map.earthmc.net";
            return earthmcFindPlayer.findPlayerCommandEarthmc(squaremapInstance, context);
        }

        context.getSource().sendError(Component.literal("TescoClient only works on EarthMC"));
        return 1;

    }
}
