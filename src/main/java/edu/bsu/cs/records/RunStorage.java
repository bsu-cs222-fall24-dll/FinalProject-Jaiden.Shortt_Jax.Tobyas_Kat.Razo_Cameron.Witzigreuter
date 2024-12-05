package edu.bsu.cs.records;

import org.ocpsoft.prettytime.PrettyTime;

import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
public record RunStorage(
        List<PlayerStorage> players,

        String dateSubmitted,
        double primaryRunTime
) {
    private static final PrettyTime prettyTime = new PrettyTime();
    public String prettyDateSubmitted() {
        if (dateSubmitted == null)
            return "<no date found>";

        try {
            return prettyTime.format(Instant.parse(dateSubmitted));
        }
        catch (DateTimeParseException dateSubmittedDoesNotIncludeTimeException) {
            String[] splitDate = dateSubmitted.split("-");
            int[] splitDateAsInts = new int[splitDate.length];

            for (int i = 0; i < splitDate.length; i++)
                splitDateAsInts[i] = Integer.parseInt(splitDate[i]);

            return prettyTime.format(LocalDate.of(splitDateAsInts[0], splitDateAsInts[1], splitDateAsInts[2]));
        }
    }

    public String formattedRunTime() {
        int[] times = calculateTimes();
        return buildFormattedRunTime(times);
    }
    private int[] calculateTimes() {
        int[] times = new int[5];

        final int secondsInADay, secondsInAnHour, secondsInAMinute;

        secondsInADay = 60 * 60 * 24;
        secondsInAnHour = 60 * 60;
        secondsInAMinute = 60;

        times[0] = (int) (primaryRunTime / secondsInADay);
        times[1] = (int) (primaryRunTime % secondsInADay / secondsInAnHour);
        times[2] = (int) (primaryRunTime % secondsInAnHour / secondsInAMinute);
        times[3] = (int) (primaryRunTime % secondsInAMinute);
        times[4] = (int) Math.round(primaryRunTime % 1 * 1000);

        return times;
    }
    private String buildFormattedRunTime(int[] times) {
        StringBuilder timeBuilder = new StringBuilder();
        String[] timeSuffixes = {"D", "h", "m", "s", "ms"};
        int currentTime = 0;

        while (currentTime < 2) {
            if (times[currentTime] > 0)
                timeBuilder.append(String.format("%d%s ", times[currentTime], timeSuffixes[currentTime]));

            currentTime++;
        }

        while (currentTime < 4) {
            if (timeBuilder.isEmpty()) {
                if (times[currentTime] > 0)
                    timeBuilder.append(String.format("%d%s ", times[currentTime], timeSuffixes[currentTime]));
            }
            else {
                timeBuilder.append(String.format("%02d%s ", times[currentTime], timeSuffixes[currentTime]));
            }

            currentTime++;
        }

        if (times[currentTime] > 0)
            timeBuilder.append(String.format("%03d%s ", times[currentTime], timeSuffixes[currentTime]));

        return timeBuilder.toString().trim();
    }

    public String playernamesForLeaderboard() {
            return (players.size() > 1)
                    ? players.getFirst().name() + ", ..."
                    : players.getFirst().name();
    }

}