package com.example.newsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.bumptech.glide.Glide;
import com.example.newsapp.databinding.ActivityMainBinding;

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