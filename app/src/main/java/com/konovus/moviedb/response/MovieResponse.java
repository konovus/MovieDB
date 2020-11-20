package com.konovus.moviedb.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.konovus.moviedb.models.MovieModel;

public class MovieResponse {

    @SerializedName("results")
    @Expose
    private MovieModel movieModel;

    public MovieModel getMovieModel(){
        return movieModel;
    }

    @Override
    public String toString() {
        return "MovieResponse{" +
                "movieModel=" + movieModel +
                '}';
    }
}
