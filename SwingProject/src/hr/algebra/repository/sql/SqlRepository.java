package hr.algebra.repository.sql;

import hr.algebra.model.*;
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

    private static final String GET_APPLICATION_USER = "{ CALL GetApplicationUser (?,?) }";
    private static final String CREATE_NEW_USER = "{ CALL CreateNewUser (?,?) }";
    private static final String USERNAME = "Username";
    private static final String PASSWORD = "Password";
    private static final String USER_TYPE_ID = "ApplicationUserTypeID";

    private static final String ID_MOVIE = "IDMovie";
    private static final String TITLE = "Title";
    private static final String PUBLISHED_DATE = "PublishedDate";
    private static final String DESCRIPTION = "MovieDescription";
    private static final String ORIGINAL_NAME = "OriginalName";
    private static final String LENGTH = "MovieLength";
    private static final String PICTURE_PATH = "PicturePath";
    private static final String LINK = "Link";
    private static final String START_DATE = "StartDate";
    private static final String GENRE_NAME = "GenreName";
    private static final String CREATE_MOVIES = "{ CALL CreateMovies (?,?,?,?,?,?,?,?,?,?,?) }";
    private static final String CREATE_MOVIE = "{ CALL CreateMovie (?,?,?,?,?,?,?,?) }";
    private static final String UPDATE_MOVIE = "{ CALL UpdateMovie (?,?,?,?,?,?,?,?,?) }";
    private static final String DELETE_MOVIE = "{ CALL DeleteMovie (?) }";
    private static final String SELECT_MOVIE_GENRES = "{ CALL SelectMovieGenres (?) }";
    private static final String SELECT_MOVIES = "{ CALL selectMovies }";
    private static final String SELECT_MOVIE = "{ CALL selectMovie (?) }";
    private static final String CLEAR_MOVIES = "{ CALL clearMovies }";

    private static final String ID_ACTOR = "IDActor";
    private static final String FIRSTNAME = "Firstname";
    private static final String LASTNAME = "Lastname";
    private static final String CREATE_ACTOR = "{ CALL CreateActor (?,?) }";
    private static final String UPDATE_ACTOR = "{ CALL UpdateActor (?,?,?) }";
    private static final String DELETE_ACTOR = "{ CALL DeleteActor (?) }";
    private static final String SELECT_ACTOR_MOVIES = "{ CALL SelectActorMovies (?) }";
    private static final String SELECT_ACTORS = "{ CALL SelectActors }";
    private static final String SELECT_ACTOR = "{ CALL SelectActor (?) }";
    private static final String ADD_MOVIE_TO_ACTOR = "{ CALL CreateMovieActor (?,?) }";
    private static final String REMOVE_MOVIE_FROM_ACTOR = "{ CALL DeleteMovieActor (?,?) }";
    private static final String SELECT_MOVIE_ACTORS = "{ CALL selectMovieActors (?) }";

    private static final String ID_DIRECTOR = "IDDirector";
    private static final String CREATE_DIRECTOR = "{ CALL CreateDirector (?,?) }";
    private static final String UPDATE_DIRECTOR = "{ CALL UpdateDirector (?,?,?) }";
    private static final String DELETE_DIRECTOR = "{ CALL DeleteDirector (?) }";
    private static final String SELECT_DIRECTOR_MOVIES = "{ CALL SelectDirectorMovies (?) }";
    private static final String SELECT_DIRECTORS = "{ CALL SelectDirectors }";
    private static final String SELECT_DIRECTOR = "{ CALL selectDirector (?) }";
    private static final String ADD_MOVIE_TO_DIRECTOR = "{ CALL CreateMovieDirector (?,?) }";
    private static final String REMOVE_MOVIE_FROM_DIRECTOR = "{ CALL DeleteMovieDirector (?,?) }";

    static <T, E extends Exception> Consumer<T> handlingConsumerWrapper(ThrowingConsumer<T, E> throwingConsumer, Class<E> exceptionClass) {

        return i -> {
            try {
                throwingConsumer.accept(i);
            } catch (Exception ex) {
                try {
                    E exCast = exceptionClass.cast(ex);
                    System.err.println("Exception occured: " + exCast.getMessage());
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
             CallableStatement stmt = con.prepareCall(CREATE_MOVIES)) {

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
    public List<Genre> selectMovieGenres(int id) throws Exception {

        List<Genre> genres = new ArrayList<>();
        DataSource dataSource = DataSourceSingleton.getInstance();

        try (Connection con = dataSource.getConnection();
             CallableStatement stmt = con.prepareCall(SELECT_MOVIE_GENRES)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    genres.add(new Genre(
                            rs.getString(GENRE_NAME)
                    ));
                }
            }
        }

        return genres;
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
                        rs.getInt(ID_MOVIE),
                        rs.getString(TITLE),
                        LocalDateTime.parse(rs.getString(PUBLISHED_DATE), Movie.DATE_FORMATTER),
                        rs.getString(DESCRIPTION),
                        rs.getString(ORIGINAL_NAME),
                        rs.getString(LENGTH),
                        rs.getString(PICTURE_PATH),
                        rs.getString(LINK),
                        rs.getString(START_DATE),
                        selectMovieGenres(rs.getInt(ID_MOVIE))));
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

    @Override
    public void updateMovie(int id, Movie movie) throws Exception {

        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
             CallableStatement stmt = con.prepareCall(UPDATE_MOVIE)) {

            stmt.setInt(1, id);
            stmt.setString(2, movie.getTitle());
            stmt.setString(3, movie.getPublishedDate().format(Movie.DATE_FORMATTER));
            stmt.setString(4, movie.getDescription());
            stmt.setString(5, movie.getOriginalName());
            stmt.setString(6, movie.getLength());
            stmt.setString(7, movie.getPicturePath());
            stmt.setString(8, movie.getLink());
            stmt.setString(9, movie.getStartDate());

            stmt.executeUpdate();
        }
    }

    @Override
    public void deleteMovie(int id) throws Exception {

        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
             CallableStatement stmt = con.prepareCall(DELETE_MOVIE)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    @Override
    public void createMovie(Movie movie) throws Exception {

        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
             CallableStatement stmt = con.prepareCall(CREATE_MOVIE)) {

            stmt.setString(1, movie.getTitle());
            stmt.setString(2, movie.getPublishedDate().format(Movie.DATE_FORMATTER));
            stmt.setString(3, movie.getDescription());
            stmt.setString(4, movie.getOriginalName());
            stmt.setString(5, movie.getLength());
            stmt.setString(6, movie.getPicturePath());
            stmt.setString(7, movie.getLink());
            stmt.setString(8, movie.getStartDate());

            stmt.executeUpdate();
        }
    }

    @Override
    public Optional<Movie> selectMovie(int id) throws Exception {

        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
             CallableStatement stmt = con.prepareCall(SELECT_MOVIE)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    return Optional.of(new Movie(
                            rs.getInt(ID_MOVIE),
                            rs.getString(TITLE),
                            LocalDateTime.parse(rs.getString(PUBLISHED_DATE), Movie.DATE_FORMATTER),
                            rs.getString(DESCRIPTION),
                            rs.getString(ORIGINAL_NAME),
                            rs.getString(LENGTH),
                            rs.getString(PICTURE_PATH),
                            rs.getString(LINK),
                            rs.getString(START_DATE),
                            selectMovieGenres(rs.getInt(ID_MOVIE))));
                }
            }
            return Optional.empty();
        }
    }

    @Override
    public List<Movie> selectActorMovies(int id) throws Exception {

        List<Movie> movies = new ArrayList<>();
        DataSource dataSource = DataSourceSingleton.getInstance();

        try (Connection con = dataSource.getConnection();
             CallableStatement stmt = con.prepareCall(SELECT_ACTOR_MOVIES)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    movies.add(new Movie(
                            rs.getInt(ID_MOVIE),
                            rs.getString(TITLE),
                            LocalDateTime.parse(rs.getString(PUBLISHED_DATE), Movie.DATE_FORMATTER),
                            rs.getString(DESCRIPTION),
                            rs.getString(ORIGINAL_NAME),
                            rs.getString(LENGTH),
                            rs.getString(PICTURE_PATH),
                            rs.getString(LINK),
                            rs.getString(START_DATE)));
                }
            }
        }

        return movies;
    }

    @Override
    public List<Actor> selectActors() throws Exception {

        List<Actor> actors = new ArrayList<>();

        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
             CallableStatement stmt = con.prepareCall(SELECT_ACTORS);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                actors.add(new Actor(
                        rs.getInt(ID_ACTOR),
                        rs.getString(FIRSTNAME),
                        rs.getString(LASTNAME),
                        selectActorMovies(rs.getInt(ID_ACTOR))));
            }
        }

        return actors;
    }

    @Override
    public Optional<Actor> selectActor(int id) throws Exception {

        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
             CallableStatement stmt = con.prepareCall(SELECT_ACTOR)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    return Optional.of(new Actor(
                            rs.getInt(ID_ACTOR),
                            rs.getString(FIRSTNAME),
                            rs.getString(LASTNAME),
                            selectActorMovies(rs.getInt(ID_ACTOR))));
                }
            }
            return Optional.empty();
        }
    }

    @Override
    public void createActor(Actor actor) throws Exception {

        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
             CallableStatement stmt = con.prepareCall(CREATE_ACTOR)) {

            stmt.setString(1, actor.getFirstName());
            stmt.setString(2, actor.getLastName());

            stmt.executeUpdate();
        }
    }

    @Override
    public void updateActor(int id, Actor actor) throws Exception {

        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
             CallableStatement stmt = con.prepareCall(UPDATE_ACTOR)) {

            stmt.setInt(1, id);
            stmt.setString(2, actor.getFirstName());
            stmt.setString(3, actor.getLastName());

            stmt.executeUpdate();
        }
    }

    @Override
    public void deleteActor(int id) throws Exception {

        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
             CallableStatement stmt = con.prepareCall(DELETE_ACTOR)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    @Override
    public void addMovieToActor(int idMovie, int idActor) throws Exception {

        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
             CallableStatement stmt = con.prepareCall(ADD_MOVIE_TO_ACTOR)) {

            stmt.setInt(1, idMovie);
            stmt.setInt(2, idActor);
            stmt.executeUpdate();
        }
    }

    @Override
    public void removeMovieFromActor(int idMovie, int idActor) throws Exception {

        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
             CallableStatement stmt = con.prepareCall(REMOVE_MOVIE_FROM_ACTOR)) {

            stmt.setInt(1, idMovie);
            stmt.setInt(2, idActor);
            stmt.executeUpdate();
        }
    }

    @Override
    public List<Movie> selectDirectorMovies(int id) throws Exception {

        List<Movie> movies = new ArrayList<>();
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
             CallableStatement stmt = con.prepareCall(SELECT_DIRECTOR_MOVIES)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    movies.add(new Movie(
                            rs.getInt(ID_MOVIE),
                            rs.getString(TITLE),
                            LocalDateTime.parse(rs.getString(PUBLISHED_DATE), Movie.DATE_FORMATTER),
                            rs.getString(DESCRIPTION),
                            rs.getString(ORIGINAL_NAME),
                            rs.getString(LENGTH),
                            rs.getString(PICTURE_PATH),
                            rs.getString(LINK),
                            rs.getString(START_DATE)));
                }
            }
        }

        return movies;
    }

    @Override
    public List<Director> selectDirectors() throws Exception {

        List<Director> directors = new ArrayList<>();

        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
             CallableStatement stmt = con.prepareCall(SELECT_DIRECTORS);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                directors.add(new Director(
                        rs.getInt(ID_DIRECTOR),
                        rs.getString(FIRSTNAME),
                        rs.getString(LASTNAME),
                        selectDirectorMovies(rs.getInt(ID_DIRECTOR))));
            }
        }

        return directors;
    }

    @Override
    public Optional<Director> selectDirector(int id) throws Exception {

        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
             CallableStatement stmt = con.prepareCall(SELECT_DIRECTOR)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    return Optional.of(new Director(
                            rs.getInt(ID_DIRECTOR),
                            rs.getString(FIRSTNAME),
                            rs.getString(LASTNAME),
                            selectActorMovies(rs.getInt(ID_DIRECTOR))));
                }
            }
            return Optional.empty();
        }
    }

    @Override
    public void createDirector(Director director) throws Exception {

        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
             CallableStatement stmt = con.prepareCall(CREATE_DIRECTOR)) {

            stmt.setString(1, director.getFirstName());
            stmt.setString(2, director.getLastName());

            stmt.executeUpdate();
        }
    }

    @Override
    public void updateDirector(int id, Director director) throws Exception {

        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
             CallableStatement stmt = con.prepareCall(UPDATE_DIRECTOR)) {

            stmt.setInt(1, id);
            stmt.setString(2, director.getFirstName());
            stmt.setString(3, director.getLastName());

            stmt.executeUpdate();
        }
    }

    @Override
    public void deleteDirector(int id) throws Exception {

        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
             CallableStatement stmt = con.prepareCall(DELETE_DIRECTOR)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    @Override
    public void addMovieToDirector(int idMovie, int idDirector) throws Exception {

        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
             CallableStatement stmt = con.prepareCall(ADD_MOVIE_TO_DIRECTOR)) {

            stmt.setInt(1, idMovie);
            stmt.setInt(2, idDirector);
            stmt.executeUpdate();
        }
    }

    @Override
    public void removeMovieFromDirector(int idMovie, int idDirector) throws Exception {

        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
             CallableStatement stmt = con.prepareCall(REMOVE_MOVIE_FROM_DIRECTOR)) {

            stmt.setInt(1, idMovie);
            stmt.setInt(2, idDirector);
            stmt.executeUpdate();
        }
    }

    @Override
    public List<Actor> selectMovieActors(int id) throws Exception {

        List<Actor> actors = new ArrayList<>();
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
             CallableStatement stmt = con.prepareCall(SELECT_MOVIE_ACTORS)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    actors.add(new Actor(
                            rs.getInt(ID_ACTOR),
                            rs.getString(FIRSTNAME),
                            rs.getString(LASTNAME))
                    );
                }
            }
        }

        return actors;
    }
}
