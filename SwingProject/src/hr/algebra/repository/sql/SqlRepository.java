package hr.algebra.repository.sql;

import hr.algebra.model.ApplicationUser;
import hr.algebra.repository.Repository;
import java.util.Optional;

/**
 *
 * @author Kevin Furjan
 */
public class SqlRepository implements Repository {

    @Override
    public Optional<ApplicationUser> GetApplicationUser(String username, String password) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
