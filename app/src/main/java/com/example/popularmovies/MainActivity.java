package com.example.popularmovies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MoviesAdapter.ListItemClickListener {

    public static ArrayList<MovieData> movies=new ArrayList<>();
    RecyclerView recyclerView;
    public static MoviesAdapter moviesAdapter;
    int numberOfItems;
    NetworkUtils networkUtils=new NetworkUtils();
    Button imageView;

    static int VISIBILITY;

    static String   url="https://api.themoviedb.org/3/movie/now_playing?api_key=5a4d8ca56550fbd8f8015c4b02a70e71";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        imageView=findViewById(R.id.start_page);
        if(VISIBILITY==View.GONE)
            imageView.setVisibility(View.GONE);

        recyclerView=findViewById(R.id.rv_movies);
        moviesAdapter=new MoviesAdapter(this,numberOfItems,this);
        GridLayoutManager gridLayoutManager =new GridLayoutManager(this,2);

        recyclerView.setLayoutManager(gridLayoutManager);

        //recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(moviesAdapter);
        Log.i("numberOfItems",Integer.toString(numberOfItems));
        moviesAdapter.notifyDataSetChanged();

    }
    public void  conectToApi(String url){
        networkUtils.execute(url);
        numberOfItems=movies.size();
        Log.i("numberOfItems",Integer.toString(numberOfItems));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sort_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.sort_by_popular:
                networkUtils.execute("http://api.themoviedb.org/3/movie/popular?api_key=5a4d8ca56550fbd8f8015c4b02a70e71");
                break;
            case R.id.sort_by_most_rated:
                networkUtils.execute("http://api.themoviedb.org/3/movie/top_rated?api_key=5a4d8ca56550fbd8f8015c4b02a70e71");
                break;

            case R.id.now_playing:
                networkUtils.execute("http://api.themoviedb.org/3/movie/now_playing?api_key=5a4d8ca56550fbd8f8015c4b02a70e71");
                break;

        }

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

        return true;
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        Log.i("clickedItemIndex",Integer.toString(clickedItemIndex));
        Intent intent=new Intent(MainActivity.this,DetailActivity.class);
        intent.putExtra(Intent.EXTRA_TEXT,clickedItemIndex);
        startActivity(intent);
    }

   @Override
    public void onBackPressed() {

        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);

        startActivity(startMain);

    }

    public void startPage(View view) {
        imageView=findViewById(R.id.start_page);
        imageView.setVisibility(View.GONE);
        VISIBILITY=View.GONE;
       networkUtils.execute("http://api.themoviedb.org/3/movie/now_playing?api_key=5a4d8ca56550fbd8f8015c4b02a70e71");
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
