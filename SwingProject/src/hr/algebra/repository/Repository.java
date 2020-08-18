package hr.algebra.repository;

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

    List<Movie> selectMovies() throws Exception;

    void clearMovies() throws Exception;
}
