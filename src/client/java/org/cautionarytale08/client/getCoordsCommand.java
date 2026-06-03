package org.cautionarytale08.client;

import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.network.chat.Component;

import java.util.List;
import java.util.Map;

public class getCoordsCommand {
    public int getCoordsCommand(CommandContext<FabricClientCommandSource> context) {
        SquaremapInstance squaremapInstance = new SquaremapInstance();

        squaremapInstance.url = "https://map.earthmc.net";

        List playersList = squaremapInstance.players.getPlayersList();

        Map coords = squaremapInstance.players.findPlayerByUsername("cautionarytale08", playersList);

//        context.getSource().sendFeedback(Component.literal(apiInterface.getAllCoords().toString()));
        context.getSource().sendFeedback(Component.literal(coords.toString()));
        return 1;
    }

//    public int getCoordsCommand(CommandContext<CommandSourceStack> commandSourceStackCommandContext) {
//    }
}
