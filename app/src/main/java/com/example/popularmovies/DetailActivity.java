package com.example.popularmovies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    ImageView mainPosetr;
    TextView overview;
    TextView averageVote;
    TextView releaseDate;

    Toolbar mainTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mainPosetr=findViewById(R.id.main_poster);
        overview=findViewById(R.id.overview);
        averageVote=findViewById(R.id.averageVote);
        releaseDate=findViewById(R.id.releaseDate);

        mainTitle=findViewById(R.id.main_title);

        Intent intent=getIntent();
        if (intent.hasExtra(Intent.EXTRA_TEXT)) {
            int index = intent.getIntExtra(Intent.EXTRA_TEXT,0);

            Picasso.with(this).load(MainActivity.movies.get(index).getMoviePosterImage()).into(mainPosetr);

            mainTitle.setTitle(MainActivity.movies.get(index).getOriginalTitle());

            overview.setText(MainActivity.movies.get(index).getOverview());
            averageVote.setText(MainActivity.movies.get(index).getVoteAverage());
            releaseDate.setText(MainActivity.movies.get(index).getReleaseDate());
        }
    }
}
