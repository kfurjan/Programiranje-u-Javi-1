package hr.algebra.repository.sql;

import hr.algebra.model.ApplicationUser;
import hr.algebra.model.UserType;
import hr.algebra.repository.Repository;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Optional;
import javax.sql.DataSource;

/**
 *
 * @author Kevin Furjan
 */
public class SqlRepository implements Repository {

    private static final String USERNAME = "Username";
    private static final String PASSWORD = "Password";
    private static final String USER_TYPE_ID = "ApplicationUserTypeID";

    private static final String GET_APPLICATON_USER = "{ CALL GetApplicationUser (?,?) }";
    private static final String CREATE_NEW_USER = "{ CALL CreateNewUser (?,?) }";

    @Override
    public Optional<ApplicationUser> GetApplicationUser(String username, String password) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(GET_APPLICATON_USER)) {

            stmt.setString(1, username);
            stmt.setString(2, password);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    return Optional.of(new ApplicationUser(
                            rs.getString(USERNAME),
                            rs.getString(PASSWORD),
                            UserType.fromId(rs.getInt(USER_TYPE_ID))));
                }
            }
        }

        return Optional.empty();
    }

    @Override
    public void CreateNewUser(String username, String password) throws Exception {

        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(CREATE_NEW_USER)) {

            stmt.setString(1, username);
            stmt.setString(2, password);

            stmt.executeUpdate();
        }
    }
}
