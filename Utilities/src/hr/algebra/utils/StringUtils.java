package hr.algebra.utils;

import org.jsoup.Jsoup;

/**
 *
 * @author Kevin Furjan
 */
public class StringUtils {

    public static String EscapeHtmlTags(String html) {
        
        return Jsoup.parse(html).text();
    }
}
