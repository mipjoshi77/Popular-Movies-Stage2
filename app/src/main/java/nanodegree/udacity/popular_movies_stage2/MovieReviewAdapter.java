package nanodegree.udacity.popular_movies_stage2;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import nanodegree.udacity.popular_movies_stage1.R;
import nanodegree.udacity.popular_movies_stage2.model.Movie;

public class MovieReviewAdapter extends RecyclerView.Adapter<MovieReviewAdapter.MovieReviewViewHolder>{

    private Context context;
    private List<Movie> movieReviewList;

    public MovieReviewAdapter(Context context, List<Movie> movieReviewList) {
        this.context = context;
        this.movieReviewList = movieReviewList;
    }

    @NonNull
    @Override
    public MovieReviewViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.movie_review_list_item, viewGroup, false);
        return new MovieReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieReviewViewHolder holder, final int position) {
        holder.reviewAuthor.setText(movieReviewList.get(position).getReviewAuthor());
        holder.reviewContent.setText(movieReviewList.get(position).getReviewContent());
        holder.fullReviewButton.setOnClickListener(view -> {
            Intent openFullReviewPage = new Intent(Intent.ACTION_VIEW);
            openFullReviewPage.setData(Uri.parse(movieReviewList.get(position).getReviewUrl()));
            context.startActivity(openFullReviewPage);
        });
    }

    @Override
    public int getItemCount() {
        return movieReviewList.size();
    }

    public class MovieReviewViewHolder extends RecyclerView.ViewHolder {

        private TextView reviewAuthor;
        private TextView reviewContent;
        private Button fullReviewButton;

        public MovieReviewViewHolder(View itemView) {
            super(itemView);
            reviewAuthor = itemView.findViewById(R.id.review_author);
            reviewContent = itemView.findViewById(R.id.review_content);
            fullReviewButton = itemView.findViewById(R.id.full_review_button);
        }
    }
}
