package com.konovus.moviedb.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.palette.graphics.Palette;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Movie;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.konovus.moviedb.R;
import com.konovus.moviedb.models.MovieModel;

import java.util.concurrent.ExecutionException;

public class MovieDetailsActivity extends AppCompatActivity {

    private ImageView movie_img;
    private TextView title, description;
    private RatingBar ratingBar;
    private static ConstraintLayout root;

    private Palette.Swatch vibrant;
    private Palette.Swatch vibrant_Dark;
    private Palette.Swatch vibrant_Light;
    private Palette.Swatch muted;
    private Palette.Swatch muted_Dark;
    private Palette.Swatch muted_Light;
    private Palette.Swatch dominant;

    private int swatch_nr;

    private Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        movie_img = findViewById(R.id.movie_img_details);
        title = findViewById(R.id.movie_title_details);
        description = findViewById(R.id.description_details);
        ratingBar = findViewById(R.id.rating_bar_details);
        root = findViewById(R.id.root_details);

        getDataFromIntent();

//        Bitmap bitmap = ((BitmapDrawable) movie_img.getDrawable()).getBitmap();




    }

    private void getDataFromIntent(){
        if(getIntent().hasExtra("movie")){
            MovieModel movie = getIntent().getParcelableExtra("movie");

            title.setText(movie.getTitle());
            description.setText(movie.getOverview());
            ratingBar.setRating(movie.getVote_average());

            new LoadBitmap(this).execute(movie);

        }

    }


    private class LoadBitmap extends AsyncTask<MovieModel, Void, Void>{
        private Context context;
        public LoadBitmap(Context context){
            this.context = context;
        }

        @Override
        protected Void doInBackground(MovieModel... movieModels) {
            MovieModel movie = movieModels[0];
//            Glide.with(context)
//                    .load("https://image.tmdb.org/t/p/w500/" + movie.getPoster_path())
//                    .into(movie_img);
            FutureTarget<Bitmap> futureBitmap = Glide.with(context)
                    .asBitmap()
                    .load("https://image.tmdb.org/t/p/w500/" + movie.getPoster_path())
                           .submit();
            try {
                 bitmap = futureBitmap.get();

            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                @Override
                public void onGenerated(@Nullable Palette palette) {
                    BitmapDrawable ob = new BitmapDrawable(getResources(), bitmap);
                    movie_img.setBackground(ob);

                    dominant = palette.getDominantSwatch();
                    root.setBackgroundColor(dominant.getRgb());
                    title.setTextColor(dominant.getTitleTextColor());
                    description.setTextColor(dominant.getBodyTextColor());

                }
            });
        }
    }
}
