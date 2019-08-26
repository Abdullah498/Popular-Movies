package com.example.popularmovies;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.squareup.picasso.Picasso;

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

public class DetailActivity extends AppCompatActivity {

    public  ArrayList<MovieData> trailers=new ArrayList<>();
    RecyclerView recyclerView;
    public static TrailersAdapter trailersAdapter;

    public ArrayList<MovieData> reviews=new ArrayList<>();
    RecyclerView recyclerView2;
    public static ReviewAdapter reviewAdapter;


    ImageView mainPosetr;
    TextView overview;
    TextView averageVote;
    TextView releaseDate;

    Button favouriteBtn;

    Toolbar mainTitle;

    MovieData movieData;

    AppDatabase appDatabase;
    FavouritesViewModel favouritesViewModel;

    int favFlag=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        appDatabase=AppDatabase.getInstance(this);
        favouritesViewModel= ViewModelProviders.of(this).get(FavouritesViewModel.class);

        favouriteBtn=findViewById(R.id.favouritesBtn);

        mainPosetr = findViewById(R.id.main_poster);
        overview = findViewById(R.id.overview);
        averageVote = findViewById(R.id.averageVote);
        releaseDate = findViewById(R.id.releaseDate);
        mainTitle = findViewById(R.id.main_title);

        String posterPath=getIntent().getStringExtra("posterPath");
        String title=getIntent().getStringExtra("title");
        String mOverview=getIntent().getStringExtra("overview");
        String date=getIntent().getStringExtra("date");
        String voteAverage=getIntent().getStringExtra("voteAverage");
        final String movieId=getIntent().getStringExtra("id");


        Picasso.with(this).load(getIntent().getStringExtra("posterPath")).into(mainPosetr);

        mainTitle.setTitle(getIntent().getStringExtra("title"));

        overview.setText(getIntent().getStringExtra("overview"));
        averageVote.setText(getIntent().getStringExtra("title"));
        releaseDate.setText(getIntent().getStringExtra("date"));
        averageVote.setText(getIntent().getStringExtra("voteAverage"));

        movieData=new MovieData(title,posterPath,mOverview,voteAverage,date,movieId);


        //trailers

        recyclerView=findViewById(R.id.rv_trailers);
        LinearLayoutManager linearLayoutManager =new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        //linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);


        recyclerView.setHasFixedSize(false);

        new TrailersAsyncTask().execute("https://api.themoviedb.org/3/movie/"+getIntent().getStringExtra("id")+"/videos?api_key=5a4d8ca56550fbd8f8015c4b02a70e71");
        new ReviewsAsyncTask().execute("https://api.themoviedb.org/3/movie/"+getIntent().getStringExtra("id")+"/reviews?api_key=5a4d8ca56550fbd8f8015c4b02a70e71");

        //reviews
        recyclerView2=findViewById(R.id.rv_reviews);

        LinearLayoutManager linearLayoutManager2 =new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);

        recyclerView2.setLayoutManager(linearLayoutManager2);


        recyclerView2.setHasFixedSize(false);



        //favourites Button

        final SharedPreferences preferences = getSharedPreferences(movieId, Context.MODE_PRIVATE);
        favFlag = preferences.getInt(movieId, 0);


        if (favFlag ==1){
            favouriteBtn.setBackgroundColor(getResources().getColor(R.color.white));
            favouriteBtn.setText("Added to favourites".toUpperCase());
        }

        favouriteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (favFlag==0){
                    new AddToFavouriteAsyncTask().execute();
                    favouriteBtn.setBackgroundColor(getResources().getColor(R.color.white));
                    favouriteBtn.setText("Added to favourites".toUpperCase());
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putInt(movieId, 1);
                    editor.apply();
                    favFlag = 1;
                }else{
                    favouritesViewModel.deleteMovie(movieData);
                    favouriteBtn.setBackgroundResource(R.color.red);
                    favouriteBtn.setText("Add to favourites".toUpperCase());
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putInt(movieId, 0);
                    editor.apply();
                    favFlag = 0;
                    Toast.makeText(getApplicationContext(), " Movie Deleted From Favourites", Toast.LENGTH_SHORT).show();
                }
            }


        });


    }

    class TrailersAsyncTask extends AsyncTask<String,Void,ArrayList<MovieData>> {

        @Override
        protected ArrayList<MovieData> doInBackground(String... siteUrl) {
            String text;

            if (trailers.isEmpty()){
                try {

                    URL url = new URL(siteUrl[0]);

                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.connect();

                    InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
                    text = stream2String(inputStream);

                    trailers = extractFromJson(text);

                    return trailers;


                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else {
                return trailers;
            }
            return trailers;
        }

        @Override
        protected void onPostExecute(ArrayList<MovieData> movieData) {
            trailersAdapter=new TrailersAdapter(DetailActivity.this,movieData);
            recyclerView.setAdapter(trailersAdapter);
            trailersAdapter.notifyDataSetChanged();
            Toast.makeText(DetailActivity.this,"hhhhh",Toast.LENGTH_SHORT).show();
        }
    }
    public ArrayList<MovieData> extractFromJson(String json){
        ArrayList<MovieData> movies = new ArrayList<>();
        try {

            JSONObject root = new JSONObject(json);
            JSONArray results = root.getJSONArray("results");

            for (int i=0; i<results.length(); i++){

                JSONObject currentMovie = results.getJSONObject(i);

               String key=currentMovie.getString("key");
               String name=currentMovie.getString("name");

                movies.add(new MovieData(key,name));
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
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    class ReviewsAsyncTask extends AsyncTask<String,Void,ArrayList<MovieData>> {

        @Override
        protected ArrayList<MovieData> doInBackground(String... siteUrl) {
            String text;

            if (reviews.isEmpty()){
                try {

                    URL url = new URL(siteUrl[0]);

                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.connect();

                    InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
                    text = stream2String(inputStream);

                    reviews = extractReviewsFromJson(text);

                    return reviews;


                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else {
                return reviews;
            }
            return reviews;
        }

        @Override
        protected void onPostExecute(ArrayList<MovieData> movieData) {
            reviewAdapter=new ReviewAdapter(DetailActivity.this,movieData);
            recyclerView2.setAdapter(reviewAdapter);
            reviewAdapter.notifyDataSetChanged();
            Toast.makeText(DetailActivity.this,"LoooooL",Toast.LENGTH_SHORT).show();
        }
    }
    public ArrayList<MovieData> extractReviewsFromJson(String json){
        ArrayList<MovieData> movies = new ArrayList<>();
        try {

            JSONObject root = new JSONObject(json);
            JSONArray results = root.getJSONArray("results");

            for (int i=0; i<results.length(); i++){

                JSONObject currentMovie = results.getJSONObject(i);

                String author=currentMovie.getString("author");
                String content=currentMovie.getString("content");
                String url=currentMovie.getString("url");

                movies.add(new MovieData(author,content,url));
            }

            return movies;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;


    }

    public class AddToFavouriteAsyncTask extends AsyncTask<Void, Void, Void>{
        @Override
        protected Void doInBackground(Void... voids) {
            appDatabase.movieDao().insertMovie(movieData);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Toast.makeText(getApplicationContext(), "Movie Added To Favourites", Toast.LENGTH_SHORT).show();
        }
    }

}
