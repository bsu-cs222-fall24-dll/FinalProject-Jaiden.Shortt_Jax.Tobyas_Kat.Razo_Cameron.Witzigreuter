package edu.bsu.cs;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;
import edu.bsu.cs.records.*;
import org.apache.commons.io.IOUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedList;
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
            throw new IOException(String.format("The fetched JSON contains status code '%s'.", definiteScan("status")));
    }

    private Object definiteScan(String keyPath) {
        return JsonPath.read(JSON, String.format("$.%s", keyPath));
    }
    Object test_definiteScan(String keyPath) {
        return definiteScan(keyPath);
    }

    private List<String> indefiniteScan(String key) {
        return JsonPath.read(JSON, String.format("$..%s", key));
    }
    @SuppressWarnings("SameParameterValue") // test method
    List<String> test_indefiniteScan(String key) {
        return indefiniteScan(key);
    }

    private boolean pathExists(String key) {
        try {
            definiteScan(key);
            return true;
        }
        catch (PathNotFoundException pathDoesNotExistException) {
            return false;
        }
    }
    boolean test_pathExists(String key) {
        return pathExists(key);
    }

    public GameStorage createGame() {
        return new GameStorage(
                (String) definiteScan("data.weblink"),
                (String) definiteScan("data.links[0].uri"),
                (String) definiteScan("data.id"),
                (String) definiteScan("data.names.international"),

                (String) definiteScan("data.links[3].uri"),
                (String) definiteScan("data.links[2].uri")
        );
    }

    public List<CategoryStorage> createCategoryList() {
        int listSize = (int) definiteScan("data.length()");
        List<CategoryStorage> toReturn = new ArrayList<>(listSize);

        for (int i = 0; i < listSize; i++) {
            toReturn.add(new CategoryStorage(
                    (String) definiteScan(String.format("data.[%d].weblink", i)),
                    (String) definiteScan(String.format("data.[%d].links[0].uri", i)),
                    (String) definiteScan(String.format("data.[%d].id", i)),
                    (String) definiteScan(String.format("data.[%d].name", i)),

                    (String) definiteScan(String.format("data.[%d].type", i)),

                    (String) definiteScan(String.format("data.[%d].links[1].uri", i))
            ));
        }

        return toReturn;
    }

    public List<LevelStorage> createLevelList() {
        int listSize = (int) definiteScan("data.length()");
        List<LevelStorage> toReturn = new ArrayList<>(listSize);

        for (int i = 0; i < listSize; i++) {
            toReturn.add(new LevelStorage(
                    (String) definiteScan(String.format("data.[%d].weblink", i)),
                    (String) definiteScan(String.format("data.[%d].links[0].uri", i)),
                    (String) definiteScan(String.format("data.[%d].id", i)),
                    (String) definiteScan(String.format("data.[%d].name", i)),

                    (String) definiteScan(String.format("data.[%d].links[1].uri", i))
            ));
        }

        return toReturn;
    }

    public LeaderboardStorage createLeaderboard(int maxRuns) throws IOException {
        String webLink = (String) definiteScan("data.weblink");

        String gameLink = (String) definiteScan("data.links[0].uri");
        String categoryLink = (String) definiteScan("data.links[1].uri");
        String levelLink = (pathExists("data.links[2]"))
                ? (String) definiteScan("data.links[2].uri")
                : null;

        String timing = (String) definiteScan("data.timing");
        List<RunStorage> runs = createRunList(maxRuns, false);

        return new LeaderboardStorage(webLink, gameLink, categoryLink, levelLink, timing, runs);
    }
    LeaderboardStorage test_createLeaderboard() throws IOException {
        String webLink = (String) definiteScan("data.weblink");

        String gameLink = (String) definiteScan("data.links[0].uri");
        String categoryLink = (String) definiteScan("data.links[1].uri");
        String levelLink = (pathExists("data.links[2]"))
                ? (String) definiteScan("data.links[2].uri")
                : null;

        String timing = (String) definiteScan("data.timing");
        List<RunStorage> runs = test_createRunList();
        return new LeaderboardStorage(webLink, gameLink, categoryLink, levelLink, timing, runs);
    }

    public List<RunStorage> createRunList(int maxRuns, boolean justRuns) throws IOException {
        List<RunStorage> toReturn = new ArrayList<>();

        if (!justRuns) {
            for (int i = 0; i < maxRuns && i < (int) definiteScan("data.length()"); i++)
                toReturn.add(WebApiHandler.getRunData(
                        (String) definiteScan(String.format("data.runs[%d].run.id", i)),
                        (int) definiteScan(String.format("data.runs[%d].place", i))
                ));
        }
        else
            for (int i = 0; i < maxRuns && i < (int) definiteScan("data.length()"); i++)
                toReturn.add(WebApiHandler.getRunData(
                        (String) definiteScan(String.format("data[%d].id", i)),
                        0
                ));

        return toReturn;
    }
    List<RunStorage> test_createRunList() throws IOException {
        return List.of(
                new JsonReader(IOUtils.toString(new FileInputStream("src/test/resources/edu.bsu.cs/sms-anypercent-run1.json"), StandardCharsets.UTF_8)).test_createRun(1),
                new JsonReader(IOUtils.toString(new FileInputStream("src/test/resources/edu.bsu.cs/sms-anypercent-run2.json"), StandardCharsets.UTF_8)).test_createRun(2),
                new JsonReader(IOUtils.toString(new FileInputStream("src/test/resources/edu.bsu.cs/sms-anypercent-run3.json"), StandardCharsets.UTF_8)).test_createRun(3)
        );
    }

    public RunStorage createRun(int place) {
        String weblink = (String) definiteScan("data.weblink");
        String selflink = (String) definiteScan("data.links[0].uri");
        String id = (String) definiteScan("data.id");

        String gamelink = (String) definiteScan("data.links[1].uri");
        String categorylink = (String) definiteScan("data.links[2].uri");
        List<PlayerStorage> players = new LinkedList<>();

        // place
        String dateSubmitted = (String) definiteScan("data.submitted");
        String primaryRunTime = (String) definiteScan("data.times.primary");

        for (int i = 0; i < (int) definiteScan("data.players.length()"); i++)
            players.add(WebApiHandler.getPlayerData((String) definiteScan(String.format("data.players[%d].uri", i))));

        return new RunStorage(
                weblink, selflink, id, gamelink, categorylink, players, place, dateSubmitted, primaryRunTime
        );
    }
    RunStorage test_createRun(int place) throws IOException {
        String weblink = (String) definiteScan("data.weblink");
        String selflink = (String) definiteScan("data.links[0].uri");
        String id = (String) definiteScan("data.id");

        String gamelink = (String) definiteScan("data.links[1].uri");
        String categorylink = (String) definiteScan("data.links[2].uri");
        List<PlayerStorage> players = new LinkedList<>();

        // place
        String dateSubmitted = (String) definiteScan("data.submitted");
        String primaryRunTime = (String) definiteScan("data.times.primary");

        players.add(new JsonReader(IOUtils.toString(new FileInputStream("src/test/resources/edu.bsu.cs/example-guest.json"), StandardCharsets.UTF_8)).createGuest());
        players.add(new JsonReader(IOUtils.toString(new FileInputStream("src/test/resources/edu.bsu.cs/example-user.json"), StandardCharsets.UTF_8)).createUser());

        return new RunStorage(
                weblink, selflink, id, gamelink, categorylink, players, place, dateSubmitted, primaryRunTime
        );
    }

    public PlayerStorage createUser() {
        return new PlayerStorage(
                (String) definiteScan("data.weblink"),
                (String) definiteScan("data.links[0].uri"),
                (String) definiteScan("data.id"),
                (String) definiteScan("data.names.international")
        );
    }
    public PlayerStorage createGuest() {
        return new PlayerStorage(
                null,
                (String) definiteScan("data.links[0].uri"),
                null,
                definiteScan("data.name") + "*"
        );
    }
}
