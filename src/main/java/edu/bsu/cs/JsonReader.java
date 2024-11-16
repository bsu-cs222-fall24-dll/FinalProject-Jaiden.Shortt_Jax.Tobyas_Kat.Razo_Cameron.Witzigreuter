package edu.bsu.cs;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;
import edu.bsu.cs.records.*;
import edu.bsu.cs.webapihandlers.PlayerHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/* JsonPath documentation states the following:
 * > When using JsonPath in java its [sic] important to know what type you expect in your result.
 * > JsonPath will automatically try to cast the result to the type expected by the invoker.
 * Because of this, unchecked casts are necessary to properly store data.
 *
 * Also, the IDE seems to think the suppression is redundant when it truly is not.
 * As such, the Redundant Suppression is, ironically, also suppressed.
 */
@SuppressWarnings({"unchecked", "RedundantSuppression"})

public class JsonReader {
    private final Object JSON;

    public JsonReader(String json) throws IOException {
        JSON = Configuration.defaultConfiguration().jsonProvider().parse(json);

        if (pathExists("status"))
            throw new IOException(String.format("The fetched JSON contains status code '%s'.", scan("status")));
    }

    // Protected methods are intentionally so for testing purposes.


    protected Object scan(String keyPath) {
        return JsonPath.read(JSON, String.format("$.%s", keyPath));
    }

    protected boolean pathExists(String key) {
        try {
            scan(key);
            return true;
        }
        catch (PathNotFoundException pathDoesNotExistException) {
            return false;
        }
    }


    public GameStorage createGame() {
        return new GameStorage(
                (String) scan("data.id"),
                (String) scan("data.names.international"),

                getGameCategories(),
                getGameLevels()
        );
    }

    private List<CategoryStorage> getGameCategories() {
        int numOfCategories = (int) scan("data.categories.data.length()");
        List<CategoryStorage> categories = new ArrayList<>();

        for (int i = 0; i < numOfCategories; i++)
            categories.add(getCategoryAtPath(String.format("data.categories.data[%d]", i)));

        return categories;
    }
    private CategoryStorage getCategoryAtPath(String path) {
        return new CategoryStorage(
                (String) scan(String.format("%s.id", path)),
                (String) scan(String.format("%s.name", path)),
                (String) scan(String.format("%s.type", path))
        );
    }

    private List<LevelStorage> getGameLevels() {
        int numOfLevels = (int) scan("data.levels.data.length()");
        List<LevelStorage> levels = new ArrayList<>();

        for (int i = 0; i < numOfLevels; i++)
            levels.add(getLevelAtPath(String.format("data.levels.data[%d]", i)));

        return levels;
    }
    private LevelStorage getLevelAtPath(String path) {
        return new LevelStorage(
                (String) scan(String.format("%s.id", path)),
                (String) scan(String.format("%s.name", path))
        );
    }


    public LeaderboardStorage createLeaderboard(int maxRuns, boolean justRuns) throws IOException {
        List<RunStorage> runs = createRunList(maxRuns, justRuns);

        List<String> places = new ArrayList<>();
        if (!justRuns)
            for (int i = 1; i <= maxRuns; i++)
                places.add(String.valueOf(i));
        else
            for (RunStorage ignored : runs)
                places.add("#");

        return new LeaderboardStorage(runs, places);
    }
    protected LeaderboardStorage test_createLeaderboard(boolean justRuns) {
        List<RunStorage> runs = test_createRunList(justRuns);

        List<String> places = new ArrayList<>();
        if (!justRuns)
            for (int i = 1; i <= 3; i++)
                places.add(String.valueOf(i));
        else
            for (RunStorage ignored : runs)
                places.add("#");

        return new LeaderboardStorage(runs, places);
    }

    public List<RunStorage> createRunList(int maxRuns, boolean justRuns) throws IOException {
        String runsPath = (!justRuns) ? "data.runs" : "data";

        int numOfRunsInJson = (int) scan(String.format("%s.length()", runsPath));
        List<RunStorage> toReturn = new ArrayList<>();

        for (int i = 0; i < maxRuns && i < numOfRunsInJson; i++) {
            String pathThisRun = String.format(
                    "%s[%d]%s",
                    runsPath, i, ((!justRuns) ? "run" : "")
            );
            toReturn.add(createRun(pathThisRun));
        }

        return toReturn;
    }
    protected List<RunStorage> test_createRunList(boolean justRuns) {
        String runsPath = (!justRuns) ? "data.runs" : "data";

        int numOfRunsInJson = (int) scan(String.format("%s.length()", runsPath));
        List<RunStorage> toReturn = new ArrayList<>();

        for (int i = 0; i < 3 && i < numOfRunsInJson; i++) {
            String pathThisRun = String.format(
                    "%s[%d]%s",
                    runsPath, i, ((!justRuns) ? "run" : "")
            );
            toReturn.add(test_createRun(pathThisRun));
        }

        return toReturn;
    }

    private RunStorage createRun(String keyToRootOfRun) throws IOException {
        List<PlayerStorage> playersInRun;
        String dateSubmitted;
        String primaryRunTime;

        playersInRun = PlayerHandler.getPlayersFromLinksInRun((List<String>) scan(String.format(
                "%s.players[*].uri", keyToRootOfRun
        )));

        dateSubmitted = getDateSubmitted(keyToRootOfRun);

        primaryRunTime = (String) scan(String.format("%s.times.primary", keyToRootOfRun));

        return new RunStorage(
                playersInRun,
                dateSubmitted,
                primaryRunTime
        );
    }
    protected RunStorage test_createRun(String keyToRootOfRun) {
        List<PlayerStorage> playersInRun = new ArrayList<>();
        String dateSubmitted;
        String primaryRunTime;

        for (int i = 0; i < (int) scan(String.format("%s.players.length()", keyToRootOfRun)); i ++)
            playersInRun.add
                    (new PlayerStorage((String) scan(String.format(
                            "%s.players[%d].uri", keyToRootOfRun, i))));

        dateSubmitted = getDateSubmitted(keyToRootOfRun);

        primaryRunTime = (String) scan(String.format("%s.times.primary", keyToRootOfRun));

        return new RunStorage(
                playersInRun,
                dateSubmitted,
                primaryRunTime
        );
    }

    protected String getDateSubmitted(String keyToRootOfRun) {
        String dateSubmitted = (String) scan(String.format("%s.submitted", keyToRootOfRun));

        if (dateSubmitted == null)
            dateSubmitted = (String) scan(String.format("%s.date", keyToRootOfRun));

        if (dateSubmitted == null && (scan(String.format("%s.status.status", keyToRootOfRun))).equals("verified"))
            dateSubmitted = (String) scan(String.format("%s.status.verify-date", keyToRootOfRun));

        return dateSubmitted;
    }


    public PlayerStorage createPlayer() {
        return new PlayerStorage(
                (String) ((pathExists("data.id"))
                                ? scan("data.names.international")
                                : scan("data.name"))
        );
    }
}