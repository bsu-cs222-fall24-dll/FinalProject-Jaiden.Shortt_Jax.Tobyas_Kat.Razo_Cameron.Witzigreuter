package edu.bsu.cs.records;
public record GameStorage(
        String id,
        String name,

        String linkToCategories,
        String linkToLevels
) {}