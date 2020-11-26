package com.konovus.moviedb.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.konovus.moviedb.R;
import com.konovus.moviedb.models.MovieModel;
import com.konovus.moviedb.utils.Credentials;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    private List<MovieModel> movies;
    private OnMovieListener onMovieListener;

    public static final int DISPLAY_POP = 1;
    public static final int DISPLAY_SEARCH = 2;

    private boolean isPopular;

    public MovieAdapter(OnMovieListener onMovieListener, boolean isPopular) {
        this.onMovieListener = onMovieListener;
        this.isPopular = isPopular;
    }

    @NonNull
    @Override
    public MovieAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        if(viewType == DISPLAY_POP){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_list_item2, parent, false);
            return new ViewHolder(view, onMovieListener);
        } else{
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_list_item, parent, false);
            return new ViewHolder(view, onMovieListener);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapter.ViewHolder holder, int position) {
        int itemViewType = getItemViewType(position);
        if(itemViewType == DISPLAY_SEARCH){
            holder.title.setText(movies.get(position).getTitle());
            holder.releaseDate.setText(movies.get(position).getRelease_date());
            holder.original_language.setText(movies.get(position).getOriginal_language());
        }

        holder.ratingBar.setRating(movies.get(position).getVote_average()/2);

        Glide.with(holder.imageView.getContext())
                .load("https://image.tmdb.org/t/p/w500/" + movies.get(position).getPoster_path())
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        if(movies != null)
            return movies.size();
        else return 0;
    }

    @Override
    public int getItemViewType(int position) {
        if(isPopular)
            return DISPLAY_POP;
        else
            return DISPLAY_SEARCH;
    }

    public void setMovies(List<MovieModel> movies){
        this.movies = movies;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

//        Widgets
        TextView title, releaseDate, original_language;
        ImageView imageView;
        RatingBar ratingBar;

//        ClickListener
        OnMovieListener onMovieListener;

        public ViewHolder(@NonNull View itemView, OnMovieListener onMovieListener) {
            super(itemView);

            title = itemView.findViewById(R.id.movie_title);
            releaseDate = itemView.findViewById(R.id.movie_releaseDate);
            original_language = itemView.findViewById(R.id.movie_duration);
            imageView = itemView.findViewById(R.id.movie_img);
            ratingBar = itemView.findViewById(R.id.rating_bar);

            this.onMovieListener = onMovieListener;
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            onMovieListener.OnMovieClick(getAdapterPosition());
        }
    }

    public MovieModel getSelectedMovie(int position){
        if(movies != null && movies.size() > 0)
            return movies.get(position);
        return null;
    }

    public interface OnMovieListener{

        void OnMovieClick(int position);
        void OnCategoryClick(String category);
    }
}
