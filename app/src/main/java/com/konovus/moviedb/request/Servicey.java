package com.konovus.moviedb.request;

import com.konovus.moviedb.utils.Credentials;
import com.konovus.moviedb.utils.MovieAPI;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Servicey {

    private static Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
            .baseUrl(Credentials.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create());
    private static Retrofit retrofit = retrofitBuilder.build();

    private static MovieAPI movieAPI = retrofit.create(MovieAPI.class);

    public MovieAPI getMovieAPI(){
        return movieAPI;
    }
}
