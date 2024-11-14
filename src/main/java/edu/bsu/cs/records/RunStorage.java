package edu.bsu.cs.records;

import org.ocpsoft.prettytime.PrettyTime;

import java.time.Instant;
import java.util.List;
public record RunStorage(
        String webLink,
        String selfLink,
        String id,

        String gameLink,
        String categoryLink,
        List<PlayerStorage> players,

        int place,
        String dateSubmitted,
        String primaryRunTime
) {
    private static final PrettyTime prettyTime = new PrettyTime();

    public String playernamesForLeaderboard() {
            return (players.size() > 1)
                    ? players.getFirst().name() + ", ..."
                    : players.getFirst().name();
    }

    public String prettyDateSubmitted() {
        return prettyTime.format(Instant.parse(dateSubmitted));
    }
}




