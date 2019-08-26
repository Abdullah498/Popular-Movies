package com.example.popularmovies;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;

import java.util.ArrayList;
import java.util.List;

public class FavouritesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MoviesAdapter moviesAdapter;
    private ArrayList<MovieData> favouritesMovies;

    private FavouritesViewModel favouritesViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);

        favouritesMovies=new ArrayList<>();
        favouritesMovies.add(new MovieData("http://image.tmdb.org/t/p/w342\\/a4BfxRK8dBgbQqbRxPs8kmLd8LG.jpg"));
        favouritesMovies.add(new MovieData("http://image.tmdb.org/t/p/w342\\/a4BfxRK8dBgbQqbRxPs8kmLd8LG.jpg"));
        favouritesMovies.add(new MovieData("http://image.tmdb.org/t/p/w342\\/a4BfxRK8dBgbQqbRxPs8kmLd8LG.jpg"));
        favouritesMovies.add(new MovieData("http://image.tmdb.org/t/p/w342\\/a4BfxRK8dBgbQqbRxPs8kmLd8LG.jpg"));

        recyclerView=findViewById(R.id.rv_favourites);
        moviesAdapter=new MoviesAdapter(this,new ArrayList<MovieData>());
        GridLayoutManager gridLayoutManager =new GridLayoutManager(this,numberOfColumns());
        recyclerView.setHasFixedSize(false);

        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(moviesAdapter);
        moviesAdapter.notifyDataSetChanged();

        favouritesViewModel= ViewModelProviders.of(this).get(FavouritesViewModel.class);
        favouritesViewModel.getMoviesListLiveData().observe(FavouritesActivity.this, new Observer<List<MovieData>>() {
            @Override
            public void onChanged(List<MovieData> movies) {
                moviesAdapter.setMovies(movies);
            }
        });
    }

    //Here you can dynamically calculate the number of columns and the layout will adapt to the screen size and orientation
    private int numberOfColumns() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        // You can change this divider to adjust the size of the item
        int widthDivider = 400;
        int width = displayMetrics.widthPixels;
        int nColumns = width / widthDivider;
        if (nColumns < 2) return 2; //to keep the grid aspect
        return nColumns;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        startActivity(new Intent(this,MainActivity.class).putExtra("pop",getIntent().getStringExtra("currentUrl")));
    }
}
