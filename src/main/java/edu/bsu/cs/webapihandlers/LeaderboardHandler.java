package edu.bsu.cs.webapihandlers;

import edu.bsu.cs.jsonreaders.LeaderboardReader;
import edu.bsu.cs.records.CategoryStorage;
import edu.bsu.cs.records.GameStorage;
import edu.bsu.cs.records.LeaderboardStorage;
import edu.bsu.cs.records.LevelStorage;

import java.io.IOException;

public class LeaderboardHandler extends WebApiHandler {
    public static LeaderboardStorage getLeaderboard(GameStorage game, CategoryStorage category, LevelStorage level, int topPositions) throws IOException {
        StringBuilder leaderboardLinkBuilder = new StringBuilder();
        boolean levelIncluded = level != null;

        leaderboardLinkBuilder.append(String.format("https://www.speedrun.com/api/v1/leaderboards/%s/", game.id()));

        if (category.type().equals("per-level") && levelIncluded)
            leaderboardLinkBuilder.append(String.format("level/%s/%s", level.id(), category.id()));
        else // (category.type().equals("per-game"))
            leaderboardLinkBuilder.append(String.format("category/%s", category.id()));

        leaderboardLinkBuilder.append(String.format("?embed=players&top=%d", topPositions));

        return new LeaderboardReader(establishConnection(leaderboardLinkBuilder.toString())).createLeaderboard();
    }
}
