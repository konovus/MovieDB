package com.konovus.moviedb.repositories;

import com.konovus.moviedb.models.MovieModel;
import com.konovus.moviedb.request.MovieApiClient;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class MovieRepository {

    private static MovieRepository instance;
    private MovieApiClient movieApiClient;

    private String mQuery;
    private int mPageNumber;

    public static MovieRepository getInstance(){
        if(instance == null)
            instance = new MovieRepository();
        return instance;
    }

    private MovieRepository(){
        movieApiClient = MovieApiClient.getInstance();
    }

    public LiveData<List<MovieModel>> getMovies(){return  movieApiClient.getMovies();}
    public LiveData<List<MovieModel>> getPopularMovies(){return  movieApiClient.getMoviesPopular();}

    public void searchMovieApi(String query, int pageNumber){
        mQuery = query;
        if(mPageNumber == 0)
            mPageNumber = pageNumber;
        movieApiClient.searchMovieApi(query, pageNumber);
    }
    public void searchNextPage(){
        searchMovieApi(mQuery, mPageNumber++);
    }

}
