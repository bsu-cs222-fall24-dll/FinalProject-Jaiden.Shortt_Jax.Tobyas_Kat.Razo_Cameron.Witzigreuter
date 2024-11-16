package edu.bsu.cs.webapihandlers;

import edu.bsu.cs.JsonReader;
import edu.bsu.cs.records.PlayerStorage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PlayerHandler extends WebApiHandler {
    public static List<PlayerStorage> getPlayersFromLinksInRun(List<String> playerLinks) throws IOException {
        List<PlayerStorage> playersFromRun = new ArrayList<>();

        for (String playerLink : playerLinks)
            playersFromRun.add(getPlayerData(playerLink));

        return playersFromRun;
    }

    public static PlayerStorage getPlayerData(String playerLink) throws IOException {
        try {
            return new JsonReader(establishConnection(playerLink)).createPlayer();
        }
        catch (FileNotFoundException guestPlayerHadIllegalNameForURLException) {
            return new PlayerStorage("<Guest>");
        }
    }
}
