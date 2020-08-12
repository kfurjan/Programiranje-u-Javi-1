package hr.algebra.repository.sql;

import hr.algebra.model.ApplicationUser;
import hr.algebra.model.Movie;
import hr.algebra.model.UserType;
import hr.algebra.repository.Repository;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;
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
    private static final String CREATE_MOVIE = "{ CALL CreateMovie (?,?,?,?,?,?,?,?,?,?,?) }";

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

    @Override
    public void CreateMovies(List<Movie> movies) throws Exception {

        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(CREATE_MOVIE)) {

            for (Movie movie : movies) {
                stmt.setString(1, movie.getTitle());
                stmt.setString(2, movie.getPublishedDate().toString());
                stmt.setString(3, movie.getDescription());
                stmt.setString(4, movie.getOriginalName());
                stmt.setString(5, movie.getDirectors());
                stmt.setString(6, movie.getActors());
                stmt.setString(7, movie.getLength());
                stmt.setString(8, movie.getGenre());
                stmt.setString(9, movie.getPicturePath());
                stmt.setString(10, movie.getLink());
                stmt.setString(11, movie.getStartDate());

                stmt.executeUpdate();
            }
        }
    }
}
