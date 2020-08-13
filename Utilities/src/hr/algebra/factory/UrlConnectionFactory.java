package hr.algebra.factory;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author dnlbe
 */
public class UrlConnectionFactory {

    public static HttpURLConnection getHttpUrlConnection(String path, int timeout, String requestMethod) throws IOException {
        URL url = new URL(path);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setReadTimeout(timeout);
        con.setConnectTimeout(timeout);
        con.setRequestMethod(requestMethod);
        con.addRequestProperty("User-Agent", "Mozilla/5.0");
        return con;
    }
}
