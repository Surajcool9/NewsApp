package com.example.newsapp.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.newsapp.Network.ApiInterface;
import com.example.newsapp.Network.RetrofitInstance;
import com.example.newsapp.Utility.StringKs;
import com.example.newsapp.model.Articles;
import com.example.newsapp.model.NewsModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsViewModel extends AndroidViewModel {

    private MutableLiveData<NewsModel> newsModel = new MutableLiveData<>();

    public NewsViewModel(@NonNull Application application) {
        super(application);
    }
    public MutableLiveData<NewsModel> getNewsList() {
        return newsModel;
    }

    public void makeApiCall() {

        ApiInterface apiInterface = RetrofitInstance.getRetrofitClient().create(ApiInterface.class);
        Call<NewsModel> call = apiInterface.getsNewsList(StringKs.INDIA_CODE,StringKs.API_KEY);
        call.enqueue(new Callback<NewsModel>() {
            @Override
            public void onResponse(Call<NewsModel> call, Response<NewsModel> response) {
                newsModel.postValue(response.body());
            }

            @Override
            public void onFailure(Call<NewsModel> call, Throwable t) {
                newsModel.postValue(null);
                Log.e("Error Msg", t.getMessage());
            }
        });
    }
}
