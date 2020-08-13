package hr.algebra.repository;

import hr.algebra.model.ApplicationUser;
import hr.algebra.model.Movie;

import java.util.List;
import java.util.Optional;

/**
 * @author Kevin Furjan
 */
public interface Repository {

    Optional<ApplicationUser> GetApplicationUser(String username, String password) throws Exception;

    void CreateNewUser(String username, String password) throws Exception;

    void CreateMovies(List<Movie> movies) throws Exception;

    List<Movie> SelectMovies() throws Exception;

    void ClearMovies() throws Exception;
}
