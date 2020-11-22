package com.konovus.moviedb.request;

import com.konovus.moviedb.AppExecutors;
import com.konovus.moviedb.models.MovieModel;
import com.konovus.moviedb.response.MovieSearchResponse;
import com.konovus.moviedb.utils.Credentials;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Response;

public class MovieApiClient {

    private MutableLiveData<List<MovieModel>> mMovies;

    private static MovieApiClient instance;

    private RetrieveMoviesRunnable retrieveMoviesRunnable;

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

    public void searchMovieApi(String query, int pageNumber){
        if(retrieveMoviesRunnable != null)
            retrieveMoviesRunnable = null;
        retrieveMoviesRunnable = new RetrieveMoviesRunnable(query, pageNumber);
        final Future myHandler = AppExecutors.getInstance().getmNetworkIO().submit(retrieveMoviesRunnable);

        AppExecutors.getInstance().getmNetworkIO().schedule(new Runnable() {
            @Override
            public void run() {
                myHandler.cancel(true);

            }
        }, 5000, TimeUnit.MILLISECONDS);
    }

    private class RetrieveMoviesRunnable implements Runnable{
        private String query;
        private int pageNumber;
        boolean cancelRequest;

        public RetrieveMoviesRunnable(String query, int pageNumber) {
            this.query = query;
            this.pageNumber = pageNumber;
            cancelRequest = false;
        }

        @Override
        public void run() {
            try {

                Response response = getMovies(query, pageNumber).execute();
                if(cancelRequest)
                    return;
                if(response.isSuccessful()){
                    List<MovieModel> list = ((MovieSearchResponse) response.body()).getMovies();
                    if(pageNumber == 1)
                        mMovies.postValue(list);
                    else{
                        List<MovieModel> currentList = mMovies.getValue();
                        currentList.addAll(list);
                        mMovies.postValue(currentList);
                    }
                } else mMovies.postValue(null);
            } catch (IOException e) {
                e.printStackTrace();
            }



        }

        private Call<MovieSearchResponse> getMovies(String query, int pageNumber){
            return Servicey.getMovieAPI().searchMovie(
                    Credentials.API_Key,
                    query,
                    String.valueOf(pageNumber)
            );
        }

        private void cancelRequest(){
            cancelRequest = true;
        }
    }
}
