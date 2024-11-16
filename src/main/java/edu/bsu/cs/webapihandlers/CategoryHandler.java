package edu.bsu.cs.webapihandlers;

import edu.bsu.cs.JsonReader;
import edu.bsu.cs.records.CategoryStorage;
import edu.bsu.cs.records.GameStorage;

import java.io.IOException;
import java.util.List;

public class CategoryHandler extends WebApiHandler {
    public static List<CategoryStorage> getCategoryData(GameStorage game) throws IOException {
        return new JsonReader(establishConnection(game.linkToCategories())).createCategoryList();
    }
}
