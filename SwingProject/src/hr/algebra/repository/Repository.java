package hr.algebra.repository;

import hr.algebra.model.*;

import java.util.List;
import java.util.Optional;

/**
 * @author Kevin Furjan
 */
public interface Repository {

    Optional<ApplicationUser> getApplicationUser(String username, String password) throws Exception;

    void createNewUser(String username, String password) throws Exception;

    void createMovies(List<Movie> movies) throws Exception;

    void createMovie(Movie movie) throws Exception;

    List<Genre> selectMovieGenres(int id) throws Exception;

    List<Movie> selectMovies() throws Exception;

    Optional<Movie> selectMovie(int id) throws Exception;

    void clearMovies() throws Exception;

    void updateMovie(int id, Movie movie) throws Exception;

    void deleteMovie(int id) throws Exception;
    
    List<Actor> selectMovieActors(int id) throws Exception;

    List<Movie> selectActorMovies(int id) throws Exception;

    List<Actor> selectActors() throws Exception;

    Optional<Actor> selectActor(int id) throws Exception;

    void createActor(Actor actor) throws Exception;

    void updateActor(int id, Actor actor) throws Exception;

    void deleteActor(int id) throws Exception;

    void addMovieToActor(int idMovie, int idActor) throws Exception;

    void removeMovieFromActor(int idMovie, int idActor) throws Exception;

    List<Movie> selectDirectorMovies(int id) throws Exception;

    List<Director> selectDirectors() throws Exception;

    Optional<Director> selectDirector(int id) throws Exception;

    void createDirector(Director director) throws Exception;

    void updateDirector(int id, Director director) throws Exception;

    void deleteDirector(int id) throws Exception;

    void addMovieToDirector(int idMovie, int idDirector) throws Exception;

    void removeMovieFromDirector(int idMovie, int idDirector) throws Exception;
}
