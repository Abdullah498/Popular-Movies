package com.example.popularmovies;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{

    RecyclerView recyclerView;
    public static MoviesAdapter moviesAdapter;
    public ArrayList<MovieData> movies=new ArrayList<>();

    Button startPage;

    static int VISIBILITY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        recyclerView=findViewById(R.id.rv_movies);
        GridLayoutManager gridLayoutManager =new GridLayoutManager(this,numberOfColumns());

        recyclerView.setLayoutManager(gridLayoutManager);

        if(!getIntent().hasExtra("pop")){

            Toast.makeText(this,"else",Toast.LENGTH_SHORT).show();
            new MoveisAsyncTask().execute("http://api.themoviedb.org/3/movie/now_playing?api_key=5a4d8ca56550fbd8f8015c4b02a70e71");
        }
        else {
            Toast.makeText(this,"test",Toast.LENGTH_SHORT).show();
            new MoveisAsyncTask().execute(getIntent().getStringExtra("pop"));
        }
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sort_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.sort_by_popular:
                Intent intent=new Intent(this,MainActivity.class).putExtra("pop","http://api.themoviedb.org/3/movie/popular?api_key=5a4d8ca56550fbd8f8015c4b02a70e71");
                startActivity(intent);
                finish();
                Toast.makeText(this,"Popular",Toast.LENGTH_SHORT).show();
                break;
            case R.id.sort_by_most_rated:
                getIntent().putExtra("pop","http://api.themoviedb.org/3/movie/top_rated?api_key=5a4d8ca56550fbd8f8015c4b02a70e71");
                finish();
                Toast.makeText(this,"top",Toast.LENGTH_SHORT).show();
                startActivity(getIntent());
                break;

            case R.id.now_playing:
                getIntent().putExtra("pop","http://api.themoviedb.org/3/movie/now_playing?api_key=5a4d8ca56550fbd8f8015c4b02a70e71");
                finish();
                Toast.makeText(this,"now",Toast.LENGTH_SHORT).show();
                startActivity(getIntent());
                break;

        }

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

        return true;
    }
    //this method make app return to home directly when backButton clicked
   @Override
    public void onBackPressed() {

        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);

        startActivity(intent);

    }


    class MoveisAsyncTask extends AsyncTask<String,Void,ArrayList<MovieData>>{

        @Override
        protected ArrayList<MovieData> doInBackground(String... siteUrl) {
            String text;

            if (movies.isEmpty()){
                try {

                    URL url = new URL(siteUrl[0]);

                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.connect();

                    InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
                    text = stream2String(inputStream);

                    movies = extractFromJson(text);

                    return movies;


                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else {
                return movies;
            }
            return movies;
        }

        @Override
        protected void onPostExecute(ArrayList<MovieData> movieData) {
            moviesAdapter=new MoviesAdapter(MainActivity.this,movieData);
            recyclerView.setAdapter(moviesAdapter);
            moviesAdapter.notifyDataSetChanged();
        }
    }
    public ArrayList<MovieData> extractFromJson(String json){
        ArrayList<MovieData> movies = new ArrayList<>();
        try {

            JSONObject root = new JSONObject(json);
            JSONArray results = root.getJSONArray("results");

            for (int i=0; i<results.length(); i++){

                JSONObject currentMovie = results.getJSONObject(i);

                String title = currentMovie.getString("title");
                String posterPath = currentMovie.getString("poster_path");
                String overview = currentMovie.getString("overview");
                String voteAvg = currentMovie.getString("vote_average");
                String releaseDate = currentMovie.getString("release_date");
                String movieId = currentMovie.getString("id");

                movies.add(new MovieData(title, "http://image.tmdb.org/t/p/w342"+posterPath, overview, voteAvg, releaseDate, movieId));
            }

            return movies;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;


    }
    public String stream2String(InputStream inputStream){

        String line;
        StringBuilder text = new StringBuilder();

        BufferedReader reader =  new BufferedReader(new InputStreamReader(inputStream));

        try{
            while((line = reader.readLine()) != null){
                text.append(line);
            }
        }catch (IOException e){}

        return text.toString();
    }

}


