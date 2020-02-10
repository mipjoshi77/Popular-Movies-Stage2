package nanodegree.udacity.popular_movies_stage2.database;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import java.util.List;

import nanodegree.udacity.popular_movies_stage2.model.Movie;

public class MovieRepository {

    private MovieDao mMovieDao;
    private LiveData<List<Movie>> mFavoriteMovieList;


    MovieRepository(Application application) {
        MovieRoomDatabase db = MovieRoomDatabase.getDatabase(application);
        mMovieDao = db.movieDao();
        mFavoriteMovieList = mMovieDao.getFavoriteMovies();
    }


    LiveData<List<Movie>> getFavoriteMovies() {
        return mFavoriteMovieList;
    }



    void insertMovie(Movie movie) {
        MovieRoomDatabase.databaseWriteExecutor.execute(() -> {
            mMovieDao.insertMovie(movie);
        });
    }

    void deleteMovie(Movie movie) {
        MovieRoomDatabase.databaseWriteExecutor.execute(() -> {
            mMovieDao.deleteMovie(movie);
        });
    }

    boolean isFavorite(String movieId) {
        return mMovieDao.isFavorite(movieId);
    }
}
