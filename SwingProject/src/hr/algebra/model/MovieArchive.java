package hr.algebra.model;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * @author Kevin Furjan
 */
@XmlRootElement(name = "moviesarchive")
@XmlAccessorType(XmlAccessType.FIELD)
public class MovieArchive {

    @XmlElementWrapper
    @XmlElement(name = "movie")
    private List<Movie> movies;

    public MovieArchive() {
    }

    public MovieArchive(List<Movie> movies) {
        this.movies = movies;
    }

    public List<Movie> getMovies() {
        return movies;
    }
}
