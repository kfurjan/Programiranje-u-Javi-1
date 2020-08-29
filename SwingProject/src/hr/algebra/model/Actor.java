package hr.algebra.model;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

/**
 * @author Kevin Furjan
 */
public class Actor extends Person {

    @XmlElementWrapper
    @XmlElement(name = "movie")
    private List<Movie> movies;

    public Actor(String firstName, String lastName) {
        super(firstName, lastName);
    }

    public Actor(int id, String firstName, String lastName) {
        super(id, firstName, lastName);
    }

    public Actor(int id, String firstName, String lastName, List<Movie> movies) {
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
