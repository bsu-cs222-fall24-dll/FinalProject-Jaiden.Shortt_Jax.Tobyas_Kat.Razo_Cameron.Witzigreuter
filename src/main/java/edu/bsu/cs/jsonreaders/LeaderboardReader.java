package edu.bsu.cs.jsonreaders;

import edu.bsu.cs.records.LeaderboardStorage;
import edu.bsu.cs.records.PlayerStorage;
import edu.bsu.cs.records.RunStorage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// The suppressions here extend the reason for the suppressions in JsonReader.java.
@SuppressWarnings({"unchecked", "RedundantSuppression"})
public class LeaderboardReader extends JsonReader {
    List<PlayerStorage> allPlayersInFile;

    public LeaderboardReader(String json) throws IOException {
        super(json);
        createAllPlayersInFile();
    }

    private void createAllPlayersInFile() {
        allPlayersInFile = new ArrayList<>();

        for (int i = 0; i < (int) scan("data.players.data.length()"); i++)
            allPlayersInFile.add(createPlayer(String.format("data.players.data[%d]", i)));
    }



    public LeaderboardStorage createLeaderboard() {
        List<RunStorage> runs = createAllRuns();
        List<String> places = (List<String>) scan("data.runs[*].place");

        return new LeaderboardStorage(runs, places);
    }

    private List<RunStorage> createAllRuns() {
        List<RunStorage> toReturn = new ArrayList<>();

        for (int i = 0; i < (int) scan("data.runs.length()"); i++) {
            String pathToThisRun = String.format("data.runs[%d].run", i);

            toReturn.add(new RunStorage(
                    matchPlayersToRun(pathToThisRun),
                    getDateSubmitted(pathToThisRun),
                    Double.parseDouble(String.valueOf(scan(String.format("%s.times.primary_t", pathToThisRun))))
            ));
        }

        return toReturn;
    }

    private List<PlayerStorage> matchPlayersToRun(String pathToRun) {
        List<String> allPlayerLinksInRun = (List<String>) scan(String.format("%s.players[*].uri", pathToRun));
        List<PlayerStorage> playersInRun = new ArrayList<>();

        for (String playerLink : allPlayerLinksInRun)
            for (PlayerStorage player : allPlayersInFile)
                if (player.selflink().equals(playerLink))
                    playersInRun.add(player);

        return playersInRun;
    }
}
