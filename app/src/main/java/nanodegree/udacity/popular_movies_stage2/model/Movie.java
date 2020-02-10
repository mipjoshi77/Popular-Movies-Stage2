package nanodegree.udacity.popular_movies_stage2.model;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.io.Serializable;

@Entity(tableName = "movie_table")
public class Movie implements Serializable {

    @PrimaryKey
    @NonNull
    private String movieId;
    private String originalTitle;
    private String rating;
    private String overview;
    private String releaseDate;
    private String moviePoster;
    private String reviewAuthor;
    private String reviewContent;
    private String reviewUrl;
    private String trailerName;
    private String trailerKey;
    private String trailerId;
    private boolean isFavorite;

    public Movie (String movieId, String originalTitle, String rating, String overview, String releaseDate, String moviePoster) {
        this.movieId = movieId;
        this.originalTitle = originalTitle;
        this.rating = rating;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.moviePoster = moviePoster;
    }

    @Ignore
    public Movie() {
    }

    public void setMovieReviewData(String reviewAuthor, String reviewContent, String reviewUrl) {
        this.reviewAuthor = reviewAuthor;
        this.reviewContent = reviewContent;
        this.reviewUrl = reviewUrl;
    }

    public void setMovieTrailerData(String trailerName, String trailerKey, String trailerId) {
        this.trailerName = trailerName;
        this.trailerKey = trailerKey;
        this.trailerId = trailerId;
    }

    public void setReviewAuthor(String reviewAuthor) {
        this.reviewAuthor = reviewAuthor;
    }

    public void setReviewContent(String reviewContent) {
        this.reviewContent = reviewContent;
    }

    public void setReviewUrl(String reviewUrl) {
        this.reviewUrl = reviewUrl;
    }

    public void setTrailerName(String trailerName) {
        this.trailerName = trailerName;
    }

    public void setTrailerKey(String trailerKey) {
        this.trailerKey = trailerKey;
    }

    public void setTrailerId(String trailerId) {
        this.trailerId = trailerId;
    }

    public void setFavorite(boolean isFavorite) {
        this.isFavorite = isFavorite;
    }

    public String getMovieId() {
        return movieId;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getRating() {
        return rating;
    }

    public String getOverview() {
        return overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getMoviePoster() {
        return moviePoster;
    }

    public String getReviewAuthor() {
        return reviewAuthor;
    }

    public String getReviewContent() {
        return reviewContent;
    }

    public String getReviewUrl() {
        return reviewUrl;
    }

    public String getTrailerName() {
        return trailerName;
    }

    public String getTrailerKey() {
        return trailerKey;
    }

    public String getTrailerImageUrl() {
        String baseUrl = "https://img.youtube.com/vi/"+trailerKey+"/0.jpg";
        return baseUrl;
    }

    public String getTrailerId() {
        return trailerId;
    }

    public boolean isFavorite() {
        return isFavorite;
    }
}
