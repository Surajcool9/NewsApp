package com.example.newsapp.Network;

import com.example.newsapp.Utility.StringKs;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {

    private static Retrofit retrofit;

    public static Retrofit getRetrofitClient() {

        if(retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(StringKs.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
