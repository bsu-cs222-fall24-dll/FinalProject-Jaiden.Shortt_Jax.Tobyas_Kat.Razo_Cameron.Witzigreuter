package edu.bsu.cs.records;

import java.util.LinkedHashMap;
public record LeaderboardStorage(
        String weblink,

        String gameLink,
        String categoryLink,

        String timing,
        LinkedHashMap<Integer, RunStorage> runs
) {}

