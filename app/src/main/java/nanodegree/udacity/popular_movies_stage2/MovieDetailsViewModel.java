package nanodegree.udacity.popular_movies_stage2;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import nanodegree.udacity.popular_movies_stage2.database.MovieRoomDatabase;
import nanodegree.udacity.popular_movies_stage2.model.Movie;

public class MovieDetailsViewModel extends ViewModel {
    private LiveData<Movie> movie;

    public MovieDetailsViewModel(MovieRoomDatabase database, String movieId) {
        movie = database.movieDao ().isMovieInDb(movieId);
    }

    public LiveData<Movie> checkIfMovieInDb() {
        return movie;
    }
}
