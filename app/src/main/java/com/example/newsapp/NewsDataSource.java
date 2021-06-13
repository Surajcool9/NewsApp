package com.example.newsapp;


import android.app.Application;

import androidx.arch.core.util.Function;
import androidx.paging.PageKeyedDataSource;

import com.example.newsapp.Network.ApiInterface;
import com.example.newsapp.Network.RetrofitInstance;
import com.example.newsapp.Utility.StringKs;
import com.example.newsapp.model.NewsModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsDataSource extends PageKeyedDataSource<Integer, NewsModel.Article> {

    private ApiInterface apiInterface;
    private Application application;
    private ArrayList<NewsModel.Article> newsArticles;

    public NewsDataSource(ApiInterface apiInterface, Application application) {
        this.apiInterface = apiInterface;
        this.application = application;
    }

    @Override
    public void loadAfter(@NotNull LoadParams<Integer> loadParams, @NotNull final LoadCallback<Integer, NewsModel.Article> loadCallback) {

        ApiInterface apiInterface = RetrofitInstance.getRetrofitClient().create(ApiInterface.class);
        Call<NewsModel> call = apiInterface.getsNewsListPaging(StringKs.INDIA_CODE, StringKs.API_KEY, loadParams.key);

        call.enqueue(new Callback<NewsModel>() {
            @Override
            public void onResponse(Call<NewsModel> call, Response<NewsModel> response) {

                if (response.body().getStatus().equals(StringKs.STATUS_API)) {
                    NewsModel newsModel = response.body();

                    if (newsModel.getArticles() != null) {
                        newsArticles = newsModel.getArticles();

                        loadCallback.onResult(newsArticles, loadParams.key + 1);
                    }
                }
            }

            @Override
            public void onFailure(Call<NewsModel> call, Throwable t) {

            }
        });
    }

    @Override
    public void loadBefore(@NotNull LoadParams<Integer> loadParams, @NotNull LoadCallback<Integer, NewsModel.Article> loadCallback) {

    }

    @Override
    public void loadInitial(@NotNull LoadInitialParams<Integer> loadInitialParams, @NotNull LoadInitialCallback<Integer, NewsModel.Article> loadInitialCallback) {

        ApiInterface apiInterface = RetrofitInstance.getRetrofitClient().create(ApiInterface.class);
        Call<NewsModel> call = apiInterface.getsNewsListPaging(StringKs.INDIA_CODE, StringKs.API_KEY, 1);

        call.enqueue(new Callback<NewsModel>() {
            @Override
            public void onResponse(Call<NewsModel> call, Response<NewsModel> response) {

                if (response.body().getStatus().equals(StringKs.STATUS_API)) {
                    NewsModel newsModel = response.body();
                    if (newsModel.getArticles() != null) {
                        newsArticles = newsModel.getArticles();

                        loadInitialCallback.onResult(newsArticles, null, 2);
                    }
                }
            }

            @Override
            public void onFailure(Call<NewsModel> call, Throwable t) {

            }
        });
    }
}
