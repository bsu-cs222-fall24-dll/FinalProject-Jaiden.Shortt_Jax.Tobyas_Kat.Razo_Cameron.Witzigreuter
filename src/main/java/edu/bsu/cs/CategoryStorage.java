package edu.bsu.cs;

import java.util.List;
import java.util.Map;

public class CategoryStorage extends DataStorage {
    private final String id;
    private final String name;
    private final String weblink;
    private final String type;
    private final String rules;
    private final Map<String, String> players;
    private final boolean miscellaneous;
    private final List<Map<String, String>> links;

    public CategoryStorage(String id, String name, String weblink, String type, String rules, Map<String, String> players, boolean miscellaneous, List<Map<String, String>> links) {
        this.id = id;
        this.name = name;
        this.weblink = weblink;
        this.type = type;
        this.rules = rules;
        this.players = players;
        this.miscellaneous = miscellaneous;
        this.links = links;
    }

    @Override
    public String toString() {
        return String.format(
                """
                %s [%s]

                Rules:
                -----------
                %s
                ----------

                Players: %s
                ID: %s | Misc.: %b
                [%s]
                
                Links:
                %s""",
                name, type, rules, players, id, miscellaneous, weblink, links
        );
    }
}