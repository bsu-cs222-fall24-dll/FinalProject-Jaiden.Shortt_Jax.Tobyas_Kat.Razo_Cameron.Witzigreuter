package edu.bsu.cs;

import edu.bsu.cs.records.GameStorage;
import edu.bsu.cs.records.CategoryStorage;
import edu.bsu.cs.records.LeaderboardStorage;
import edu.bsu.cs.records.RunStorage;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import org.apache.commons.io.IOUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/* JsonPath documentation states the following:
 ^ > When using JsonPath in java its [sic] important to know what type you expect in your result.
 * > JsonPath will automatically try to cast the result to the type expected by the invoker.
 * Because of this, unchecked casts are necessary to properly store data.
 *
 * Also, the IDE seems to think the suppression is redundant when it truly is not.
 * As such, the Redundant Suppression is, ironically, also suppressed.
 */
@SuppressWarnings({"unchecked", "RedundantSuppression"})

public class JsonReader {
    private final Object JSON;

    public static JsonReader createReader(String json) {
        return new JsonReader(json);
    }
    private JsonReader(String json) {
        JSON = Configuration.defaultConfiguration().jsonProvider().parse(json);
    }

    private String definiteScan(String keyPath) {
        return JsonPath.read(JSON, String.format("$.data%s", keyPath));
    }
    @SuppressWarnings("SameParameterValue")
    private List<String> indefiniteScan(String key) {
        return JsonPath.read(JSON, String.format("$..%s", key));
    }

    private int scanLength(String key) {
        return JsonPath.read(JSON, String.format("$..data%s.length()", key));
    }
    private int scanInt(String key) {
        return JsonPath.read(JSON, String.format("$.data%s", key));
    }

    public GameStorage createGame() {
        return new GameStorage(
                definiteScan(".weblink"),
                definiteScan(".links[0].uri"),
                definiteScan(".id"),
                definiteScan(".names.international"),

                definiteScan(".links[3].uri")
        );
    }

    public List<CategoryStorage> createCategoryList() {
        int listSize = scanLength("");
        List<CategoryStorage> toReturn = new ArrayList<>(listSize);

        for (int i = 0; i < listSize; i++) {
            if (definiteScan(String.format("[%d].type", i)).equals("per-game"))
                toReturn.add(new CategoryStorage(
                        definiteScan(String.format("[%d].weblink", i)),
                        definiteScan(String.format("[%d].links[0].uri", i)),
                        definiteScan(String.format("[%d].id", i)),
                        definiteScan(String.format("[%d].name", i)),

                        definiteScan(String.format("[%d].links[5].uri", i)),
                        definiteScan(String.format("[%d].links[1].uri", i))
                ));
        }

        return toReturn;
    }

    public LeaderboardStorage createLeaderboard(int maxRuns) throws IOException {
        String webLink = definiteScan(".weblink");

        String gameLink = definiteScan(".links[0].uri");
        String categoryLink = definiteScan(".links[1].uri");

        String timing = definiteScan(".timing");
        LinkedHashMap<Integer, RunStorage> runs = new LinkedHashMap<>();

        for (int i = 0; i < maxRuns && i < scanLength(".runs"); i++) {
            runs.put(
                    scanInt(String.format(".runs[%d].place", i)),
                    WebApiHandler.getRunData(definiteScan(String.format(".runs[%d].run.id", i)))
            );
        }

        return new LeaderboardStorage(webLink, gameLink, categoryLink, timing, runs);
    }

    public RunStorage createRun() {
        return new RunStorage(
                definiteScan(".weblink"),
                definiteScan(".links[0].uri"),
                definiteScan(".id"),

                definiteScan(".links[1].uri"),
                definiteScan(".links[2].uri"),
                indefiniteScan("players[*].uri"),

                definiteScan(".submitted"),
                definiteScan(".times.primary")
        );
    }
    public LeaderboardStorage test_createLeaderboard() throws IOException {
        String webLink = definiteScan(".weblink");

        String gameLink = definiteScan(".links[0].uri");
        String categoryLink = definiteScan(".links[1].uri");

        String timing = definiteScan(".timing");
        LinkedHashMap<Integer, RunStorage> runs = new LinkedHashMap<>();

        runs.put(1, JsonReader.createReader(IOUtils.toString(new FileInputStream("src/test/resources/edu.bsu.cs/sms-anypercent-run1.json"), StandardCharsets.UTF_8)).createRun());
        runs.put(2, JsonReader.createReader(IOUtils.toString(new FileInputStream("src/test/resources/edu.bsu.cs/sms-anypercent-run2.json"), StandardCharsets.UTF_8)).createRun());
        runs.put(3, JsonReader.createReader(IOUtils.toString(new FileInputStream("src/test/resources/edu.bsu.cs/sms-anypercent-run3.json"), StandardCharsets.UTF_8)).createRun());

        return new LeaderboardStorage(webLink, gameLink, categoryLink, timing, runs);
    }
}
