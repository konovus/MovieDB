package com.konovus.moviedb.request;

import com.konovus.moviedb.utils.Credentials;
import com.konovus.moviedb.utils.MovieAPI;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Servicey {

    private static OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .build();

    private static Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
            .baseUrl(Credentials.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = retrofitBuilder.build();

    private static MovieAPI movieAPI = retrofit.create(MovieAPI.class);

    public static MovieAPI getMovieAPI(){
        return movieAPI;
    }
}
