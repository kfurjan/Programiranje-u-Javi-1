package hr.algebra.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 *
 * @author Kevin Furjan
 */
public class Movie {
    
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    
    private String title;
    private LocalDateTime publishedDate;
    private String description;
    private String originalName;
    private List<Director> directors;
    private List<Actor> actors;
    private int length;
    private String genre;
    private String poster;
    private String link;
    private String guid;
    private String picture;
    private String trailer;
    private LocalDateTime startDate;
}
