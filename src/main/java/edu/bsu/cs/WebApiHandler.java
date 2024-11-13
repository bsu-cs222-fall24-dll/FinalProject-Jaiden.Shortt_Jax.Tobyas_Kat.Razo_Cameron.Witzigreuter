package edu.bsu.cs;

import edu.bsu.cs.records.*;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;


public class WebApiHandler {

    private static String establishConnection(String url) throws IOException {
        URL fetchUrl = URI.create(url).toURL();
        URLConnection connection = fetchUrl.openConnection();
        connection.setRequestProperty("User-Agent", "Speedrun Tracker/1.0.0 (makenzie.tobyas@bsu.edu + cameron.witzigreuter@bsu.edu + connor.razo@bsu.edu + haylee.shortt@bsu.edu)");
        InputStream inputStream = connection.getInputStream();
        return IOUtils.toString(inputStream, StandardCharsets.UTF_8);
    }

    public static GameStorage getGameData (String gameTitle) throws IOException {
        gameTitle = gameTitle.replace(' ', '_');
        gameTitle = URLEncoder.encode(gameTitle, StandardCharsets.UTF_8);
        String gameDataLink = String.format("https://www.speedrun.com/api/v1/games/%s", gameTitle);
        return new JsonReader(establishConnection(gameDataLink)).createGame();
    }

    public static List<CategoryStorage> getCategoryData (GameStorage game) throws IOException {
        return new JsonReader(establishConnection(game.linkToCategories())).createCategoryList();
    }

    public static LeaderboardStorage getLeaderboardData (CategoryStorage category, int maxRuns) throws IOException {
        return new JsonReader(establishConnection(category.linkToLeaderboard())).createLeaderboard(maxRuns);
    }

    public static RunStorage getRunData (String runId) throws IOException {
        String runLink = String.format("https://www.speedrun.com/api/v1/runs/%s", runId);
        return new JsonReader(establishConnection(runLink)).createRun();
    }

    public static PlayerStorage getPlayerData (String playerlink) {
        try {
            String playerJson = establishConnection(playerlink);
            JsonReader playerReader = new JsonReader(playerJson);

            if (playerJson.contains("names"))
                return playerReader.createUser();
            else if (playerJson.contains("name"))
                return playerReader.createGuest();
            else
                return new PlayerStorage(null, null, null, "<not found>");
        }
        catch (IOException EncodedPlayerIdYieldsNoPlayerException) {
            return new PlayerStorage(null, null, null, "<id error>");
        }
    }
}
