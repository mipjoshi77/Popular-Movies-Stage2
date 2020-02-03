package nanodegree.udacity.popular_movies_stage2;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.net.URL;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import nanodegree.udacity.popular_movies_stage1.R;
import nanodegree.udacity.popular_movies_stage2.model.Movie;
import nanodegree.udacity.popular_movies_stage2.utilities.JsonUtils;
import nanodegree.udacity.popular_movies_stage2.utilities.NetworkUtils;

public class MainActivity extends AppCompatActivity implements MovieDBAdapter.MovieDBAdapterOnClickHandler {

    @BindView(R.id.movies_rv)
    RecyclerView recyclerView;
    @BindView(R.id.error_message)
    TextView errorMessage;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    private MovieDBAdapter adapter;
    private List<Movie> movieList;
    private String query = "popular";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        loadMovieData();

    }

    private void loadMovieData() {
        String movieQueryType = query;
        showMovieDataResults();
        new FetchMovieTask().execute(movieQueryType);
    }

    private void showMovieDataResults() {
        errorMessage.setVisibility(View.INVISIBLE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {
        recyclerView.setVisibility(View.INVISIBLE);
        errorMessage.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(int position) {
        Intent intentToStartDetailsActivity = new Intent(this, DetailsActivity.class);
        intentToStartDetailsActivity.putExtra(Intent.EXTRA_TEXT, position);
        intentToStartDetailsActivity.putExtra("id", movieList.get(position).getMovideId());
        intentToStartDetailsActivity.putExtra("title", movieList.get(position).getOriginalTitle());
        intentToStartDetailsActivity.putExtra("poster", movieList.get(position).getMoviePoster());
        intentToStartDetailsActivity.putExtra("rate", movieList.get(position).getRating());
        intentToStartDetailsActivity.putExtra("release", movieList.get(position).getReleaseDate());
        intentToStartDetailsActivity.putExtra("overview", movieList.get(position).getOverview());

        startActivity(intentToStartDetailsActivity);
    }

    public class FetchMovieTask extends AsyncTask<String, Void, List<Movie>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<Movie> doInBackground(String... params) {
            if (params.length == 0){
                return null;
            }

            String sortBy = params[0];
            URL movieRequestUrl = NetworkUtils.urlBuilder(sortBy);
            Log.d("ID4", "movieRequestUrl: " +movieRequestUrl.toString());

            try {
                String jsonMovieResponse = NetworkUtils.getResponseFromHttpUrl(movieRequestUrl);

                movieList
                        = JsonUtils.parseMovieDbJson(jsonMovieResponse);

                return movieList;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<Movie> movieList) {
            progressBar.setVisibility(View.INVISIBLE);
            if (movieList != null) {
                showMovieDataResults();
                adapter = new MovieDBAdapter(movieList, MainActivity.this);
                recyclerView.setAdapter(adapter);
            }
            else {
                showErrorMessage();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuItemSelected = item.getItemId();

        if (menuItemSelected == R.id.action_popular) {
            query = "popular";
            loadMovieData();
            return true;
        }

        if (menuItemSelected == R.id.action_top_rated) {
            query = "top_rated";
            loadMovieData();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
