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

    public String playernamesForLeaderboard() {
            return (players.size() > 1)
                    ? players.getFirst().name() + ", ..."
                    : players.getFirst().name();
    }

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
}