package edu.bsu.cs.jsonreaders;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;
import edu.bsu.cs.records.PlayerStorage;

import java.io.IOException;

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

        return new PlayerStorage(name, selflink);
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