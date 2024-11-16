package edu.bsu.cs.webapihandlers;

import edu.bsu.cs.JsonReader;
import edu.bsu.cs.records.GameStorage;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class GameHandler extends WebApiHandler {
    public static GameStorage getGameData(String gameTitle) throws IOException {
        gameTitle = gameTitle.replace(' ', '_');
        gameTitle = URLEncoder.encode(gameTitle, StandardCharsets.UTF_8);
        String gameDataLink = String.format("https://www.speedrun.com/api/v1/games/%s", gameTitle);

        return new JsonReader(establishConnection(gameDataLink)).createGame();
    }
}
