package com.konovus.moviedb.repositories;

import com.konovus.moviedb.models.MovieModel;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class MovieRepository {

    private static MovieRepository instance;

    private MutableLiveData<List<MovieModel>> mMovies;

    public static MovieRepository getInstance(){
        if(instance == null)
            instance = new MovieRepository();
        return instance;
    }

    private MovieRepository(){
        mMovies = new MutableLiveData<>();
    }

    public LiveData<List<MovieModel>> getMovies(){return mMovies;}


}
