package nanodegree.udacity.popular_movies_stage2;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import nanodegree.udacity.popular_movies_stage1.R;
import nanodegree.udacity.popular_movies_stage2.model.Movie;
import nanodegree.udacity.popular_movies_stage2.utilities.JsonUtils;
import nanodegree.udacity.popular_movies_stage2.utilities.NetworkUtils;

public class DetailsActivity extends AppCompatActivity implements MovieTrailerAdapter.MovieDBTrailerOnClickHandler {

    @BindView(R.id.movie_poster)
    ImageView movieImage;
    @BindView(R.id.movie_rating_value)
    TextView movieRatingValue;
    @BindView(R.id.movie_release_date_value)
    TextView movieReleaseDateValue;
    @BindView(R.id.movie_overview_value)
    TextView movieOverviewValue;
    @BindView(R.id.movie_reviews_rv)
    RecyclerView reviewRecyclerView;
    @BindView(R.id.movie_trailer_rv)
    RecyclerView trailerRecyclerView;

    private List<Movie> movieReviewList;
    private List<Movie> movieTrailerList;
    private MovieReviewAdapter movieReviewAdapter;
    private MovieTrailerAdapter movieTrailerAdapter;
    public final static String REVIEW_QUERY = "reviews";
    public final static String VIDEO_QUERY = "videos";
    public final static String YOUTUBE_BASE_APP = "vnd.youtube:";
    public final static String YOUTUBE_BASE_URL = "http://www.youtube.com/watch?v=";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ButterKnife.bind(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        reviewRecyclerView.setLayoutManager(linearLayoutManager);
        reviewRecyclerView.setAdapter(movieReviewAdapter);
        reviewRecyclerView.setHasFixedSize(true);

        trailerRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        trailerRecyclerView.setAdapter(movieTrailerAdapter);
        trailerRecyclerView.setHasFixedSize(true);

        Intent intentThatStartedThisActivity = getIntent();

        if (intentThatStartedThisActivity != null) {
            String movieId = intentThatStartedThisActivity.getStringExtra("id");
            String moviePoster = intentThatStartedThisActivity.getStringExtra("poster");
            String title = intentThatStartedThisActivity.getStringExtra("title");
            String rating = intentThatStartedThisActivity.getStringExtra("rate");
            String release = intentThatStartedThisActivity.getStringExtra("release");
            String overview = intentThatStartedThisActivity.getStringExtra("overview");

            setTitle(title);
            movieRatingValue.setText(rating);
            movieReleaseDateValue.setText(release);
            movieOverviewValue.setText(overview);

            Picasso.get()
                    .load(moviePoster)
                    .fit()
                    .into(movieImage);

            new FetchMovieReviewsTask().execute(movieId, REVIEW_QUERY);

            new FetchMovieVideosTask().execute(movieId, VIDEO_QUERY);
        }

    }

    @Override
    public void onClick(int position) {
        Intent youTubeIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(YOUTUBE_BASE_APP + movieTrailerList.get(position).getTrailerKey()));
        Intent webBrowserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(YOUTUBE_BASE_URL + movieTrailerList.get(position).getTrailerKey()));

        try {
            startActivity(youTubeIntent);
        } catch (ActivityNotFoundException e) {
            startActivity(webBrowserIntent);
        }
    }

    public class FetchMovieReviewsTask extends AsyncTask<String, Void, List<Movie>> {

        @Override
        protected List<Movie> doInBackground(String... params) {
            if (params.length == 0){
                return null;
            }

            String movieId = params[0];
            String queryType = params[1];
            URL movieReviewsRequestUrl = NetworkUtils.urlBuilder(movieId, queryType);
            Log.d("ID4", "movieReviewsRequestUrl: " +movieReviewsRequestUrl.toString());

            try {
                String jsonMovieReviewResponse = NetworkUtils.getResponseFromHttpUrl(movieReviewsRequestUrl);

                movieReviewList
                        = JsonUtils.parseMovieReviewDbJson(jsonMovieReviewResponse);

                return movieReviewList;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<Movie> movieReviewList) {
            if (movieReviewList != null) {
                movieReviewAdapter = new MovieReviewAdapter(DetailsActivity.this, movieReviewList);
                reviewRecyclerView.setAdapter(movieReviewAdapter);
                Log.d("ID4", "review async task logging if part for now");
            }
            else {
                Log.d("ID4", "review async task logging else part for now");
//                reviewRecyclerView.setVisibility(View.GONE);
            }
        }
    }

    public class FetchMovieVideosTask extends AsyncTask<String, Void, List<Movie>> {

        @Override
        protected List<Movie> doInBackground(String... params) {
            if (params.length == 0) {
                return null;
            }

            String movieId = params[0];
            String queryType = params[1];
            URL movieTrailersRequestUrl = NetworkUtils.urlBuilder(movieId, queryType);

            try {
                String jsonMovieTrailerResponse = NetworkUtils.getResponseFromHttpUrl(movieTrailersRequestUrl);

                movieTrailerList = JsonUtils.parseMovieTrailerDbJson(jsonMovieTrailerResponse);

                return movieTrailerList;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<Movie> movieTrailerList) {
            if (movieTrailerList != null) {
                movieTrailerAdapter = new MovieTrailerAdapter(movieTrailerList, DetailsActivity.this);
                trailerRecyclerView.setAdapter(movieTrailerAdapter);
                Log.d("ID4", "videos  async task logging if part for now");
            }
            else {
                Log.d("ID4", "videos async task logging else part for now");
//                trailerRecyclerView.setVisibility(View.GONE);
            }
        }
    }
}
