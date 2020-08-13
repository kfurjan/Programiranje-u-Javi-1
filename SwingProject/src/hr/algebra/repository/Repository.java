package hr.algebra.repository;

import hr.algebra.model.ApplicationUser;
import hr.algebra.model.Movie;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Kevin Furjan
 */
public interface Repository {

    public Optional<ApplicationUser> GetApplicationUser(String username, String password) throws Exception;
    public void CreateNewUser(String username, String password) throws Exception;
    public void CreateMovies(List<Movie> movies) throws Exception;
    public List<Movie> SelectMovies() throws Exception;
    public void ClearMovies() throws Exception;
}
