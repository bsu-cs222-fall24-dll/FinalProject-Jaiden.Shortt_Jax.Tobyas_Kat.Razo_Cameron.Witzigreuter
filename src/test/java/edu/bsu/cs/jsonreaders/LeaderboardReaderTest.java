package edu.bsu.cs.jsonreaders;

import edu.bsu.cs.records.LeaderboardStorage;
import edu.bsu.cs.records.PlayerStorage;
import edu.bsu.cs.records.RunStorage;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.List;

public class LeaderboardReaderTest {
    @BeforeAll
    public static void initializeLeaderboardReaders() throws IOException {
        smsAnyPercentReader = new LeaderboardReader(
                IOUtils.toString(new FileInputStream("src/test/resources/edu.bsu.cs/jsonreaders/LeaderboardReader/sms-anypercent-leaderboard-with-embeds.json"), StandardCharsets.UTF_8)
        );
        pseudoLeaderboardReader = new LeaderboardReader(
                IOUtils.toString(new FileInputStream("src/test/resources/edu.bsu.cs/jsonreaders/LeaderboardReader/fake-leaderboard-for-players.json"), StandardCharsets.UTF_8)
        );
    }
    static LeaderboardReader smsAnyPercentReader, pseudoLeaderboardReader;

    @Test
    public void test_createLeaderboard() {
        LeaderboardStorage expectedLeaderboard = getLeaderboardForTest();
        LeaderboardStorage actualLeaderboard = smsAnyPercentReader.createLeaderboard();

        // The spaces in the expected list cause it to never equal the actual list.
        Assertions.assertEquals(expectedLeaderboard.runs(), actualLeaderboard.runs());
    }
    private LeaderboardStorage getLeaderboardForTest() {
        List<RunStorage> runs = List.of(
                new RunStorage(
                        List.of(new PlayerStorage(
                                "Weegee",
                                "https://www.speedrun.com/api/v1/users/kjprmwk8",
                                "https://www.speedrun.com/user/Weegee",
                                "user",
                                getMap("1A")
                        )),
                        "2024-10-14T08:11:12Z", "PT17M24S"
                ),
                new RunStorage(
                        List.of(new PlayerStorage(
                                "JTown2909",
                                "https://www.speedrun.com/api/v1/users/8r3064w8",
                                "https://www.speedrun.com/user/JTown2909",
                                "user",
                                getMap("1B")
                        )),
                        "2024-11-07T02:19:28Z", "PT18M57S"
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
        List<String> places = List.of("1", "2", "3");

        return new LeaderboardStorage(runs, places);
    }

    @Test
    public void test_createLeaderboard_forCoverage() {
        LeaderboardStorage expectedLeaderboard = getLeaderboardForCoverageTest();
        LeaderboardStorage actualLeaderboard = pseudoLeaderboardReader.createLeaderboard();

        // The spaces in the expected list cause it to never equal the actual list.
        Assertions.assertEquals(expectedLeaderboard.runs(), actualLeaderboard.runs());
    }
    private LeaderboardStorage getLeaderboardForCoverageTest() {
        List<RunStorage> runs = List.of(
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
        List<String> places = List.of("1", "2", "3", "4");

        return new LeaderboardStorage(runs, places);
    }

    private LinkedHashMap<String, String> getMap(String mapIndicator) {
        LinkedHashMap<String, String> toReturn = new LinkedHashMap<>();

        switch (mapIndicator) {
            case "1A" -> {
                toReturn.put("twitch", "https://www.twitch.tv/Weegee");
                toReturn.put("hitbox", "");
                toReturn.put("youtube", "https://www.youtube.com/user/Weegee+Speedruns");
                toReturn.put("twitter", "");
                toReturn.put("speedrunslive", "");
            }
            case "1B" -> {
                toReturn.put("twitch", "");
                toReturn.put("hitbox", "");
                toReturn.put("youtube", "https://www.youtube.com/channel/UCo4JwPnVEl-iw1P7jrY_Asw");
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
                toReturn.put("twitch", "twitch");
                toReturn.put("hitbox", "");
                toReturn.put("youtube", "");
                toReturn.put("twitter", "");
                toReturn.put("speedrunslive", "");
            }

            case "2B", "2C", "2D" -> {}
        }

        return toReturn;
    }

}
