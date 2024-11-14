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

    public static GameStorage getGameData(String gameTitle) throws IOException {
        gameTitle = gameTitle.replace(' ', '_');
        gameTitle = URLEncoder.encode(gameTitle, StandardCharsets.UTF_8);
        String gameDataLink = String.format("https://www.speedrun.com/api/v1/games/%s", gameTitle);
        return new JsonReader(establishConnection(gameDataLink)).createGame();
    }

    public static List<CategoryStorage> getCategoryData(GameStorage game) throws IOException {
        return new JsonReader(establishConnection(game.linkToCategories())).createCategoryList();
    }
    public static List<LevelStorage> getLevelData(GameStorage game) throws IOException {
        return new JsonReader(establishConnection(game.linkToLevels())).createLevelList();
    }

    public static LeaderboardStorage getLeaderboardData(GameStorage game, CategoryStorage category, LevelStorage level, int maxRuns, String rawFilters) throws IOException {
        StringBuilder leaderboardLinkBuilder = new StringBuilder();
        if (rawFilters == null) {
            leaderboardLinkBuilder.append(String.format("https://www.speedrun.com/api/v1/leaderboards/%s/", game.id()));

            if (category.type().equals("per-level")) {
                if (level == null)
                    return null;

                leaderboardLinkBuilder.append(String.format("level/%s/%s", level.id(), category.id()));
            }
            else // (category.type().equals("per-game"))
                leaderboardLinkBuilder.append(String.format("category/%s", category.id()));

            return new JsonReader(establishConnection(leaderboardLinkBuilder.toString())).createLeaderboard(maxRuns);
        }
        else {
            leaderboardLinkBuilder.append(String.format("https://www.speedrun.com/api/v1/runs?game=%s", game.id()));
            leaderboardLinkBuilder.append(String.format("&category=%s", category.id()));

            if (level != null)
                leaderboardLinkBuilder.append(String.format("&level=%s", level.id()));

            leaderboardLinkBuilder.append(String.format("&%s", rawFilters));

            String weblink = String.format("https://www.speedrun.com/%s#%s", game.id(), category.id());
            String gameLink = game.selfLink();
            String categoryLink = category.selfLink();
            String levelLink = (level != null) ? level.selflink() : null;
            List<RunStorage> runs = new JsonReader(establishConnection(leaderboardLinkBuilder.toString())).createRunList(maxRuns, true);

            return new LeaderboardStorage(weblink, gameLink, categoryLink, levelLink, null, runs);
        }
    }

    public static RunStorage getRunData(String runId, int place) throws IOException {
        String runLink = String.format("https://www.speedrun.com/api/v1/runs/%s", runId);
        return new JsonReader(establishConnection(runLink)).createRun(place);
    }

    public static PlayerStorage getPlayerData(String playerlink) {
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
        catch (IOException EncodedPlayerIdIsMalformedException) {
            return new PlayerStorage(null, null, null, "<Guest>");
        }
    }
}
