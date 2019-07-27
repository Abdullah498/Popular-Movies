package com.example.popularmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {

    private static final String TAG = MoviesAdapter.class.getSimpleName();



    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

    final private ListItemClickListener mOnClickListener;


    private int mNumberItems;
    private Context mcontext;


    public void setmNumberItems(int mNumberItems) {
        this.mNumberItems = mNumberItems;
    }

    public MoviesAdapter(Context context,int numberOfItems, ListItemClickListener listener) {
        mcontext=context;
        mOnClickListener = listener;
        mNumberItems = numberOfItems;

    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.movie_poster;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        MovieViewHolder viewHolder = new MovieViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        Log.d(TAG, "#" + position);
        holder.bind(position);
    }


    @Override
    public int getItemCount() {
        return mNumberItems;
    }



    class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView poster;

        public MovieViewHolder(View itemView) {
            super(itemView);
            poster =itemView.findViewById(R.id.iv_movie_poster);

            itemView.setOnClickListener(this);

        }
        void bind(int listIndex) {
          Picasso.with(mcontext).load(MainActivity.movies.get(listIndex).getMoviePosterImage()).into(poster);
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(clickedPosition);
        }
    }
}
