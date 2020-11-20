package com.konovus.moviedb.utils;

import com.konovus.moviedb.models.MovieModel;
import com.konovus.moviedb.response.MovieResponse;
import com.konovus.moviedb.response.MovieSearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieAPI {

    @GET("search/movie")
    Call<MovieSearchResponse> searchMovie(
            @Query("api_key") String key,
            @Query("query") String query,
            @Query("page") String page
    );
    @GET("movie/{movie_id}")
    Call<MovieModel> searchMovieById(
            @Path("movie_id") int movie_id,
            @Query("api_key") String api_key
    );
}
