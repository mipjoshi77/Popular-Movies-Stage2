package nanodegree.udacity.popular_movies_stage2.model;

public class Movie {

    private String movideId;
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

    public Movie (String movideId, String originalTitle, String rating, String overview, String releaseDate, String moviePoster) {
        this.movideId = movideId;
        this.originalTitle = originalTitle;
        this.rating = rating;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.moviePoster = moviePoster;
    }

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

    public String getMovideId() {
        return movideId;
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
}
