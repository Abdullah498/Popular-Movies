package com.example.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    private static final String TAG = ReviewAdapter.class.getSimpleName();
    ArrayList<MovieData> reviews;
    private Context mcontext;


    public ReviewAdapter(Context context, ArrayList<MovieData> reviews) {
        mcontext=context;
        this.reviews=reviews;
    }

    @NonNull
    @Override
    public ReviewAdapter.ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(mcontext).inflate(R.layout.review_item,viewGroup,false);
        return new ReviewAdapter.ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReviewAdapter.ReviewViewHolder holder, int position) {
        final MovieData reviewdata=reviews.get(position);
        holder.author.setText(reviewdata.getAuthor());
        holder.content.setText(reviewdata.getContent());
    }


    @Override
    public int getItemCount() {
        return reviews.size();
    }



    class ReviewViewHolder extends RecyclerView.ViewHolder {

        TextView author,content;

        public ReviewViewHolder(View itemView) {
            super(itemView);
            author=itemView.findViewById(R.id.tv_author);
            content=itemView.findViewById(R.id.tv_content);
        }
    }
}
