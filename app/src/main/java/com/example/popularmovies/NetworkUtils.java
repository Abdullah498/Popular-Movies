package com.example.popularmovies;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public  class NetworkUtils extends AsyncTask<String,Void,String> {

    @Override
    protected String doInBackground(String... strings) {
        final String REQUEST_METHOD="GET";
        final  int READ_TIMEOUT=15000;
        final  int CONNECTION_TIMEOUT=15000;
        final String REQUEST1_METHOD="POST";

        String url=strings[0];

        String results = null;
        String inputLine;

        try {
            URL mainurl=new URL(url);
            HttpURLConnection connection=(HttpURLConnection) mainurl.openConnection();
            connection.setRequestMethod(REQUEST_METHOD);
            connection.setRequestMethod(REQUEST1_METHOD);
            connection.setReadTimeout(READ_TIMEOUT);
            connection.setConnectTimeout(CONNECTION_TIMEOUT);
            connection.connect();

            InputStreamReader inputStreamReader=new InputStreamReader(connection.getInputStream());

            BufferedReader reader=new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder=new StringBuilder();

            while ((inputLine = reader.readLine()) != null) {
                stringBuilder.append(inputLine);
            }

            reader.close();
            inputStreamReader.close();
            results =stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.i("result",results);
        return results;
    }


    @Override
    protected void onPostExecute(String results ) {

        MainActivity.movies.clear();

        if (results != null) {
            try {
                JSONObject jsonObject = new JSONObject(results);

                JSONArray movieArray = jsonObject.getJSONArray("results");

                for (int i = 1; i < movieArray.length(); i++) {

                    MovieData movieData = new MovieData();

                    JSONObject movie = movieArray.getJSONObject(i);
                    String originalTitle=movie.getString("original_title");
                    String moviePosterImage="http://image.tmdb.org/t/p/w185/"+movie.getString("poster_path");
                    String overview=movie.getString("overview");
                    String voteAverage=movie.getString("vote_average");
                    String releaseDate=movie.getString("release_date");

                    movieData.setOriginalTitle(originalTitle);
                    movieData.setMoviePosterImage(moviePosterImage);
                    movieData.setOverview(overview);
                    movieData.setVoteAverage(voteAverage);
                    movieData.setReleaseDate(releaseDate);

                    MainActivity.movies.add(movieData);
                    Log.i("Size ",Integer.toString(MainActivity.movies.size()));
                    MainActivity.moviesAdapter.setmNumberItems(MainActivity.movies.size());
                    MainActivity.moviesAdapter.notifyDataSetChanged();

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}