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

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TrailersAdapter  extends RecyclerView.Adapter<TrailersAdapter.TrailerViewHolder> {

    private static final String TAG = TrailersAdapter.class.getSimpleName();
    ArrayList<MovieData> trailers;
    private Context mcontext;


    public TrailersAdapter(Context context, ArrayList<MovieData> trailers) {
        mcontext=context;
        this.trailers=trailers;
    }

    @NonNull
    @Override
    public TrailersAdapter.TrailerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(mcontext).inflate(R.layout.trailer_item,viewGroup,false);
        return new TrailersAdapter.TrailerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TrailersAdapter.TrailerViewHolder holder, int position) {
        final MovieData trailerData=trailers.get(position);
        holder.trailerName.setText(trailerData.getName());

        holder.trailerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://www.youtube.com/watch?v="+trailerData.getKey()));
                mcontext.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return trailers.size();
    }



    class TrailerViewHolder extends RecyclerView.ViewHolder {

        TextView trailerLink,trailerName;

        public TrailerViewHolder(View itemView) {
            super(itemView);
            trailerLink=itemView.findViewById(R.id.trailer_play);
            trailerName=itemView.findViewById(R.id.trailer_title);
        }
    }
}

