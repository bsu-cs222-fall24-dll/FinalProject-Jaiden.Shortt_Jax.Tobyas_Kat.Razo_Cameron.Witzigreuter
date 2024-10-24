package edu.bsu.cs.records;
public record CategoryStorage(
        String weblink,
        String selfLink,
        String id,
        String name,

        String linkToLeaderboard,
        String linkToGame
) {
    @Override
    public String toString() {
        return name;
    }


}