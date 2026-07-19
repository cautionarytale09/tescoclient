package org.cautionarytale08.client;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

import java.util.List;

class nearestTownsCommandThread implements Runnable {
    private CommandContext<FabricClientCommandSource> context;

    public nearestTownsCommandThread(CommandContext<FabricClientCommandSource> context) {
        this.context = context;
    }

    String formatResponse(List<String> objects){
        String response = "Nearest towns\n";
        for (Object object : objects){
            response = response + " - " + object.toString() + "\n";
        }
        return response;
    }

    public void run() {
        Minecraft minecraft = Minecraft.getInstance();
        int x = IntegerArgumentType.getInteger(context, "x");
        int z = IntegerArgumentType.getInteger(context, "z");




        JsonArray closeTowns = getLocation.getClosePublicSpawnTowns(x,z);
        context.getSource().sendError(Component.literal(closeTowns.getAsString()));
        if (closeTowns.isEmpty()){
            context.getSource().sendError(Component.literal("No nearby towns"));
        } else {

            List<String> towns = new java.util.ArrayList<>(List.of());

            for (JsonElement town : closeTowns) {
                JsonObject townObject = town.getAsJsonObject();
                String townString;
                townString = townObject.get("name").getAsString();
                towns.add(townString);
            }

            minecraft.execute(() -> {context.getSource().sendFeedback(Component.literal(formatResponse(towns)));});

        }

    }
}

public class nearestTownsCommand {
    public int error(CommandContext<FabricClientCommandSource> context){
        context.getSource().sendError(Component.literal("Please supply 2 integer arguments."));
        return 1;
    }

    static CommandContext<FabricClientCommandSource> commandContext;
    public int nearestTownsCommandThreadStarter(CommandContext<FabricClientCommandSource> context){
        commandContext = context;

        nearestTownsCommandThread nearestTownsCommandThread = new nearestTownsCommandThread(context);

        // initializing Thread Object
        Thread thread = new Thread(nearestTownsCommandThread);

        // Running Thread
        thread.start();

        return 1;
    }
}
