package com.konovus.moviedb.request;

import com.konovus.moviedb.AppExecutors;
import com.konovus.moviedb.models.MovieModel;

import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class MovieApiClient {

    private MutableLiveData<List<MovieModel>> mMovies;

    private static MovieApiClient instance;

    public static MovieApiClient getInstance(){
        if(instance == null)
            instance = new MovieApiClient();
        return instance;
    }

    public MovieApiClient() {
        mMovies = new MutableLiveData<>();
    }

    public LiveData<List<MovieModel>> getMovies(){
        return mMovies;
    }

    public void searchMovieApi(){
        final Future myHandler = AppExecutors.getInstance().getmNetworkIO().submit(new Runnable() {
            @Override
            public void run() {

            }
        });

        AppExecutors.getInstance().getmNetworkIO().schedule(new Runnable() {
            @Override
            public void run() {
                myHandler.cancel(true);
            }
        }, 5000, TimeUnit.MICROSECONDS);
    }
}
