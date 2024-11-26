package edu.bsu.cs.webapihandlers;

import edu.bsu.cs.jsonreaders.RunsListReader;
import edu.bsu.cs.records.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RunsListHandler extends WebApiHandler {
    public static LeaderboardStorage getRunsListWithLeaderboardParameters(GameStorage game, CategoryStorage chosenCategory, LevelStorage chosenLevel, int maxRuns, String otherParameters) throws IOException {
        return getRunsListAndWrapInLeaderboard(parseFieldsAsQueryParameters(game, chosenCategory, chosenLevel, maxRuns, otherParameters));
    }


    private static LeaderboardStorage getRunsListAndWrapInLeaderboard(String queryParameters) throws IOException {
        return wrapRunsListInLeaderboard(getRunsList(queryParameters));
    }

    private static List<RunStorage> getRunsList(String queryParameters) throws IOException {
        String runsListLink = String.format("%s?%s", "https://www.speedrun.com/api/v1/runs", queryParameters);
        return new RunsListReader(establishConnection(runsListLink)).createRunsList();
    }



    // (package-private access for testing purposes)
    // All other methods in this class use internet-enabled code,
    // therefore I cannot cover this method with a non-private parent method.

    static LeaderboardStorage wrapRunsListInLeaderboard(List<RunStorage> runs) {
        List<String> vestigialPlaces = new ArrayList<>();

        for (RunStorage ignored : runs)
            vestigialPlaces.add("#");

        return new LeaderboardStorage(runs, vestigialPlaces);
    }

    static String parseFieldsAsQueryParameters(GameStorage game, CategoryStorage chosenCategory, LevelStorage chosenLevel, int maxRuns, String otherParameters) {
        StringBuilder queryBuilder = new StringBuilder("?");

        queryBuilder.append(String.format("game=%s", game.id()));
        queryBuilder.append(String.format("&category=%s", chosenCategory.id()));
        if (chosenLevel != null)
            queryBuilder.append(String.format("&level=%s", chosenLevel.id()));

        queryBuilder.append("&embed=players");
        queryBuilder.append(String.format("&max=%d", maxRuns));
        if (!otherParameters.isEmpty())
            queryBuilder.append(String.format("&%s", otherParameters));

        return queryBuilder.toString();
    }
}
