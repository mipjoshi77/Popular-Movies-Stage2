package nanodegree.udacity.popular_movies_stage2;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import nanodegree.udacity.popular_movies_stage1.R;
import nanodegree.udacity.popular_movies_stage2.model.Movie;

public class MovieTrailerAdapter extends RecyclerView.Adapter<MovieTrailerAdapter.MovieTrailerViewHolder> {

    private List<Movie> trailerList;
    private MovieDBTrailerOnClickHandler movieDBTrailerOnClickHandler;

    public MovieTrailerAdapter(List<Movie> trailerList, MovieDBTrailerOnClickHandler movieDBTrailerOnClickHandler) {
        this.trailerList = trailerList;
        this.movieDBTrailerOnClickHandler = movieDBTrailerOnClickHandler;
    }

    @NonNull
    @Override
    public MovieTrailerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.movie_trailer_list_item, viewGroup, false);
        return new MovieTrailerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieTrailerViewHolder holder, int position) {
        String trailerThumbnail = trailerList.get(position).getTrailerImageUrl();
        Log.d("ID4", "thumbnail URL: " +trailerThumbnail);
        Picasso.get()
                .load(trailerThumbnail)
                .into(holder.trailerThumbnail);
    }

    @Override
    public int getItemCount() {
        return trailerList.size();
    }

    public interface MovieDBTrailerOnClickHandler {
        void onClick(int position);
    }

    public class MovieTrailerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView trailerThumbnail;

        public MovieTrailerViewHolder(View itemView) {
            super(itemView);
            trailerThumbnail = itemView.findViewById(R.id.trailer_thumbnail);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            movieDBTrailerOnClickHandler.onClick(position);
        }
    }
}
