package nanodegree.udacity.popular_movies_stage2.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;


import nanodegree.udacity.popular_movies_stage2.model.Movie;

@Dao
public interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertMovie(Movie movie);

    @Query("SELECT isFavorite FROM movie_table WHERE movieId = :movieId")
    boolean isFavorite(String movieId);

    @Delete
    void deleteMovie(Movie movie);

    @Query("SELECT * FROM movie_table")
    LiveData<List<Movie>> getFavoriteMovies();

    @Query("DELETE FROM movie_table")
    void deleteAll();
}
