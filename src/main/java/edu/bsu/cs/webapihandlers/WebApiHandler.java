package edu.bsu.cs.webapihandlers;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

public abstract class WebApiHandler {

    protected static String establishConnection(String url) throws IOException {
        URL fetchUrl = URI.create(url).toURL();
        URLConnection connection = fetchUrl.openConnection();
        connection.setRequestProperty("User-Agent", "Speedrun Tracker/1.0.0 (makenzie.tobyas@bsu.edu + cameron.witzigreuter@bsu.edu + connor.razo@bsu.edu + haylee.shortt@bsu.edu)");
        InputStream inputStream = connection.getInputStream();
        return IOUtils.toString(inputStream, StandardCharsets.UTF_8);
    }

}
