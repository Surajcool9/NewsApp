package com.example.newsapp.Network;

import com.example.newsapp.model.NewsModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("/v2/top-headlines")
    Call<NewsModel> getsNewsList(@Query("country") String country, @Query("apiKey") String apiKey);

    @GET("/v2/top-headlines")
    Call<NewsModel> getsNewsListPaging(@Query("country") String country, @Query("apiKey") String apiKey, @Query("page") int page);
}
