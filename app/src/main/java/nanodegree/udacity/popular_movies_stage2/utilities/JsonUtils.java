package nanodegree.udacity.popular_movies_stage2.utilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import nanodegree.udacity.popular_movies_stage2.model.Movie;

public final class JsonUtils {

    private static final String BASE_URL = "https://image.tmdb.org/t/p/";
    private static final String POSTER_SIZE = "w185";

    private static final String RESULTS = "results";
    private static final String POSTER_PATH = "poster_path";
    private static final String MOVIE_ID = "id";
    private static final String ORIGINAL_TITLE = "title";
    private static final String RATING = "vote_average";
    private static final String OVERVIEW = "overview";
    private static final String RELEASE_DATE = "release_date";
    private static final String REVIEW_AUTHOR = "author";
    private static final String REVIEW_CONTENT = "content";
    private static final String REVIEW_URL = "url";
    private static final String TRAILER_NAME = "name";
    private static final String TRAILER_KEY = "key";
    private static final String TRAILER_ID = "id";

    public static List<Movie>parseMovieDbJson (String json) {

        try {
            JSONObject baseJSONRespone = new JSONObject(json);
            JSONArray resultsArray = baseJSONRespone.getJSONArray(RESULTS);

            List<Movie> movieList = new ArrayList<>();

            for (int i=0; i<resultsArray.length(); i++) {
                JSONObject resultsObject = resultsArray.getJSONObject(i);

                String constructedBasePath = BASE_URL + POSTER_SIZE + resultsObject.optString(POSTER_PATH);
                Movie movie = new Movie(resultsObject.optString(MOVIE_ID), resultsObject.optString(ORIGINAL_TITLE), resultsObject.optString(RATING), resultsObject.optString(OVERVIEW), resultsObject.optString(RELEASE_DATE), constructedBasePath);

                movieList.add(movie);
            }

            return movieList;

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }

    public static List<Movie>parseMovieReviewDbJson (String json) {
        try {
            JSONObject baseJSONResponse = new JSONObject(json);
            JSONArray resultsArray = baseJSONResponse.getJSONArray(RESULTS);

            List<Movie> reviewList = new ArrayList<>();

            for (int i=0; i<resultsArray.length(); i++) {
                JSONObject resultsObject = resultsArray.getJSONObject(i);

                Movie movie = new Movie();
                movie.setMovieReviewData(resultsObject.optString(REVIEW_AUTHOR), resultsObject.optString(REVIEW_CONTENT), resultsObject.optString(REVIEW_URL));

                reviewList.add(movie);
            }

            return reviewList;
        }
        catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<Movie>parseMovieTrailerDbJson(String json) {
        try {
            JSONObject baseJSONResponse = new JSONObject(json);
            JSONArray resultsArray = baseJSONResponse.getJSONArray(RESULTS);

            List<Movie> trailerList = new ArrayList<>();

            for (int i=0; i<resultsArray.length(); i++) {
                JSONObject resultsObject = resultsArray.getJSONObject(i);

                Movie movie = new Movie();

                movie.setMovieTrailerData(resultsObject.optString(TRAILER_NAME), resultsObject.optString(TRAILER_KEY), resultsObject.optString(TRAILER_ID));
                trailerList.add(movie);
            }

            return trailerList;

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
