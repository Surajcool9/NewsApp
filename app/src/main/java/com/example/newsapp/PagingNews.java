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
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.newsapp.Utility.StringKs;
import com.example.newsapp.Utility.UtilityKs;
import com.example.newsapp.adapter.NewsPagingAdapter;
import com.example.newsapp.databinding.ActivityMainBinding;
import com.example.newsapp.databinding.ActivityPagingNewsBinding;
import com.example.newsapp.model.NewsModel;
import com.example.newsapp.viewmodel.NewsPagedListViewModel;

public class PagingNews extends AppCompatActivity {

    private ActivityMainBinding binding;
    private String title, desc, author, time, imageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        Intent i = getIntent();
        title = i.getStringExtra("Title");
        desc = i.getStringExtra("Description");
        author = i.getStringExtra("Author");
        time = i.getStringExtra("Time");
        imageUrl = i.getStringExtra("Image");

        Glide.with(this)
                .load(imageUrl)
                .centerCrop()
                .placeholder(R.drawable.glide_placeholder)
                .into(binding.fullImage);
        binding.fullHeading.setText(title);
        binding.author.setText(author);
        binding.date.setText(time);
        binding.fullDesc.setText(desc);

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PagingNews.super.onBackPressed();
            }
        });

    }

}