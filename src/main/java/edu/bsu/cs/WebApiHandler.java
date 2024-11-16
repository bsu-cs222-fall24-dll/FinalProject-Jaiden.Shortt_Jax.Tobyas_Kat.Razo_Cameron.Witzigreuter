package edu.bsu.cs;

import edu.bsu.cs.records.*;
import org.apache.commons.io.IOUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
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

    public static LeaderboardStorage getLeaderboardData(GameStorage game, CategoryStorage category, LevelStorage level, int maxRuns) throws IOException {
        StringBuilder leaderboardLinkBuilder = new StringBuilder();
        boolean levelIncluded = level != null;

        leaderboardLinkBuilder.append(String.format("https://www.speedrun.com/api/v1/leaderboards/%s/", game.id()));

        if (category.type().equals("per-level") && levelIncluded)
            leaderboardLinkBuilder.append(String.format("level/%s/%s", level.id(), category.id()));
        else // (category.type().equals("per-game"))
            leaderboardLinkBuilder.append(String.format("category/%s", category.id()));

        return new JsonReader(establishConnection(leaderboardLinkBuilder.toString())).createLeaderboard(maxRuns, false);
    }
    public static LeaderboardStorage getLeaderboardData(GameStorage game, CategoryStorage category, LevelStorage level, int maxRuns, String runsListInstructions) throws IOException {
        StringBuilder runsListLinkBuilder = new StringBuilder("https://www.speedrun.com/api/v1/runs?");
        boolean levelIncluded = level != null;

        runsListLinkBuilder.append(String.format("game=%s", game.id()));
        runsListLinkBuilder.append(String.format("&category=%s", category.id()));
        if (levelIncluded) runsListLinkBuilder.append(String.format("&level=%s", level.id()));
        runsListLinkBuilder.append(String.format("&max=%d", maxRuns));
        runsListLinkBuilder.append("&status=verified");
        runsListLinkBuilder.append(String.format("&%s", runsListInstructions));

        List<RunStorage> runs =
                new JsonReader(establishConnection(runsListLinkBuilder.toString())).createRunList(maxRuns, true);

        List<String> runPlaces = new ArrayList<>();
        for (RunStorage ignored : runs)
            runPlaces.add("#");

        return new LeaderboardStorage(runs, runPlaces);
    }

    public static List<PlayerStorage> getPlayersFromLinksInRun(List<String> playerLinks) throws IOException {
        List<PlayerStorage> playersFromRun = new ArrayList<>();

        for (String playerLink : playerLinks)
            playersFromRun.add(WebApiHandler.getPlayerData(playerLink));

        return playersFromRun;
    }
    public static PlayerStorage getPlayerData(String playerLink) throws IOException {
        try {
            return new JsonReader(establishConnection(playerLink)).createPlayer();
        }
        catch (FileNotFoundException guestPlayerHadIllegalNameForURLException) {
            return new PlayerStorage("<Guest>");
        }
    }
}
