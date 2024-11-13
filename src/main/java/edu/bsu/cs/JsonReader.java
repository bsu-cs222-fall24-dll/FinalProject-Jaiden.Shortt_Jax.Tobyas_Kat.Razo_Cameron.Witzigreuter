package edu.bsu.cs;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import edu.bsu.cs.records.CategoryStorage;
import edu.bsu.cs.records.GameStorage;
import edu.bsu.cs.records.LeaderboardStorage;
import edu.bsu.cs.records.RunStorage;
import org.apache.commons.io.IOUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
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

    public JsonReader(String json) {
        JSON = Configuration.defaultConfiguration().jsonProvider().parse(json);
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


    public GameStorage createGame() {
        return new GameStorage(
                (String) definiteScan("data.weblink"),
                (String) definiteScan("data.links[0].uri"),
                (String) definiteScan("data.id"),
                (String) definiteScan("data.names.international"),

                (String) definiteScan("data.links[3].uri")
        );
    }

    public List<CategoryStorage> createCategoryList() {
        int listSize = (int) definiteScan("data.length()");
        List<CategoryStorage> toReturn = new ArrayList<>(listSize);

        for (int i = 0; i < listSize; i++) {
            if (definiteScan(String.format("data.[%d].type", i)).equals("per-game"))
                toReturn.add(new CategoryStorage(
                        (String) definiteScan(String.format("data.[%d].weblink", i)),
                        (String) definiteScan(String.format("data.[%d].links[0].uri", i)),
                        (String) definiteScan(String.format("data.[%d].id", i)),
                        (String) definiteScan(String.format("data.[%d].name", i)),

                        (String) definiteScan(String.format("data.[%d].links[5].uri", i)),
                        (String) definiteScan(String.format("data.[%d].links[1].uri", i))
                ));
        }

        return toReturn;
    }

    public LeaderboardStorage createLeaderboard(int maxRuns) throws IOException {
        String webLink = (String) definiteScan("data.weblink");

        String gameLink = (String) definiteScan("data.links[0].uri");
        String categoryLink = (String) definiteScan("data.links[1].uri");

        String timing = (String) definiteScan("data.timing");
        LinkedHashMap<Integer, RunStorage> runs = new LinkedHashMap<>();

        for (int i = 0; i < maxRuns && i < (int) definiteScan("data.runs.length()"); i++) {
            runs.put(
                    (int) definiteScan(String.format("data.runs[%d].place", i)),
                    WebApiHandler.getRunData((String) definiteScan(String.format("data.runs[%d].run.id", i)))
            );
        }

        return new LeaderboardStorage(webLink, gameLink, categoryLink, timing, runs);
    }
    LeaderboardStorage test_createLeaderboard() throws IOException {
        String webLink = (String) definiteScan("data.weblink");

        String gameLink = (String) definiteScan("data.links[0].uri");
        String categoryLink = (String) definiteScan("data.links[1].uri");

        String timing = (String) definiteScan("data.timing");
        LinkedHashMap<Integer, RunStorage> runs = new LinkedHashMap<>();

        runs.put(1, new JsonReader(IOUtils.toString(new FileInputStream("src/test/resources/edu.bsu.cs/sms-anypercent-run1.json"), StandardCharsets.UTF_8)).createRun());
        runs.put(2, new JsonReader(IOUtils.toString(new FileInputStream("src/test/resources/edu.bsu.cs/sms-anypercent-run2.json"), StandardCharsets.UTF_8)).createRun());
        runs.put(3, new JsonReader(IOUtils.toString(new FileInputStream("src/test/resources/edu.bsu.cs/sms-anypercent-run3.json"), StandardCharsets.UTF_8)).createRun());

        return new LeaderboardStorage(webLink, gameLink, categoryLink, timing, runs);
    }

    public RunStorage createRun() {
        return new RunStorage(
                (String) definiteScan("data.weblink"),
                (String) definiteScan("data.links[0].uri"),
                (String) definiteScan("data.id"),

                (String) definiteScan("data.links[1].uri"),
                (String) definiteScan("data.links[2].uri"),
                indefiniteScan("data.players[*].uri"),

                (String) definiteScan("data.submitted"),
                (String) definiteScan("data.times.primary")
        );
    }
}
