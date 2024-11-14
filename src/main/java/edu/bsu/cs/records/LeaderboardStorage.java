package edu.bsu.cs.records;

import java.util.List;

public record LeaderboardStorage(
        String weblink,

        String gameLink,
        String categoryLink,
        String levelLink,

        String timing,
        List<RunStorage> runs
) {}

