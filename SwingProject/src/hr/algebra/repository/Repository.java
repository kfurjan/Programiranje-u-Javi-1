package hr.algebra.repository;

import hr.algebra.model.ApplicationUser;
import java.util.Optional;

/**
 *
 * @author Kevin Furjan
 */
public interface Repository {

    public Optional<ApplicationUser> GetApplicationUser(String username, String password) throws Exception;
    public void CreateNewUser(String username, String password) throws Exception;
}
