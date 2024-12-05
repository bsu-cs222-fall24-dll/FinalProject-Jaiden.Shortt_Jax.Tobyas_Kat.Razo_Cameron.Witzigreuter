package edu.bsu.cs.jsonreaders.runslistreader;

import edu.bsu.cs.jsonreaders.RunsListReader;
import edu.bsu.cs.records.PlayerStorage;
import edu.bsu.cs.records.RunStorage;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;

public class RunsListReaderTest {
    @BeforeAll
    public static void initializeRunsListReader() throws IOException {
        smsRunsListReader = new RunsListReader(IOUtils.toString(Objects.requireNonNull(
                RunsListReaderTest.class.getResourceAsStream("sms-anypercent-runs-by-date.json")),
                StandardCharsets.UTF_8
        ));
        pseudoRunsListReader = new RunsListReader(IOUtils.toString(Objects.requireNonNull(
                RunsListReaderTest.class.getResourceAsStream("fake-runslist-for-players.json")),
                StandardCharsets.UTF_8
        ));
    }
    static RunsListReader smsRunsListReader, pseudoRunsListReader;

    @Test
    public void test_createRunsList() {
        List<RunStorage> expectedRunsList = getRunsListForTest();
        List<RunStorage> actualRunsList = smsRunsListReader.createRunsList();

        Assertions.assertEquals(expectedRunsList, actualRunsList);
    }
    private List<RunStorage> getRunsListForTest() {

        return List.of(
                new RunStorage(
                        List.of(new PlayerStorage(
                                "Nonag",
                                "https://www.speedrun.com/api/v1/users/xkm3lk7j",
                                "https://www.speedrun.com/user/Nonag",
                                "user",
                                getMap("1A")
                        )),
                        "2024-11-17T12:04:01Z", "PT1H25M41S"
                ),
                new RunStorage(
                        List.of(new PlayerStorage(
                                "shionkun",
                                "https://www.speedrun.com/api/v1/users/j40p3pl8",
                                "https://www.speedrun.com/user/shionkun",
                                "user",
                                getMap("1B")
                        )),
                        "2024-11-17T10:45:52Z", "PT1H14M48S"
                ),
                new RunStorage(
                        List.of(new PlayerStorage(
                                "Miton",
                                "https://www.speedrun.com/api/v1/users/qj23lpxk",
                                "https://www.speedrun.com/user/Miton",
                                "user",
                                getMap("1C")
                        )),
                        "2024-11-17T05:16:37Z", "PT23M48S"
                )
        );
    }

    @Test
    public void test_createRunsList_forCoverage() {
        List<RunStorage> expectedRunsList = getRunsListForCoverageTest();
        List<RunStorage> actualRunsList = pseudoRunsListReader.createRunsList();

        Assertions.assertEquals(expectedRunsList, actualRunsList);
    }
    private List<RunStorage> getRunsListForCoverageTest() {
        return List.of(
                new RunStorage(
                        List.of(new PlayerStorage(
                                "user",
                                "run1userselflink",
                                "weblink",
                                "user",
                                getMap("2A")
                        )),
                        "submitted", "run1time"
                ),
                new RunStorage(
                        List.of(new PlayerStorage(
                                "guest1",
                                "run2guestselflink",
                                "",
                                "guest",
                                getMap("2B")
                        )),
                        "date", "run2time"
                ),
                new RunStorage(
                        List.of(new PlayerStorage(
                                "guest2",
                                "run3guestselflink",
                                "",
                                "guest",
                                getMap("2C")
                        )),
                        "verify-date", "run3time"
                ),
                new RunStorage(
                        List.of(new PlayerStorage(
                                "guest3",
                                "run4guestselflink",
                                "",
                                "guest",
                                getMap("2D")
                        )),
                        null, "run4time"
                )
        );
    }


    private LinkedHashMap<String, String> getMap(String mapIndicator) {
        LinkedHashMap<String, String> toReturn = new LinkedHashMap<>();

        switch (mapIndicator) {
            case "1A" -> {
                toReturn.put("twitch", "https://www.twitch.tv/Nonag");
                toReturn.put("hitbox", "");
                toReturn.put("youtube", "https://www.youtube.com/channel/UCgem0CvL7-mj9sntJmjsLzw");
                toReturn.put("twitter", "");
                toReturn.put("speedrunslive", "");
            }
            case "1B" -> {
                toReturn.put("twitch", "");
                toReturn.put("hitbox", "");
                toReturn.put("youtube", "");
                toReturn.put("twitter", "");
                toReturn.put("speedrunslive", "");
            }
            case "1C" -> {
                toReturn.put("twitch", "https://www.twitch.tv/miton3t");
                toReturn.put("hitbox", "");
                toReturn.put("youtube", "https://www.youtube.com/channel/UCa8zVXcbJbqBInluc5sDmZQ");
                toReturn.put("twitter", "https://www.twitter.com/mittonton10");
                toReturn.put("speedrunslive", "");
            }
            case "2A" -> {
                toReturn.put("twitch", "");
                toReturn.put("hitbox", "");
                toReturn.put("youtube", "youtube");
                toReturn.put("twitter", "");
                toReturn.put("speedrunslive", "");
            }
            case "2B", "2C", "2D" -> {}
        }

        return toReturn;
    }

}
