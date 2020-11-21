package com.konovus.moviedb.viewmodels;

import com.konovus.moviedb.models.MovieModel;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MovieListViewModel extends ViewModel {

//    LiveData
    private MutableLiveData<List<MovieModel>> mMovies = new MutableLiveData<>();

    public MovieListViewModel() {}

    public LiveData<List<MovieModel>> getMovies(){
        return mMovies;
    }
}
