package com.konovus.moviedb.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.Bundle;

import com.konovus.moviedb.R;
import com.konovus.moviedb.adapters.MovieAdapter;
import com.konovus.moviedb.models.MovieModel;
import com.konovus.moviedb.request.Servicey;
import com.konovus.moviedb.response.MovieSearchResponse;
import com.konovus.moviedb.utils.Credentials;
import com.konovus.moviedb.utils.MovieAPI;
import com.konovus.moviedb.viewmodels.MovieListViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MovieAdapter.OnMovieListener {

    private RecyclerView recyclerView;
    private MovieAdapter movieAdapter;

    private MovieListViewModel movieListViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.recycler_view);

        movieListViewModel = new ViewModelProvider(this).get(MovieListViewModel.class);

        configureRecyclerView();
        ObserveAnyChange();
        configureSearchView();

    }

    private void ObserveAnyChange(){
        movieListViewModel.getMovies().observe(this, new Observer<List<MovieModel>>() {
            @Override
            public void onChanged(List<MovieModel> movieModels) {
                if(movieModels != null)
                    movieAdapter.setMovies(movieModels);
            }
        });
    }

    private void searchMovieApi(String query, int pageNumber){
        movieListViewModel.searchMovieApi(query, pageNumber);
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

    private void configureRecyclerView(){
//        LiveData cannot be passed to constructor
        movieAdapter = new MovieAdapter(this);

        recyclerView.setAdapter(movieAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if(!recyclerView.canScrollVertically(1)){
                    movieListViewModel.searchNextPage();
                }
            }
        });
    }
    private void findMovieById(int id){
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

    @Override
    public void OnMovieClick(int position) {
        Intent intent = new Intent(MainActivity.this, MovieDetailsActivity.class);
        intent.putExtra("movie", movieAdapter.getSelectedMovie(position));
        startActivity(intent);
    }

    @Override
    public void OnCategoryClick(String category) {

    }

    private void configureSearchView(){
        final SearchView searchView = findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                movieListViewModel.searchMovieApi(query, 1);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }
}
