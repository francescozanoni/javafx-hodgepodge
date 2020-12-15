package sample;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Properties;

public class Utils {

    /**
     * @param resourceFileName resource identifier containing configuration
     * @return configuration
     */
    public static Properties getConfig(String resourceFileName) {

        // @see https://crunchify.com/java-properties-file-how-to-read-config-properties-values-in-java
        Properties config = new Properties();

        try {
            InputStream inputStream = Utils.class.getClassLoader().getResourceAsStream(resourceFileName);
            config.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return config;
    }

    /**
     * Download the content referred by a URL via HTTP.
     *
     * @param url URL to download content from
     * @return content as string
     */
    public static String getUrlContent(String url) {

        String result = "";

        try {
            HttpClient client = HttpClient.newBuilder().build();
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            result = response.body();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return result;

    }

}
