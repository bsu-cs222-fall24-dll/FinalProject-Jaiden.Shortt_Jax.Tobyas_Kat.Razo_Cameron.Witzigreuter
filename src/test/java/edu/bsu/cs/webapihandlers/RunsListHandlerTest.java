package edu.bsu.cs.webapihandlers;

import edu.bsu.cs.records.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.List;

public class RunsListHandlerTest {
    @Test
    public void test_wrapRunsListInLeaderboard() {
        LeaderboardStorage expectedWrappedLeaderboard = getExpectedWrappedLeaderboardForTest();
        LeaderboardStorage actualWrappedLeaderboard = RunsListHandler.wrapRunsListInLeaderboard(getRunsListForTest());

        Assertions.assertEquals(expectedWrappedLeaderboard, actualWrappedLeaderboard);
    }
    private LeaderboardStorage getExpectedWrappedLeaderboardForTest() {
        return new LeaderboardStorage(
                getRunsListForTest(),
                List.of("#", "#", "#")
        );
    }
    private List<RunStorage> getRunsListForTest() {
        return List.of(
                new RunStorage(
                        List.of(new PlayerStorage(
                                "Nonag",
                                "https://www.speedrun.com/api/v1/users/xkm3lk7j",
                                "https://www.speedrun.com/user/Nonag",
                                "user",
                                getMap(1)
                        )),
                        "2024-11-17T12:04:01Z", 5141
                ),
                new RunStorage(
                        List.of(new PlayerStorage(
                                "shionkun",
                                "https://www.speedrun.com/api/v1/users/j40p3pl8",
                                "https://www.speedrun.com/user/shionkun",
                                "user",
                                getMap(2)
                        )),
                        "2024-11-17T10:45:52Z", 4488
                ),

                new RunStorage(
                        List.of(new PlayerStorage(
                                "Miton",
                                "https://www.speedrun.com/api/v1/users/qj23lpxk",
                                "https://www.speedrun.com/user/Miton",
                                "user",
                                getMap(3)
                        )),
                        "2024-11-17T05:16:37Z", 1428
                )
        );
    }
    private LinkedHashMap<String, String> getMap(int mapIndicator) {
        LinkedHashMap<String, String> toReturn = new LinkedHashMap<>();

        switch (mapIndicator) {
            case 1 -> {
                toReturn.put("twitch", "https://www.twitch.tv/Nonag");
                toReturn.put("hitbox", "");
                toReturn.put("youtube", "https://www.youtube.com/channel/UCgem0CvL7-mj9sntJmjsLzw");
                toReturn.put("twitter", "");
                toReturn.put("speedrunslive", "");
            }
            case 2 -> {
                toReturn.put("twitch", "");
                toReturn.put("hitbox", "");
                toReturn.put("youtube", "");
                toReturn.put("twitter", "");
                toReturn.put("speedrunslive", "");
            }
            case 3 -> {
                toReturn.put("twitch", "https://www.twitch.tv/miton3t");
                toReturn.put("hitbox", "");
                toReturn.put("youtube", "https://www.youtube.com/channel/UCa8zVXcbJbqBInluc5sDmZQ");
                toReturn.put("twitter", "https://www.twitter.com/mittonton10");
                toReturn.put("speedrunslive", "");
            }

        }

        return toReturn;
    }


    @Test
    public void test_parseFieldsAsQueryParameters_nonnullLevel() {
        String expectedParameters =
                "?game=v1pxjz68&category=xd1r95wk&level=xd4e80wm&embed=players&max=20&orderby=submitted&direction=desc";
        String actualParameters =
                RunsListHandler.parseFieldsAsQueryParameters(
                        new GameStorage("v1pxjz68", "Super Mario Sunshine", List.of(), List.of()),
                        new CategoryStorage("xd1r95wk", "Individual World", "per-level"),
                        new LevelStorage("xd4e80wm", "Bianco Hills"),
                        20,
                        "orderby=submitted&direction=desc"
                );

        Assertions.assertEquals(expectedParameters, actualParameters);
    }

    @Test
    public void test_parseFieldsAsQueryParameters_nullLevel() {
        String expectedParameters =
                "?game=v1pxjz68&category=n2y3r8do&embed=players&max=20&orderby=submitted&direction=desc";
        String actualParameters =
                RunsListHandler.parseFieldsAsQueryParameters(
                        new GameStorage("v1pxjz68", "Super Mario Sunshine", List.of(), List.of()),
                        new CategoryStorage("n2y3r8do", "Any%", "per-level"),
                        null,
                        20,
                        "orderby=submitted&direction=desc"
                );

        Assertions.assertEquals(expectedParameters, actualParameters);
    }

    @Test
    public void test_parseFieldsAsQueryParameters_noOtherParameters() {
        String expectedParameters =
                "?game=v1pxjz68&category=n2y3r8do&embed=players&max=20";
        String actualParameters =
                RunsListHandler.parseFieldsAsQueryParameters(
                        new GameStorage("v1pxjz68", "Super Mario Sunshine", List.of(), List.of()),
                        new CategoryStorage("n2y3r8do", "Any%", "per-level"),
                        null,
                        20,
                        ""
                );

        Assertions.assertEquals(expectedParameters, actualParameters);
    }
}
