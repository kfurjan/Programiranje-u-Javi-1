package hr.algebra.utils;

import org.jsoup.Jsoup;

public class StringUtils {

    public static String EscapeHtmlTags(String html) {

        return Jsoup.parse(html).text();
    }
}
