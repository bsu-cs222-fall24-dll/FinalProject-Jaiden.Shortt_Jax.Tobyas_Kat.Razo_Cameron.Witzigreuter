package edu.bsu.cs.records;

import java.util.LinkedHashMap;

public record PlayerStorage (
        String name,
        String selflink,
        String weblink,

        String type,

        LinkedHashMap<String, String> socialMedias
) {}
