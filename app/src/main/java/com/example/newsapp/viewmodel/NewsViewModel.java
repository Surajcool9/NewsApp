package com.example.newsapp.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.newsapp.Network.ApiInterface;
import com.example.newsapp.Network.RetrofitInstance;
import com.example.newsapp.Utility.StringKs;
import com.example.newsapp.model.Articles;
import com.example.newsapp.model.NewsModel;
import com.example.newsapp.respository.NewsRepository;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsViewModel extends AndroidViewModel {

    private NewsRepository newsRepository;
    private LiveData<List<Articles>> getAllNews;

    public NewsViewModel(@NonNull Application application) {
        super(application);
        newsRepository = new NewsRepository(application);
        getAllNews = newsRepository.getAllNewsArticles();
    }

    public LiveData<List<Articles>> getNewsList() {
        return getAllNews;
    }

    public void makeApiCall() {

        ApiInterface apiInterface = RetrofitInstance.getRetrofitClient().create(ApiInterface.class);
        Call<NewsModel> call = apiInterface.getsNewsList(StringKs.INDIA_CODE,StringKs.API_KEY);
        call.enqueue(new Callback<NewsModel>() {
            @Override
            public void onResponse(Call<NewsModel> call, Response<NewsModel> response) {
                if(response.isSuccessful()) {
                    newsRepository.insert(response.body().getArticles());
                }
            }

            @Override
            public void onFailure(Call<NewsModel> call, Throwable t) {
                Log.e("Error Msg", t.getMessage());
            }
        });
    }
}
