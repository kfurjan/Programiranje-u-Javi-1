package hr.algebra.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
    private String directors;
    private String actors;
    private String length;
    private String genre;
    private String picturePath;
    private String link;
    private String startDate;

    public Movie() {}

    public Movie(String title, LocalDateTime publishedDate, String description, String originalName, String directors, String actors, String length, String genre, String picturePath, String link, String startDate) {
        this.title = title;
        this.publishedDate = publishedDate;
        this.description = description;
        this.originalName = originalName;
        this.directors = directors;
        this.actors = actors;
        this.length = length;
        this.genre = genre;
        this.picturePath = picturePath;
        this.link = link;
        this.startDate = startDate;
    }

    public Movie(String title, LocalDateTime publishedDate, String description, String originalName, String length, String picturePath, String link, String startDate) {
        this.title = title;
        this.publishedDate = publishedDate;
        this.description = description;
        this.originalName = originalName;
        this.length = length;
        this.picturePath = picturePath;
        this.link = link;
        this.startDate = startDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(LocalDateTime publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public String getDirectors() {
        return directors;
    }

    public void setDirectors(String directors) {
        this.directors = directors;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    @Override
    public String toString() {
        return title + " - " + originalName + " - " + link;
    }
}
