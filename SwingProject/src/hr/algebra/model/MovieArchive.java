package hr.algebra.model;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
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
