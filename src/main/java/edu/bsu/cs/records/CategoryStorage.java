package edu.bsu.cs.records;
public record CategoryStorage(
        String id,
        String name,

        String type
) {
    @Override
    public String toString() {
        return (type.equals("per-game"))
                ? name
                : name + "*";
    }
}