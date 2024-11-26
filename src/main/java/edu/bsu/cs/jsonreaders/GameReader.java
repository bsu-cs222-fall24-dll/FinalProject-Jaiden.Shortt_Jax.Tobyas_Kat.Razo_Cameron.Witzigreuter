package edu.bsu.cs.jsonreaders;

import edu.bsu.cs.records.CategoryStorage;
import edu.bsu.cs.records.GameStorage;
import edu.bsu.cs.records.LevelStorage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameReader extends JsonReader {

    public GameReader(String json) throws IOException {
        super(json);
    }



    public GameStorage createGame() {
        return new GameStorage(
                (String) scan("data.id"),
                (String) scan("data.names.international"),

                createGameCategories(),
                createGameLevels()
        );
    }

    private List<CategoryStorage> createGameCategories() {
        int numOfCategories = (int) scan("data.categories.data.length()");
        List<CategoryStorage> categories = new ArrayList<>();

        for (int i = 0; i < numOfCategories; i++)
            categories.add(createCategoryAtPath(String.format("data.categories.data[%d]", i)));

        return categories;
    }
    private CategoryStorage createCategoryAtPath(String path) {
        return new CategoryStorage(
                (String) scan(String.format("%s.id", path)),
                (String) scan(String.format("%s.name", path)),
                (String) scan(String.format("%s.type", path))
        );
    }

    private List<LevelStorage> createGameLevels() {
        int numOfLevels = (int) scan("data.levels.data.length()");
        List<LevelStorage> levels = new ArrayList<>();

        for (int i = 0; i < numOfLevels; i++)
            levels.add(createLevelAtPath(String.format("data.levels.data[%d]", i)));

        return levels;
    }
    private LevelStorage createLevelAtPath(String path) {
        return new LevelStorage(
                (String) scan(String.format("%s.id", path)),
                (String) scan(String.format("%s.name", path))
        );
    }
}
