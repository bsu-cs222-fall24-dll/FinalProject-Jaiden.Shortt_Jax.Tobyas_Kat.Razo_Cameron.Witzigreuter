package edu.bsu.cs.records;

import java.util.List;

public record GameStorage(
        String id,
        String name,

        List<CategoryStorage> categories,
        List<LevelStorage> levels
) {}