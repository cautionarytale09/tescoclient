package org.cautionarytale08.client;

import com.google.gson.*;

import java.net.URI;

import static org.cautionarytale08.client.Http.sendPost;

public class getLocation {
    public static Gson gson = new Gson();
    public static JsonObject getTownInfo(String uuid) {
        String url = "https://api.earthmc.net/v4/towns";
        String body = "{\"query\":[\"%s\"]}".formatted(uuid);
        JsonElement response = sendPost(URI.create(url), body);
        if (response == null) {
            return null;
        }
        return response.getAsJsonArray().get(0).getAsJsonObject();
    }

    public static JsonArray getTownsInRadius(int x, int y, int radius) {
        String url = "https://api.earthmc.net/v4/nearby";
        String body = """
                {"query": [{"target_type": "COORDINATE", "target": [%d, %d], "search_type": "TOWN", "radius": %d}]}
                """.formatted(x, y, radius);
        JsonElement response = sendPost(URI.create(url), body);
        if (response == null) {
            return null;
        }
        return response.getAsJsonArray();
    }

    public static JsonArray getCloseTowns(int x, int y) {
        int radius = 0;
        int increase = 10;
        while (true) {
            JsonArray result = getTownsInRadius(x, y, radius);
            if (result != null) {
                return result;
            }
            radius = radius + increase;
            if (increase < 100) {
                increase = increase + 10;
            } else {
                increase = increase + 100;
            }
        }
    }

    public static JsonArray getClosePublicSpawnTowns(int x, int y) {
        int radius = 0;
        int increase = 10;
        while (true) {
            JsonArray result = getTownsInRadius(x, y, radius);
            if (result != null) {
                JsonArray towns = new JsonArray();
                for (JsonElement town : result) {
                    JsonObject townObject = town.getAsJsonArray().get(0).getAsJsonObject();
                    JsonObject townInfo = getTownInfo(townObject.getAsJsonObject().get("uuid").getAsString());
                    if (townInfo.get("status").getAsJsonObject().get("isPublic").getAsBoolean()) {
                        towns.add(townObject);
                    }
                }
                if (!towns.isEmpty()) {
                    return towns;
                }
            }
            radius = radius + increase;
            if (increase < 100) {
                increase = increase + 10;
            } else {
                increase = increase + 100;
            }
        }
    }
}