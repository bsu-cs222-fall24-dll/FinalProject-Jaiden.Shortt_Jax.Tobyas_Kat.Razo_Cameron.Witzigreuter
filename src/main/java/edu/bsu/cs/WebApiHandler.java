package edu.bsu.cs;

import edu.bsu.cs.application.CategoryDataStorage;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;

import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class WebApiHandler {
    public static void fetchCategoriesFromLink(String gameTitle) throws IOException {

    }

    protected static CategoryDataStorage testingFetchCategoriesFromJsonData(String categoryJson){
        return formattedCategories;
    }
    protected static DataStorage testingFetchLeaderboardDataFromJsonData(String leaderboardJson){
        return formattedLeaderboard;
    }

}
