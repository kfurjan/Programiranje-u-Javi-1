package hr.algebra.repository.sql;

import hr.algebra.model.ApplicationUser;
import hr.algebra.model.Movie;
import hr.algebra.model.UserType;
import hr.algebra.repository.Repository;
import hr.algebra.wrapper.ThrowingConsumer;

import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * @author Kevin Furjan
 */
public class SqlRepository implements Repository {

    private static final String USERNAME = "Username";
    private static final String PASSWORD = "Password";
    private static final String USER_TYPE_ID = "ApplicationUserTypeID";

    private static final String TITLE = "Title";
    private static final String PUBLISHED_DATE = "PublishedDate";
    private static final String DESCRIPTION = "MovieDescription";
    private static final String ORIGINAL_NAME = "OriginalName";
    private static final String LENGTH = "MovieLength";
    private static final String PICTURE_PATH = "PicturePath";
    private static final String LINK = "Link";
    private static final String START_DATE = "StartDate";

    private static final String GET_APPLICATION_USER = "{ CALL GetApplicationUser (?,?) }";
    private static final String CREATE_NEW_USER = "{ CALL CreateNewUser (?,?) }";
    private static final String CREATE_MOVIE = "{ CALL CreateMovie (?,?,?,?,?,?,?,?,?,?,?) }";
    private static final String SELECT_MOVIES = "{ CALL selectMovies }";
    private static final String CLEAR_MOVIES = "{ CALL clearMovies }";

    static <T, E extends Exception> Consumer<T> handlingConsumerWrapper(ThrowingConsumer<T, E> throwingConsumer, Class<E> exceptionClass) {

        return i -> {
            try {
                throwingConsumer.accept(i);
            } catch (Exception ex) {
                try {
                    E exCast = exceptionClass.cast(ex);
                    System.err.println("Exception occured : " + exCast.getMessage());
                } catch (ClassCastException ccEx) {
                    throw new RuntimeException(ex);
                }
            }
        };
    }

    @Override
    public Optional<ApplicationUser> getApplicationUser(String username, String password) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(GET_APPLICATION_USER)) {

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
    public void createNewUser(String username, String password) throws Exception {

        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(CREATE_NEW_USER)) {

            stmt.setString(1, username);
            stmt.setString(2, password);

            stmt.executeUpdate();
        }
    }

    @Override
    public void createMovies(List<Movie> movies) throws Exception {

        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(CREATE_MOVIE)) {

            movies.forEach(handlingConsumerWrapper(movie -> {
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
            }, SQLException.class));
        }
    }

    @Override
    public List<Movie> selectMovies() throws Exception {

        List<Movie> movies = new ArrayList<>();
        DataSource dataSource = DataSourceSingleton.getInstance();

        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(SELECT_MOVIES);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                movies.add(new Movie(
                        rs.getString(TITLE),
                        LocalDateTime.parse(rs.getString(PUBLISHED_DATE), Movie.DATE_FORMATTER),
                        rs.getString(DESCRIPTION),
                        rs.getString(ORIGINAL_NAME),
                        rs.getString(LENGTH),
                        rs.getString(PICTURE_PATH),
                        rs.getString(LINK),
                        rs.getString(START_DATE)
                ));
            }
        }

        return movies;
    }

    @Override
    public void clearMovies() throws Exception {

        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(CLEAR_MOVIES)) {

            stmt.executeUpdate();
        }
    }
}
