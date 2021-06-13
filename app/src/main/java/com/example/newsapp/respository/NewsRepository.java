package com.example.newsapp.respository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.newsapp.dao.NewsDao;
import com.example.newsapp.database.NewsDatabase;
import com.example.newsapp.model.Articles;

import java.util.ArrayList;

public class NewsRepository {

    private NewsDatabase newsDatabase;
    private LiveData<ArrayList<Articles>> getAllNews;

    public NewsRepository(Application application) {
       newsDatabase = NewsDatabase.getInstance(application);
       getAllNews = newsDatabase.newsDao().getAllNews();
    }

    public void insert(ArrayList<Articles> newsList) {
        new InsertAsyncTask(newsDatabase).execute(newsList);
    }

    public LiveData<ArrayList<Articles>> getAllNewsArticles() {
        return getAllNews;
    }

    static class InsertAsyncTask extends AsyncTask<ArrayList<Articles>,Void,Void> {

        private NewsDao newsDao;

        public InsertAsyncTask(NewsDatabase newsDatabase) {
            newsDao = newsDatabase.newsDao();
        }
        @Override
        protected Void doInBackground(ArrayList<Articles>... arrayLists) {
            newsDao.insert(arrayLists[0]);
            return null;
        }
    }
}
