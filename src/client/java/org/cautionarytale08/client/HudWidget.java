package org.cautionarytale08.client;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import org.cautionarytale08.TescoClient;

import java.text.MessageFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.apache.commons.lang3.StringUtils.split;

public class HudWidget {
    static private JsonObject makeJsonObject(JsonPrimitive primitive){
        JsonObject tempJsonObject = new JsonObject();
        tempJsonObject.add("key", primitive);
        return tempJsonObject.getAsJsonObject("key");
    }
    static void renderText(String text, GuiGraphics graphics, int i, int j){
        String[] splitText = text.split("\\r?\\n");
        int spacing = 10;
        int iterations = 0;
        for (String line : splitText){
            int modifiedj = (j + (spacing * iterations));
//            int modifiedj = (j + (spacing * iterations));

            graphics.drawString(font, line, i, modifiedj, 0xFFFFFFFF);
            iterations = iterations + 1;

        }
    }

    static String text = "Not currently tracking any players";
    static String trackedPlayer = "cautionarytale08";
    static SquaremapInstance squaremapInstance = new SquaremapInstance();
    static Minecraft minecraft = Minecraft.getInstance();
    static float lastUpdate = minecraft.gui.getGuiTicks();
    static Date lastUpdateDate = new Date();
    static Integer x;
    static Integer y;
    static Integer z;
    static Font font = Minecraft.getInstance().font;

    static void updateText(){
        squaremapInstance.url = "https://map.earthmc.net";
        Minecraft minecraft = Minecraft.getInstance();
        if (!CheckOnline.checkPlayerOnline(trackedPlayer)){
            if (x != null && y != null){
                MessageFormat messageFormat = new MessageFormat("Player {0} is not online.\nLast seen at {1}, at the coords\nx = {2}\ny = {3}\n z = {4}");
                String result = messageFormat.format(new Object[]{trackedPlayer, lastUpdateDate, x, y, z});
                text = result;
            } else {
                text = "Player " + trackedPlayer + " is not online.";
                return;
            }
        }

        List playersList = squaremapInstance.players.getPlayersList();
        Map playerCoords = squaremapInstance.players.findPlayerByUsername(trackedPlayer, playersList);

        if (playerCoords == null){
            if (x != null && y != null){
                MessageFormat messageFormat = new MessageFormat("Player {0} is not visible on the map.\nLast seen at {1}, at the coords\nx = {2}\ny = {3}\n z = {4}");
                String result = messageFormat.format(new Object[]{trackedPlayer, lastUpdateDate, x, y, z});
                text = result;
            } else {
                text = "Player " + trackedPlayer + " is not visible on the map.";
                return;
            }
        }
//        x = makeJsonObject((JsonPrimitive) ).getAsInt();

        x = (int) playerCoords.get("x");
//        y = makeJsonObject((JsonPrimitive) playerCoords.get("y")).getAsInt();

//        z = makeJsonObject((JsonPrimitive) playerCoords.get("z")).getAsInt();

        lastUpdateDate = new Date();

        MessageFormat messageFormat = new MessageFormat("{0}\nx = {1}\n- y = {2}\n- z = {3}");
        String result = messageFormat.format(new Object[]{trackedPlayer, playerCoords.get("x"), playerCoords.get("y"), playerCoords.get("z")});
        text = result;
        return;
    }

    static void extract(GuiGraphics graphics, DeltaTracker tickCounter) {
        if ((minecraft.gui.getGuiTicks() - lastUpdate) >= 40) {
            updateText();
            lastUpdate = minecraft.gui.getGuiTicks();
        }
        renderText(text, graphics, 15, 15);
    }
}