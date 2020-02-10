package nanodegree.udacity.popular_movies_stage2;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import nanodegree.udacity.popular_movies_stage1.R;
import nanodegree.udacity.popular_movies_stage2.model.Movie;

public class MovieDBAdapter extends RecyclerView.Adapter<MovieDBAdapter.MovieDBAdapterViewHolder>{

    private List<Movie> movieList;
    private MovieDBAdapterOnClickHandler movieDBAdapterOnClickHandler;

    public MovieDBAdapter(List<Movie> movieList, MovieDBAdapterOnClickHandler movieDBAdapterOnClickHandler) {
        this.movieList = movieList;
        this.movieDBAdapterOnClickHandler = movieDBAdapterOnClickHandler;
    }


    @NonNull
    @Override
    public MovieDBAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.movie_grid_list_view, viewGroup, false);
        return new MovieDBAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieDBAdapterViewHolder holder, int position) {
        String moviePoster = movieList.get(position).getMoviePoster();
        Picasso.get()
                .load(moviePoster)
                .fit()
                .into(holder.moviePoster);

        ViewCompat.setTransitionName(holder.moviePoster, movieList.get(position).getOriginalTitle());
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public interface MovieDBAdapterOnClickHandler {
        void onClick(int position, ImageView imageView, Movie movie);
    }

    public class MovieDBAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView moviePoster;

        public MovieDBAdapterViewHolder(View itemView) {
            super(itemView);
            moviePoster = itemView.findViewById(R.id.poster_list_view);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            movieDBAdapterOnClickHandler.onClick(position, moviePoster, movieList.get(position));
        }
    }

    public void setFavoriteMovies(List<Movie> favoriteMovieList) {
        this.movieList = favoriteMovieList;
    }
}
