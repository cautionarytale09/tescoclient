package org.cautionarytale08.client;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.MessageFormat;
import java.util.*;

import static org.cautionarytale08.client.Json.toJson;


class Json {
    static JsonObject toJson(String string){
        JsonElement jsonElement = JsonParser.parseString(string);
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        return jsonObject;
    }
}

class Http {
    static URI createUri(String string) {
        try {
            return new URI(string);
        } catch (Exception e) {
            return null;
        }
    }

    static HttpResponse sendGet(URI uri) {
        try {
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(uri)
                    .GET()
                    .build();
            HttpClient httpClient = HttpClient.newBuilder()
                    .build();
            HttpResponse response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            return response;

        } catch (Exception e) {
            return null;
        }
    }

    static JsonElement sendPost(URI uri, String body) {
        try {
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(uri)
                    .POST(HttpRequest.BodyPublishers.ofString(body))
                    .build();
            try {
                HttpClient httpClient = HttpClient.newBuilder()
                        .build();
                HttpResponse response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
                JsonElement jsonObject = toJson(response.body().toString());
                return jsonObject;

            } catch (Exception e) {
                return null;
            }

        } catch (Exception e) {
            return null;
        }
    }

    static HttpResponse sendGetBytes(URI uri) {
        try {
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(uri)
                    .GET()
                    .build();
            HttpClient httpClient = HttpClient.newBuilder()
                    .build();
            HttpResponse response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofByteArray());
            return response;

        } catch (Exception e) {
            return null;
        }
    }
}

class Players {
    SquaremapInstance squaremapInstance;

    void setSquaremapInstance(SquaremapInstance squaremapInstance) {
        this.squaremapInstance = squaremapInstance;
    }

    public List getPlayersList() {
        String urlString = this.squaremapInstance.url.toString() + "/tiles/players.json";
        HttpResponse httpResponse = Http.sendGet(Http.createUri(urlString));
        String responseBody = httpResponse.body().toString();
        JsonObject responseJson = toJson(responseBody);
        JsonElement playersListJson = responseJson.get("players");
        List playersList = playersListJson.getAsJsonArray().asList();
        return playersList;
    }

    public Map findPlayerByUsername(String username, List coordList) {
        for (Object player : coordList) {
            if (!(player instanceof JsonElement)) {
                return null;
            }
            JsonObject playerJson = ((JsonElement) player).getAsJsonObject();
             String playerName = playerJson.get("name").getAsString();
             if (playerName.equals(username)) {
                 return playerJson.asMap();
             }

        }
        return null;
    }
}

class SquaremapInstance {
    String url;
    Players players = new Players();

    void setPlayersParent() {
        players.squaremapInstance = this;
    }

    { setPlayersParent(); }

    public void setUrl(String string) {
        this.url = string;
    }

}
