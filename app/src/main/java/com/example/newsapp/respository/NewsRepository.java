package com.example.newsapp.respository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.newsapp.dao.NewsDao;
import com.example.newsapp.database.NewsDatabase;
import com.example.newsapp.model.Articles;

import java.util.ArrayList;
import java.util.List;

public class NewsRepository {

    private NewsDatabase newsDatabase;
    private LiveData<List<Articles>> getAllNews;

    public NewsRepository(Application application) {
       newsDatabase = NewsDatabase.getInstance(application);
       getAllNews = newsDatabase.newsDao().getAllNews();
    }

    public void insert(List<Articles> newsList) {
        new InsertAsyncTask(newsDatabase).execute(newsList);
    }

    public LiveData<List<Articles>> getAllNewsArticles() {
        return getAllNews;
    }

    static class InsertAsyncTask extends AsyncTask<List<Articles>,Void,Void> {

        private NewsDao newsDao;

        public InsertAsyncTask(NewsDatabase newsDatabase) {
            newsDao = newsDatabase.newsDao();
        }
        @Override
        protected Void doInBackground(List<Articles>... arrayLists) {
            newsDao.insert(arrayLists[0]);
            return null;
        }
    }
}
