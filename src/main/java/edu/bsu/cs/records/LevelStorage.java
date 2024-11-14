package edu.bsu.cs.records;

public record LevelStorage(
        String weblink,
        String selflink,
        String id,
        String name,

        String gamelink
) {
    @Override
    public String toString() {
        return name;
    }
}
