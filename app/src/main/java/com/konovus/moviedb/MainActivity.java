package com.konovus.moviedb;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.os.Bundle;

import com.konovus.moviedb.models.MovieModel;
import com.konovus.moviedb.request.Servicey;
import com.konovus.moviedb.response.MovieResponse;
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
        findById(26389);
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
                                        + "\n score: " + movie.getVote_average()
                                        + "\n id: " + movie.getId());
                }
            }

            @Override
            public void onFailure(Call<MovieSearchResponse> call, Throwable t) {
                System.out.println("Error - " + t.getMessage());
            }
        });
    }
    private void findById(int id){
        MovieAPI movieAPI = Servicey.getMovieAPI();
        Call<MovieModel> responseCall = movieAPI.searchMovieById(id, Credentials.API_Key);
        responseCall.enqueue(new Callback<MovieModel>() {
            @Override
            public void onResponse(Call<MovieModel> call, Response<MovieModel> response) {
                if(response.isSuccessful()){
                    MovieModel movie = response.body();
                    System.out.println("\nTitle: " + movie.getTitle()
                            + "\n score: " + movie.getVote_average());
                }
            }

            @Override
            public void onFailure(Call<MovieModel> call, Throwable t) {

            }
        });
    }
}
