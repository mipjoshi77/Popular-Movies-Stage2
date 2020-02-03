package nanodegree.udacity.popular_movies_stage2.utilities;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import nanodegree.udacity.popular_movies_stage2.Constants;

public class NetworkUtils {
    private static final String BASE_URL = "https://api.themoviedb.org/3/movie";
    private static final String API_KEY_HEADER = "api_key";
    private static final String API_KEY_VALUE = Constants.MOVIE_DB_API;

    public static URL urlBuilder(String movieDbSearchQuery) {
        Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                .appendEncodedPath(movieDbSearchQuery)
                .appendQueryParameter(API_KEY_HEADER, API_KEY_VALUE)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static URL urlBuilder(String movieId, String movieReviewQuery) {
        Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                .appendEncodedPath(movieId)
                .appendEncodedPath(movieReviewQuery)
                .appendQueryParameter(API_KEY_HEADER, API_KEY_VALUE)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
