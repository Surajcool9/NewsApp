package com.example.newsapp;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import com.example.newsapp.Network.ApiInterface;
import com.example.newsapp.model.NewsModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class NewsDataSourceFactory extends DataSource.Factory<Integer, NewsModel.Article> {

    private ApiInterface apiInterface;
    private Application application;
    private NewsDataSource newsDataSource;
    private NewsModel newsModel;
    private ArrayList<NewsModel.Article> newsArticles;
    private MutableLiveData<NewsDataSource>  newsDataSourceMutableLiveData;

    public NewsDataSourceFactory(ApiInterface apiInterface, Application application) {
        this.apiInterface = apiInterface;
        this.application = application;
        newsDataSourceMutableLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<NewsDataSource> getNewsList() {
        return newsDataSourceMutableLiveData;
    }

    @NotNull
    @Override
    public DataSource<Integer, NewsModel.Article> create() {
        newsDataSource = new NewsDataSource(apiInterface,application);
        newsDataSourceMutableLiveData.postValue(newsDataSource);
        return newsDataSource;
    }

}
