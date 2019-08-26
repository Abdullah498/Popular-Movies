package com.example.popularmovies;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class FavouritesViewModel extends AndroidViewModel {
    private final LiveData<List<MovieData>> moviesListLiveData;

    private AppDatabase appDatabase;

    public FavouritesViewModel(@NonNull Application application) {
        super(application);

        appDatabase = AppDatabase.getInstance(this.getApplication());

        moviesListLiveData = appDatabase.movieDao().getAllMovies();
    }

    public LiveData<List<MovieData>> getMoviesListLiveData() {
        return moviesListLiveData;
    }

    public void deleteMovie(MovieData movie){
        new DeleteItemAsyncTask(appDatabase).execute(movie);
    }

    private static class DeleteItemAsyncTask extends AsyncTask<MovieData, Void, Void> {

        private AppDatabase appDatabase;

        public DeleteItemAsyncTask(AppDatabase database) {
            this.appDatabase = database;
        }

        @Override
        protected Void doInBackground(MovieData... voids) {
            appDatabase.movieDao().deleteMovie(voids[0].getId());
            return null;
        }
    }

}
