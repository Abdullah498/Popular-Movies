package com.example.popularmovies;

import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;

@Database(entities = {MovieData.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static final Object LOCK = new Object();
    private static final String DB_NAME = "moviesDB";
    private static AppDatabase sInstance;

    public static AppDatabase getInstance(Context context){
        if (sInstance==null){
            synchronized (LOCK){
                sInstance = Room.databaseBuilder(context, AppDatabase.class, DB_NAME)
                        .build();
            }
        }
        return sInstance;
    }

    public abstract MovieDao movieDao();
}
