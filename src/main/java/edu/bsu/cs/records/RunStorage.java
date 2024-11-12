package edu.bsu.cs.records;

import java.util.List;
public record RunStorage(
        String webLink,
        String selfLink,
        String id,

        String gameLink,
        String categoryLink,
        List<String> playerLinks,

        String date_submitted,
        String primaryRunTime
) {
    @Override
    public String toString(){

        return playerLinks + "     " + date_submitted + "     " + primaryRunTime;
    }
}




