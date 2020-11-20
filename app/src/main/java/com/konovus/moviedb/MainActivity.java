package com.konovus.moviedb;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.os.Bundle;

import com.konovus.moviedb.models.MovieModel;
import com.konovus.moviedb.request.Servicey;
import com.konovus.moviedb.response.MovieSearchResponse;
import com.konovus.moviedb.utils.Credentials;
import com.konovus.moviedb.utils.MovieAPI;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getRetrofitResponse();
    }

    private void getRetrofitResponse(){
        MovieAPI movieAPI = Servicey.getMovieAPI();



        Call<MovieSearchResponse> responseCall = movieAPI
                .searchMovie(Credentials.API_Key,
                        "From paris with love","1");
        responseCall.enqueue(new Callback<MovieSearchResponse>() {
            @Override
            public void onResponse(Call<MovieSearchResponse> call, Response<MovieSearchResponse> response) {
                if(response.isSuccessful()){
                    List<MovieModel> movies = response.body().getMovies();
                    for(MovieModel movie : movies)
                        System.out.println("\nTitle: " + movie.getTitle()
                                        + "\n score: " + movie.getVote_average());
                }
            }

            @Override
            public void onFailure(Call<MovieSearchResponse> call, Throwable t) {
                System.out.println("Error - " + t.getMessage());
            }
        });
    }
}
