package org.cautionarytale08.client;

import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.network.chat.Component;



class findPlayerThread implements Runnable {
    private CommandContext<FabricClientCommandSource> context;

    public findPlayerThread(CommandContext<FabricClientCommandSource> context) {
        this.context = context;
    }

    public void run() {
        SquaremapInstance squaremapInstance = new SquaremapInstance();
        ServerData serverData = Minecraft.getInstance().getCurrentServer();
        String host = Http.createUri(serverData.ip).toString();
        if (host.contains("earthmc.net")) {
            squaremapInstance.url = "https://map.earthmc.net";
            earthmcFindPlayer.findPlayerCommandEarthmc(squaremapInstance, context);
        } else {
            context.getSource().sendError(Component.literal("TescoClient only works on EarthMC"));
        }
    }
}

public class findPlayer {
    static CommandContext<FabricClientCommandSource> commandContext;
    public int findPlayerThreadStarter(CommandContext<FabricClientCommandSource> context){
        commandContext = context;

        findPlayerThread findPlayerThread = new findPlayerThread(context);

        // initializing Thread Object
        Thread thread = new Thread(findPlayerThread);

        // Running Thread
        thread.start();
        
        return 1;
    }
}
