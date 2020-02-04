package nanodegree.udacity.popular_movies_stage2.database;

import android.arch.lifecycle.LiveData;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import nanodegree.udacity.popular_movies_stage2.model.Movie;

@Dao
public interface MovieDao {

    @Insert
    void insertMovie(Movie movie);

    @Delete
    void deleteMovie(Movie movie);

    @Query("SELECT * FROM movie_table")
    LiveData<List<Movie>> getFavoriteMovies();
}
