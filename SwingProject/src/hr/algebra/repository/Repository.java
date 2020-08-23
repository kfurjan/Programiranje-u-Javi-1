package hr.algebra.repository;

import hr.algebra.model.Actor;
import hr.algebra.model.ApplicationUser;
import hr.algebra.model.Movie;

import java.util.List;
import java.util.Optional;

/**
 * @author Kevin Furjan
 */
public interface Repository {

    Optional<ApplicationUser> getApplicationUser(String username, String password) throws Exception;

    void createNewUser(String username, String password) throws Exception;

    void createMovies(List<Movie> movies) throws Exception;

    void createMovie(Movie movie) throws Exception;

    List<Movie> selectMovies() throws Exception;

    Optional<Movie> selectMovie(int id) throws Exception;

    void clearMovies() throws Exception;

    void updateMovie(int id, Movie movie) throws Exception;

    void deleteMovie(int id) throws Exception;

    List<Movie> selectActorMovies(int id) throws Exception;

    List<Actor> selectActors() throws Exception;

    Optional<Actor> selectActor(int id) throws Exception;
}
