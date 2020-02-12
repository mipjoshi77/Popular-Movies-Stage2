package nanodegree.udacity.popular_movies_stage2;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import nanodegree.udacity.popular_movies_stage2.database.MovieRoomDatabase;

public class MovieDetailsViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final MovieRoomDatabase mDb;
    private final String mMovieId;

    public MovieDetailsViewModelFactory(MovieRoomDatabase database, String movieId) {
        mDb = database;
        mMovieId = movieId;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new MovieDetailsViewModel(mDb, mMovieId);
    }
}
