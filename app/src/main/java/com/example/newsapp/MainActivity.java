package com.example.newsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.example.newsapp.Utility.UtilityKs;
import com.example.newsapp.adapter.NewsPagingAdapter;
import com.example.newsapp.databinding.ActivityPagingNewsBinding;
import com.example.newsapp.model.Articles;
import com.example.newsapp.model.NewsModel;
import com.example.newsapp.viewmodel.NewsPagedListViewModel;

public class MainActivity extends AppCompatActivity implements NewsPagingAdapter.OnPagingCardClick {

    private ActivityPagingNewsBinding binding;
    private NewsPagedListViewModel newsPagedListViewModel;
    private NewsPagingAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_paging_news);

        newsPagedListViewModel = new ViewModelProvider(this).get(NewsPagedListViewModel.class);
        UtilityKs.startShimmer(binding.shimmerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.pagingNewsShow.setLayoutManager(layoutManager);
        binding.pagingNewsShow.setItemAnimator(null);

        adapter = new NewsPagingAdapter(this,this);

        newsPagedListViewModel.pagedListLiveData.observe(this, new Observer<PagedList<Articles>>() {
            @Override
            public void onChanged(PagedList<Articles> articles) {
                adapter.submitList(articles);
            }
        });

        binding.pagingNewsShow.setAdapter(adapter);

        toggle();

    }
    private void toggle() {
        newsPagedListViewModel.onItemAtEndLoaded.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                UtilityKs.stopShimmer(binding.shimmerView);
                binding.pagingNewsShow.setVisibility(View.VISIBLE);
                binding.noNews.setVisibility(View.GONE);
            }
        });

        newsPagedListViewModel.onZeroItemsLoaded.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                UtilityKs.stopShimmer(binding.shimmerView);
                binding.pagingNewsShow.setVisibility(View.GONE);
                binding.noNews.setVisibility(View.VISIBLE);
            }
        });

        newsPagedListViewModel.onItemAtFrontLoaded.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                UtilityKs.stopShimmer(binding.shimmerView);
                binding.pagingNewsShow.setVisibility(View.VISIBLE);
                binding.noNews.setVisibility(View.GONE);
            }
        });
    }



    @Override
    protected void onPause() {
        UtilityKs.stopShimmer(binding.shimmerView);
        super.onPause();

    }

    @Override
    public void newsClick(String imageUrl, String title, String description, String author, String time) {
        Intent i = new Intent(this, PagingNews.class);
        i.putExtra("Image", imageUrl);
        i.putExtra("Title", title);
        i.putExtra("Description", description);
        i.putExtra("Author", author);
        i.putExtra("Time",time);
        startActivity(i);
    }
}