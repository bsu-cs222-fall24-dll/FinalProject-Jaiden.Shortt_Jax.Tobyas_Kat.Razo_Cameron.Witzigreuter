package edu.bsu.cs.webapihandlers;

import edu.bsu.cs.records.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

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
                        List.of(new PlayerStorage("Nonag", "https://www.speedrun.com/api/v1/users/xkm3lk7j")),
                        "2024-11-17T12:04:01Z", "PT1H25M41S"
                ),
                new RunStorage(
                        List.of(new PlayerStorage("shionkun", "https://www.speedrun.com/api/v1/users/j40p3pl8")),
                        "2024-11-17T10:45:52Z", "PT1H14M48S"
                ),
                new RunStorage(
                        List.of(new PlayerStorage("Miton", "https://www.speedrun.com/api/v1/users/qj23lpxk")),
                        "2024-11-17T05:16:37Z", "PT23M48S"
                )
        );
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
