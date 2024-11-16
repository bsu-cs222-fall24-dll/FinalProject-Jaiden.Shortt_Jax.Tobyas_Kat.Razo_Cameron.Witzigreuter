package edu.bsu.cs.webapihandlers;

import edu.bsu.cs.JsonReader;
import edu.bsu.cs.records.GameStorage;
import edu.bsu.cs.records.LevelStorage;

import java.io.IOException;
import java.util.List;

public class LevelHandler extends WebApiHandler {
    public static List<LevelStorage> getLevelData(GameStorage game) throws IOException {
        return new JsonReader(establishConnection(game.linkToLevels())).createLevelList();
    }
}
