package nanodegree.udacity.popular_movies_stage2;

import android.arch.lifecycle.ViewModelProviders;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import nanodegree.udacity.popular_movies_stage1.R;
import nanodegree.udacity.popular_movies_stage2.database.MovieRoomDatabase;
import nanodegree.udacity.popular_movies_stage2.database.MovieViewModel;
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
    @BindView(R.id.favorite_button)
    ToggleButton favoriteButton;
    @BindView(R.id.movie_review_header)
    TextView movieReviewHeader;
    @BindView(R.id.movie_trailer_header)
    TextView movieTrailerHeader;
    @BindView(R.id.reviews_not_available)
    TextView reviewsNotAvailable;
    @BindView(R.id.trailers_not_available)
    TextView trailersNotAvailable;

    private List<Movie> movieReviewList;
    private List<Movie> movieTrailerList;
    private MovieReviewAdapter movieReviewAdapter;
    private MovieTrailerAdapter movieTrailerAdapter;
    public final static String REVIEW_QUERY = "reviews";
    public final static String VIDEO_QUERY = "videos";
    public final static String YOUTUBE_BASE_APP = "vnd.youtube:";
    public final static String YOUTUBE_BASE_URL = "http://www.youtube.com/watch?v=";

    private MovieRoomDatabase database;

    private ScaleAnimation scaleAnimation;
    private BounceInterpolator bounceInterpolator;
    private boolean isFavorite;

    private Movie movie;

    private String movieId;
    private String moviePoster;
    private String title;
    private String rating;
    private String release;
    private String overview;
    private int moviePosition;

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

        MovieViewModel movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);

        Intent intentThatStartedThisActivity = getIntent();

        if (intentThatStartedThisActivity != null) {
            movie = (Movie) intentThatStartedThisActivity.getSerializableExtra("Movie");
        }

        database = MovieRoomDatabase.getDatabase(getApplicationContext());

        movieId = movie.getMovieId();
        moviePoster = movie.getMoviePoster();
        title = movie.getOriginalTitle();
        rating = movie.getRating();
        release = movie.getReleaseDate();
        overview = movie.getOverview();
        moviePosition = intentThatStartedThisActivity.getExtras().getInt("temp");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            String imageTransitionName = intentThatStartedThisActivity.getStringExtra("imageTransitionName");
            movieImage.setTransitionName(imageTransitionName);
        }

        setTitle(title);
        movieRatingValue.setText(rating);
        movieReleaseDateValue.setText(release);
        movieOverviewValue.setText(overview);

        Picasso.get()
                .load(moviePoster)
                .fit()
                .into(movieImage, new Callback() {
                    @Override
                    public void onSuccess() {
                        supportStartPostponedEnterTransition();
                    }

                    @Override
                    public void onError(Exception e) {
                        supportStartPostponedEnterTransition();
                    }
                });

        new FetchMovieReviewsTask().execute(movieId, REVIEW_QUERY);

        new FetchMovieVideosTask().execute(movieId, VIDEO_QUERY);

        setUpFavoriteButtonAnimation();

        setupFavoriteButtonCheckedState();

        favoriteButton.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            if (isChecked) {
                compoundButton.startAnimation(scaleAnimation);
                movieViewModel.insert(movie);
                favoriteButton.setChecked(true);
            }
            else {
                compoundButton.startAnimation(scaleAnimation);
                movieViewModel.delete(movie);
                favoriteButton.setChecked(false);
            }
        });

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
            if (!movieReviewList.isEmpty()) {
                movieReviewAdapter = new MovieReviewAdapter(DetailsActivity.this, movieReviewList);
                reviewRecyclerView.setAdapter(movieReviewAdapter);
            }
            else {
                reviewsNotAvailable.setVisibility(View.VISIBLE);
                reviewRecyclerView.setNestedScrollingEnabled(false);
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
            if (!movieTrailerList.isEmpty()) {
                movieTrailerAdapter = new MovieTrailerAdapter(movieTrailerList, DetailsActivity.this);
                trailerRecyclerView.setAdapter(movieTrailerAdapter);
            }
            else {
                trailersNotAvailable.setVisibility(View.VISIBLE);
                trailerRecyclerView.setNestedScrollingEnabled(false);
            }
        }
    }


    //Implemented below by referring to this blog post: https://medium.com/@rashi.karanpuria/create-beautiful-toggle-buttons-in-android-64d299050dfb
    // Implemented ic_favorite.xml/ic_favorite_border.xml and favorite_button.xml by referring to same post
    private void setUpFavoriteButtonAnimation() {
        scaleAnimation = new ScaleAnimation(0.7f, 1.0f, 0.7f, 1.0f, Animation.RELATIVE_TO_SELF, 0.7f, Animation.RELATIVE_TO_SELF, 0.7f);
        scaleAnimation.setDuration(500);
        bounceInterpolator = new BounceInterpolator();
        scaleAnimation.setInterpolator(bounceInterpolator);
    }

    private void setupFavoriteButtonCheckedState() {
        MovieDetailsViewModelFactory viewModelFactory = new MovieDetailsViewModelFactory(database, movie.getMovieId());

        MovieDetailsViewModel movieDetailsViewModel =
                ViewModelProviders.of(this, viewModelFactory).get(MovieDetailsViewModel.class);

        movieDetailsViewModel.checkIfMovieInDb().observe(this, movieInDb -> {
            if (movieInDb == null) {
                favoriteButton.setChecked(false);
            }
            else if (movie.getMovieId().equals(movieInDb.getMovieId())) {
                favoriteButton.setChecked(true);
            }
            else {
                favoriteButton.setChecked(false);
            }
        });
    }
}
