package com.example.newsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import com.example.newsapp.Utility.UtilityKs;
import com.example.newsapp.adapter.NewsListAdapter;
import com.example.newsapp.databinding.ActivityPagingNewsBinding;
import com.example.newsapp.model.Articles;
import com.example.newsapp.model.NewsModel;
import com.example.newsapp.viewmodel.NewsViewModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NewsListAdapter.OnPagingCardClick {

    private ActivityPagingNewsBinding binding;
    private NewsViewModel newsViewModel;
    private NewsListAdapter adapter;
    private ArrayList<Articles> newsArticles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_paging_news);

        newsViewModel = new ViewModelProvider(this).get(NewsViewModel.class);
        UtilityKs.startShimmer(binding.shimmerView);
      newsViewModel.getNewsList().observe(this, new Observer<NewsModel>() {
          @Override
          public void onChanged(NewsModel newsModel) {
              newsArticles = newsModel.getArticles();
              UtilityKs.stopShimmer(binding.shimmerView);
              setAdapterNews(newsArticles);
          }
      });
        newsViewModel.makeApiCall();

    }

    private void setAdapterNews(ArrayList<Articles> articles) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL,false);
        binding.newsShow.setLayoutManager(layoutManager);
        adapter = new NewsListAdapter(this, articles,this);
        binding.newsShow.setAdapter(adapter);
        adapter.setNewsList(articles);
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