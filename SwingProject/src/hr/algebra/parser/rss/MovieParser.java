package hr.algebra.parser.rss;

import hr.algebra.factory.ParserFactory;
import hr.algebra.factory.UrlConnectionFactory;
import hr.algebra.model.Movie;
import hr.algebra.utils.FileUtils;
import hr.algebra.utils.StringUtils;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

/**
 * @author Kevin Furjan
 */
public class MovieParser {

    private static final String RSS_URL = "https://www.blitz-cinestar.hr/rss.aspx?najava=1";
    private static final int TIMEOUT = 10000;
    private static final String REQUEST_METHOD = "GET";
    private static final String EXT = ".jpg";
    private static final String DIR = "assets";

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.RFC_1123_DATE_TIME;
    private static final Random RANDOM = new Random();

    public static List<Movie> parse() throws IOException, XMLStreamException {

        List<Movie> movies = new ArrayList<>();
        HttpURLConnection con = UrlConnectionFactory.getHttpUrlConnection(RSS_URL, TIMEOUT, REQUEST_METHOD);
        XMLEventReader reader = ParserFactory.createStaxParser(con.getInputStream());

        Movie movie = null;
        StartElement startElement;
        Optional<RSSTagType> rssTagTyoe = Optional.empty();

        while (reader.hasNext()) {
            XMLEvent event = reader.nextEvent();
            switch (event.getEventType()) {
                case XMLStreamConstants.START_ELEMENT:
                    startElement = event.asStartElement();
                    String qName = startElement.getName().getLocalPart();
                    rssTagTyoe = RSSTagType.from(qName);
                    break;
                case XMLStreamConstants.CHARACTERS:
                    if (rssTagTyoe.isPresent()) {
                        Characters characters = event.asCharacters();
                        String data = characters.getData().trim();
                        switch (rssTagTyoe.get()) {
                            case ITEM:
                                movie = new Movie();
                                movies.add(movie);
                                break;
                            case TITLE:
                                if (movie != null && !data.isEmpty()) {
                                    movie.setTitle(data);
                                }
                                break;
                            case PUB_DATE:
                                if (movie != null && !data.isEmpty()) {
                                    LocalDateTime publishedDate = LocalDateTime.parse(data, DATE_FORMATTER);
                                    movie.setPublishedDate(publishedDate);
                                }
                                break;
                            case DESCRIPTION:
                                if (movie != null && !data.isEmpty()) {
                                    movie.setDescription(StringUtils.EscapeHtmlTags(data));
                                }
                                break;
                            case ORIGINAL_NAME:
                                if (movie != null && !data.isEmpty()) {
                                    movie.setOriginalName(data);
                                }
                                break;
                            case DIRECTOR:
                                if (movie != null && !data.isEmpty()) {
                                    movie.setDirectors(data);
                                }
                                break;
                            case ACTOR:
                                if (movie != null && !data.isEmpty()) {
                                    movie.setActors(data);
                                }
                                break;
                            case LENGTH:
                                if (movie != null && !data.isEmpty()) {
                                    movie.setLength(data);
                                }
                                break;
                            case GENRE:
                                if (movie != null && !data.isEmpty()) {
                                    movie.setGenre(data);
                                }
                                break;
                            case POSTER:
                                if (movie != null && !data.isEmpty()) {
                                    handlePicture(movie, data);
                                }
                                break;
                            case LINK:
                                if (movie != null && !data.isEmpty()) {
                                    movie.setLink(data);
                                }
                                break;
                            case START_DATE:
                                if (movie != null && !data.isEmpty()) {
                                    movie.setStartDate(data);
                                }
                                break;
                            default:
                                break;
                        }
                    }
                    break;
            }
        }
        return movies;
    }

    private static void handlePicture(Movie movie, String pictureUrl) throws IOException {

        String ext = pictureUrl.substring(pictureUrl.lastIndexOf("."));
        if (ext.length() > 4) {
            ext = EXT;
        }
        String pictureName = Math.abs(RANDOM.nextInt()) + ext;
        String localPicturePath = DIR + File.separator + pictureName;

        FileUtils.copyFromUrl(pictureUrl, localPicturePath);
        movie.setPicturePath(localPicturePath);
    }

    private enum RSSTagType {

        ITEM("item"),
        TITLE("title"),
        PUB_DATE("pubDate"),
        DESCRIPTION("description"),
        ORIGINAL_NAME("orignaziv"),
        DIRECTOR("redatelj"),
        ACTOR("glumci"),
        LENGTH("trajanje"),
        GENRE("zanr"),
        POSTER("plakat"),
        LINK("link"),
        START_DATE("pocetak");

        private final String name;

        RSSTagType(String name) {
            this.name = name;
        }

        private static Optional<RSSTagType> from(String name) {
            for (RSSTagType value : values()) {
                if (value.name.equals(name)) {
                    return Optional.of(value);
                }
            }
            return Optional.empty();
        }
    }
}
