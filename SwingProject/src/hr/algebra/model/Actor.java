package hr.algebra.model;

import java.util.List;

/**
 * @author Kevin Furjan
 */
public class Actor extends Person {

    private int id;
    private List<Movie> movies;

    public Actor(String firstName, String lastName) {
        super(firstName, lastName);
    }

    public Actor(int id, String firstName, String lastName) {
        super(firstName, lastName);
        this.id = id;
    }

    public Actor(int id, String firstName, String lastName, List<Movie> movies) {
        super(firstName, lastName);
        this.id = id;
        this.movies = movies;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    @Override
    public String toString() {
        return "Actor{" + "id=" + id + ", movies=" + movies + '}';
    }
}
