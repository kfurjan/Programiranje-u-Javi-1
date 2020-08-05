package hr.algebra.repository;

import hr.algebra.repository.sql.SqlRepository;

/**
 *
 * @author Kevin Furjan
 */
public class RepositoryFactory {

    private RepositoryFactory() {}

    public static Repository getRepository() throws Exception {
        return new SqlRepository();
    }
}
