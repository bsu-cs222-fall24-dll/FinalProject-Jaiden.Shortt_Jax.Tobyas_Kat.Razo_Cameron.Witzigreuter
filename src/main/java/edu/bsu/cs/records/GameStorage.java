package edu.bsu.cs.records;
public record GameStorage(
        String weblink,
        String selfLink,
        String id,
        String name,

        String linkToCategories
) {}