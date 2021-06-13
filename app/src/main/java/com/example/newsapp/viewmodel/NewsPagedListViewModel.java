package com.example.newsapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import androidx.paging.PagedListConfigKt;

import com.example.newsapp.Network.ApiInterface;
import com.example.newsapp.Network.RetrofitInstance;
import com.example.newsapp.NewsDataSource;
import com.example.newsapp.NewsDataSourceFactory;
import com.example.newsapp.model.Articles;
import com.example.newsapp.model.NewsModel;

import org.jetbrains.annotations.NotNull;

public class NewsPagedListViewModel extends AndroidViewModel {

    LiveData<NewsDataSource> newsDataSourceLiveData;
    public LiveData<PagedList<Articles>> pagedListLiveData;
    public MutableLiveData<Boolean> onZeroItemsLoaded = new MutableLiveData<>();
    public MutableLiveData<Boolean> onItemAtEndLoaded = new MutableLiveData<>();
    public MutableLiveData<Boolean> onItemAtFrontLoaded = new MutableLiveData<>();

    public NewsPagedListViewModel(@NonNull Application application) {
        super(application);

        ApiInterface apiInterface = RetrofitInstance.getRetrofitClient().create(ApiInterface.class);
        NewsDataSourceFactory factory = new NewsDataSourceFactory(apiInterface,application);
        newsDataSourceLiveData = factory.getNewsList();

        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(true)
                .setInitialLoadSizeHint(10)
                .setPageSize(20)
                .build();

        pagedListLiveData = new LivePagedListBuilder<Integer,Articles>(factory,config).setBoundaryCallback(new PagedList.BoundaryCallback<Articles>() {
            @Override
            public void onItemAtEndLoaded(@NotNull Articles itemAtEnd) {
                super.onItemAtEndLoaded(itemAtEnd);
                onItemAtEndLoaded.setValue(true);
            }

            @Override
            public void onItemAtFrontLoaded(@NotNull Articles itemAtFront) {
                super.onItemAtFrontLoaded(itemAtFront);
                onItemAtFrontLoaded.setValue(true);
            }

            @Override
            public void onZeroItemsLoaded() {
                super.onZeroItemsLoaded();
                onZeroItemsLoaded.setValue(true);
            }
        }).build();
    }


}
