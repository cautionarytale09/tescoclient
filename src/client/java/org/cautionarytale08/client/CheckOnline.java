package org.cautionarytale08.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerInfo;

public class CheckOnline {
    public static boolean checkPlayerOnline(String username){
        PlayerInfo playerInfo = Minecraft.getInstance().getConnection().getPlayerInfo(username);
        if (playerInfo == null){
            return false;
        }
        return true;
    }
}
