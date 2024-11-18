package edu.bsu.cs.jsonreaders;

import edu.bsu.cs.records.PlayerStorage;
import edu.bsu.cs.records.RunStorage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RunsListReader extends JsonReader {

    public RunsListReader(String json) throws IOException {
        super(json);
    }

    public List<RunStorage> createRunsList() {
        List<RunStorage> runs = new ArrayList<>();

        for (int i = 0; i < (int) scan("data.length()"); i++) {
            runs.add(createRunWithPlayersAtPath(String.format("data[%d]", i)));
        }

        return runs;
    }



    private RunStorage createRunWithPlayersAtPath(String path) {
        List<PlayerStorage> players = createPlayersAtPath(String.format("%s.players", path));
        String dateSubmitted = getDateSubmitted(path);
        String primaryRunTime = (String) scan(String.format("%s.times.primary", path));

        return new RunStorage(players, dateSubmitted, primaryRunTime);
    }

    private List<PlayerStorage> createPlayersAtPath(String path) {
        List<PlayerStorage> toReturn = new ArrayList<>();
        int numOfPlayersAtPath = (int) scan(String.format("%s.data.length()", path));

        for (int i = 0; i < numOfPlayersAtPath; i++) {
            toReturn.add(createPlayer(String.format("%s.data[%d]", path, i)));
        }

        return toReturn;
    }
}
