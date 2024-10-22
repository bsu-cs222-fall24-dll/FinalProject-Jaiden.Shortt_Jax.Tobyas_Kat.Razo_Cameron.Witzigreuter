package edu.bsu.cs;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class WebApiHandlerTest {
    @Test
    public void CategoryGrabTest() {
        List<String> expectedCategories = Arrays.asList("Pilot", "Episode 1 Demo", "Escape From Pork Belly");

        //Assertions.assertEquals(expectedCategoryData, actualCategoryData);
    }
    @Test
    public void LeaderboardDataGrabTest() {
//        String expectedRedirect = "";
//        List<String> expectedUsers = Arrays.asList("PunMister", "xambdi", "cardboardhed", "motheater", "ppap1542");
//        //primary - currently in ISO 8601
//        List<String> expectedTimes = Arrays.asList("PT4M50.980S", "PT5M33S", "PT5M43S", "PT5M57.065S", "PT10M22.900S");
//        //expectedTimes = Arrays.asList("4m:50.980s", "5m:33.0s", "5m:43.0s", "5m:57.065s", "10m:22.900s");
//
//        //submitted - currently in ISO 8601
//        List<String> expectedDates = Arrays.asList("2023-04-14T13:53:49Z", "2023-03-30T05:45:34Z", "2023-04-02T04:34:22Z", "2023-05-09T07:45:36Z", "2023-05-07T13:55:51Z");
//        //expectedDates = Arrays.asList("2023-04-14 : 13:53:", "5m:33.0s", "5m:43.0s", "5m:57.065s", "10m:22.900s");
//        DataStorage expectedLeaderboardData = new DataStorage(expectedRedirect, expectedUsers, expectedTimes, expectedDates);
//        DataStorage actualLeaderboardData = WebApiHandler.testingFetchLeaderboardDataFromJsonData("{\"data\":{\"weblink\":\"https://www.speedrun.com/star_fetchers_pilot#EP1Demo\",\"game\":\"m1mxlok6\",\"category\":\"5dwv98lk\",\"level\":null,\"platform\":null,\"region\":null,\"emulators\":null,\"video-only\":false,\"timing\":\"ingame\",\"values\":{},\"runs\":[{\"place\":1,\"run\":{\"id\":\"z0ngql9m\",\"weblink\":\"https://www.speedrun.com/star_fetchers_pilot/run/z0ngql9m\",\"game\":\"m1mxlok6\",\"level\":null,\"category\":\"5dwv98lk\",\"videos\":{\"links\":[{\"uri\":\"https://www.youtube.com/watch?v=DA0ntvgY-zg\"}]},\"comment\":\"Some small silly mistakes, sub 50 soon\",\"status\":{\"status\":\"verified\",\"examiner\":\"jn32qp2x\",\"verify-date\":\"2023-04-14T16:19:45Z\"},\"players\":[{\"rel\":\"user\",\"id\":\"8ew0n0d8\",\"uri\":\"https://www.speedrun.com/api/v1/users/8ew0n0d8\"}],\"date\":\"2023-04-14\",\"submitted\":\"2023-04-14T13:53:49Z\",\"times\":{\"primary\":\"PT4M50.980S\",\"primary_t\":290.98,\"realtime\":\"PT4M50.980S\",\"realtime_t\":290.98,\"realtime_noloads\":null,\"realtime_noloads_t\":0,\"ingame\":null,\"ingame_t\":0},\"system\":{\"platform\":\"8gej2n93\",\"emulated\":false,\"region\":null},\"splits\":null,\"values\":{\"r8rrzz58\":\"19293vjq\"}}},{\"place\":2,\"run\":{\"id\":\"zpgv1ngz\",\"weblink\":\"https://www.speedrun.com/star_fetchers_pilot/run/zpgv1ngz\",\"game\":\"m1mxlok6\",\"level\":null,\"category\":\"5dwv98lk\",\"videos\":{\"links\":[{\"uri\":\"https://youtu.be/YfLzwHMts1w\"}]},\"comment\":null,\"status\":{\"status\":\"verified\",\"examiner\":\"jn32qp2x\",\"verify-date\":\"2023-03-30T05:45:34Z\"},\"players\":[{\"rel\":\"user\",\"id\":\"jn32qp2x\",\"uri\":\"https://www.speedrun.com/api/v1/users/jn32qp2x\"}],\"date\":\"2023-03-30\",\"submitted\":\"2023-03-30T05:45:34Z\",\"times\":{\"primary\":\"PT5M33S\",\"primary_t\":333,\"realtime\":\"PT5M33S\",\"realtime_t\":333,\"realtime_noloads\":null,\"realtime_noloads_t\":0,\"ingame\":null,\"ingame_t\":0},\"system\":{\"platform\":\"8gej2n93\",\"emulated\":false,\"region\":null},\"splits\":null,\"values\":{\"r8rrzz58\":\"14ovjpwq\"}}},{\"place\":3,\"run\":{\"id\":\"m72je6ez\",\"weblink\":\"https://www.speedrun.com/star_fetchers_pilot/run/m72je6ez\",\"game\":\"m1mxlok6\",\"level\":null,\"category\":\"5dwv98lk\",\"videos\":{\"links\":[{\"uri\":\"https://youtu.be/Zl_qn2BNZBg\"}]},\"comment\":\"Might come back to try for the record but I\\u0027m leaving it at this for now\",\"status\":{\"status\":\"verified\",\"examiner\":\"jmopr9e8\",\"verify-date\":\"2023-04-08T00:19:51Z\"},\"players\":[{\"rel\":\"user\",\"id\":\"8v59erej\",\"uri\":\"https://www.speedrun.com/api/v1/users/8v59erej\"}],\"date\":\"2023-04-02\",\"submitted\":\"2023-04-02T04:34:22Z\",\"times\":{\"primary\":\"PT5M43S\",\"primary_t\":343,\"realtime\":\"PT5M43S\",\"realtime_t\":343,\"realtime_noloads\":null,\"realtime_noloads_t\":0,\"ingame\":null,\"ingame_t\":0},\"system\":{\"platform\":\"8gej2n93\",\"emulated\":false,\"region\":null},\"splits\":null,\"values\":{\"r8rrzz58\":\"14ovjpwq\"}}},{\"place\":4,\"run\":{\"id\":\"y982v1rz\",\"weblink\":\"https://www.speedrun.com/star_fetchers_pilot/run/y982v1rz\",\"game\":\"m1mxlok6\",\"level\":null,\"category\":\"5dwv98lk\",\"videos\":{\"links\":[{\"uri\":\"https://youtu.be/gnQZArytr5c\"}]},\"comment\":\"*thumbs up*\",\"status\":{\"status\":\"verified\",\"examiner\":\"jn32qp2x\",\"verify-date\":\"2023-07-21T14:33:14Z\"},\"players\":[{\"rel\":\"user\",\"id\":\"x3wylz6j\",\"uri\":\"https://www.speedrun.com/api/v1/users/x3wylz6j\"}],\"date\":\"2023-05-09\",\"submitted\":\"2023-05-09T07:45:36Z\",\"times\":{\"primary\":\"PT5M57.065S\",\"primary_t\":357.065,\"realtime\":\"PT5M57.065S\",\"realtime_t\":357.065,\"realtime_noloads\":null,\"realtime_noloads_t\":0,\"ingame\":null,\"ingame_t\":0},\"system\":{\"platform\":\"8gej2n93\",\"emulated\":false,\"region\":null},\"splits\":null,\"values\":{\"r8rrzz58\":\"14ovjpwq\"}}},{\"place\":5,\"run\":{\"id\":\"y2gq1o5z\",\"weblink\":\"https://www.speedrun.com/star_fetchers_pilot/run/y2gq1o5z\",\"game\":\"m1mxlok6\",\"level\":null,\"category\":\"5dwv98lk\",\"videos\":{\"links\":[{\"uri\":\"https://youtu.be/ufUVQ7QEJwA\"}]},\"comment\":\"HELP\\r\\n\",\"status\":{\"status\":\"verified\",\"examiner\":\"jn32qp2x\",\"verify-date\":\"2023-05-07T14:05:21Z\"},\"players\":[{\"rel\":\"user\",\"id\":\"xz0wz798\",\"uri\":\"https://www.speedrun.com/api/v1/users/xz0wz798\"}],\"date\":\"2023-05-07\",\"submitted\":\"2023-05-07T13:55:51Z\",\"times\":{\"primary\":\"PT10M22.900S\",\"primary_t\":622.9,\"realtime\":\"PT10M22.900S\",\"realtime_t\":622.9,\"realtime_noloads\":null,\"realtime_noloads_t\":0,\"ingame\":null,\"ingame_t\":0},\"system\":{\"platform\":\"8gej2n93\",\"emulated\":false,\"region\":null},\"splits\":null,\"values\":{\"r8rrzz58\":\"19293vjq\"}}}],\"links\":[{\"rel\":\"game\",\"uri\":\"https://www.speedrun.com/api/v1/games/m1mxlok6\"},{\"rel\":\"category\",\"uri\":\"https://www.speedrun.com/api/v1/categories/5dwv98lk\"}]}}");
//
//        Assertions.assertEquals(expectedLeaderboardData, actualLeaderboardData);
    }

}
