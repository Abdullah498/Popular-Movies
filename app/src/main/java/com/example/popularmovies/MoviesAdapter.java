package com.example.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {

    private static final String TAG = MoviesAdapter.class.getSimpleName();
    ArrayList<MovieData> movies;
    private Context mcontext;


    public MoviesAdapter(Context context, ArrayList<MovieData> movies) {
        mcontext=context;
        this.movies=movies;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(mcontext).inflate(R.layout.movie_poster,viewGroup,false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
       final MovieData movieData=movies.get(position);
       Picasso.with(mcontext).load(movieData.getMoviePosterImage()).into(holder.poster);
       holder.poster.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent=new Intent(mcontext,DetailActivity.class);
               intent.putExtra("posterPath",movieData.getMoviePosterImage());
               intent.putExtra("title",movieData.getOriginalTitle());
               intent.putExtra("overview",movieData.getOverview());
               intent.putExtra("date",movieData.getReleaseDate());
               intent.putExtra("voteAverage",movieData.getVoteAverage());
               intent.putExtra("id", movieData.getId());
               mcontext.startActivity(intent);
           }
       });
    }


    @Override
    public int getItemCount() {
        return movies.size();
    }



    class MovieViewHolder extends RecyclerView.ViewHolder {

        ImageView poster;

        public MovieViewHolder(View itemView) {
            super(itemView);
            poster = itemView.findViewById(R.id.iv_movie_poster);
        }
    }
}
