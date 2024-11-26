package edu.bsu.cs.records;

public record LevelStorage(
        String id,
        String name
) {
    @Override
    public String toString() {
        return name;
    }
}
