package nanodegree.udacity.popular_movies_stage2.database;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

import nanodegree.udacity.popular_movies_stage2.model.Movie;

public class MovieViewModel extends AndroidViewModel {
    private MovieRepository movieRepository;

    private LiveData<List<Movie>> favoriteMovieList;

    public MovieViewModel(Application application) {
        super(application);
        movieRepository = new MovieRepository(application);
        favoriteMovieList = movieRepository.getFavoriteMovies();
    }

    public LiveData<List<Movie>> getFavoriteMovieList() {
        return favoriteMovieList;
    }

    public void insert(Movie movie) {
        movieRepository.insertMovie(movie);
    }

    public void delete(Movie movie) {
        movieRepository.deleteMovie(movie);
    }

    public boolean isFavorite(String movieId) {
        return movieRepository.isFavorite(movieId);
    }
}
