package edu.bsu.cs.webapihandlers;

import edu.bsu.cs.JsonReader;
import edu.bsu.cs.records.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LeaderboardHandler extends WebApiHandler {
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
}
