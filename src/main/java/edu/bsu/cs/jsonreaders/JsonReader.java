package edu.bsu.cs.jsonreaders;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;
import edu.bsu.cs.records.PlayerStorage;

import java.io.IOException;
import java.util.LinkedHashMap;

/* JsonPath documentation states the following:
 * > When using JsonPath in java its [sic] important to know what type you expect in your result.
 * > JsonPath will automatically try to cast the result to the type expected by the invoker.
 * Because of this, unchecked casts are necessary to properly store data.
 *
 * Also, the IDE seems to think the suppression is redundant when it truly is not.
 * As such, the Redundant Suppression is, ironically, also suppressed.
 */
@SuppressWarnings({"unchecked", "RedundantSuppression"})

public abstract class JsonReader {
    private final Object JSON;

    public JsonReader(String json) throws IOException {
        JSON = Configuration.defaultConfiguration().jsonProvider().parse(json);

        if (pathExists("status"))
            throw new IOException(String.format("The fetched JSON contains status code '%s'.", scan("status")));
    }

    protected Object scan(String keyPath) {
        return JsonPath.read(JSON, String.format("$.%s", keyPath));
    }

    protected String scanForUriIfNotNull(String keyPath) {
        return (pathExists(String.format("%s.uri", keyPath)))
                ? (String) scan(String.format("%s.uri", keyPath))
                : "";
    }

    protected boolean pathExists(String key) {
        try {
            scan(key);
            return true;
        }
        catch (PathNotFoundException pathDoesNotExistException) {
            return false;
        }
    }



    protected PlayerStorage createPlayer(String keyToRootOfPlayer) {
        boolean playerIsUser = pathExists(String.format("%s.id", keyToRootOfPlayer));

        String name = (String) ((playerIsUser)
                ? scan(String.format("%s.names.international", keyToRootOfPlayer))
                : scan(String.format("%s.name", keyToRootOfPlayer)));

        String selflink = (String) scan(String.format("%s.links[0].uri", keyToRootOfPlayer));
        String weblink = (String) ((playerIsUser)
                ? scan(String.format("%s.weblink", keyToRootOfPlayer)) : "");

        String type = (playerIsUser) ? "user" : "guest";

        LinkedHashMap<String, String> socials = readSocialMedias(keyToRootOfPlayer, playerIsUser);

        return new PlayerStorage(name, selflink, weblink, type, socials);
    }
    private LinkedHashMap<String, String> readSocialMedias(String keyToRootOfPlayer, boolean playerIsUser) {
        LinkedHashMap<String, String> socialsToReturn = new LinkedHashMap<>();

        if (!playerIsUser)
            return socialsToReturn;

        socialsToReturn.put("twitch", scanForUriIfNotNull(String.format("%s.twitch", keyToRootOfPlayer)));
        socialsToReturn.put("hitbox", scanForUriIfNotNull(String.format("%s.hitbox", keyToRootOfPlayer)));
        socialsToReturn.put("youtube",scanForUriIfNotNull(String.format("%s.youtube", keyToRootOfPlayer)));
        socialsToReturn.put("twitter", scanForUriIfNotNull(String.format("%s.twitter", keyToRootOfPlayer)));
        socialsToReturn.put("speedrunslive", scanForUriIfNotNull(String.format("%s.speedrunslive", keyToRootOfPlayer)));

        return socialsToReturn;
    }

    protected String getDateSubmitted(String keyToRootOfRun) {
        String dateSubmitted = (String) scan(String.format("%s.submitted", keyToRootOfRun));

        if (dateSubmitted == null)
            dateSubmitted = (String) scan(String.format("%s.date", keyToRootOfRun));

        if (dateSubmitted == null && (scan(String.format("%s.status.status", keyToRootOfRun))).equals("verified"))
            dateSubmitted = (String) scan(String.format("%s.status.verify-date", keyToRootOfRun));

        return dateSubmitted;
    }
}