package hr.algebra.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import java.util.List;

/**
 * @author Kevin Furjan
 */
public class Director extends Person {

    @XmlElementWrapper
    @XmlElement(name = "movie")
    private List<Movie> movies;

    public Director(String firstName, String lastName) {
        super(firstName, lastName);
    }

    public Director(int id, String firstName, String lastName) {
        super(id, firstName, lastName);
    }

    public Director(int id, String firstName, String lastName, List<Movie> movies) {
        super(id, firstName, lastName);
        this.movies = movies;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }
}
