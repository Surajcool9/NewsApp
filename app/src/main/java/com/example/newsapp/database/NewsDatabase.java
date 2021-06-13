package com.example.newsapp.database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.newsapp.dao.NewsDao;
import com.example.newsapp.model.Articles;

@Database(entities = {Articles.class}, version = 1)
public abstract class NewsDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "ArticleDatabase";

    public abstract NewsDao newsDao();

    private static volatile NewsDatabase INSTANCE;

    public static NewsDatabase getInstance(Context context) {
        if(INSTANCE == null) {
            synchronized (NewsDatabase.class) {
                if(INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context, NewsDatabase.class,
                            DATABASE_NAME)
                            .addCallback(callback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    static Callback callback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new ViewAsyncTask(INSTANCE);
        }
    };

    static class ViewAsyncTask extends AsyncTask<Void, Void, Void>{

        private NewsDao newsDao;

        public ViewAsyncTask(NewsDatabase newsDatabase) {
            newsDao = newsDatabase.newsDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            newsDao.deleteAll();
            return null;
        }
    }
}
