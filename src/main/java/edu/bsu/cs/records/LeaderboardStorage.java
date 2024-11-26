package edu.bsu.cs.records;

import java.util.List;

public record LeaderboardStorage(
        List<RunStorage> runs,
        List<String> runPlaces
) {}

