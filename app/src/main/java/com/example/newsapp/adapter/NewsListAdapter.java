package com.example.newsapp.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.newsapp.R;
import com.example.newsapp.databinding.NewsListviewBinding;
import com.example.newsapp.model.Articles;
import com.example.newsapp.model.NewsModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.NewsListViewHolder> {

    private Context context;
    private ArrayList<Articles> articles;
    private OnPagingCardClick onCardClick;

    public NewsListAdapter(Context context, ArrayList<Articles> articles, OnPagingCardClick onCardClick) {
        this.context = context;
        this.articles = articles;
        this.onCardClick = onCardClick;
    }

    @NonNull
    @Override
    public NewsListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NewsListViewHolder(DataBindingUtil.inflate(LayoutInflater.from(context),
                R.layout.news_listview, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NewsListViewHolder holder, int position) {

        if (!TextUtils.isEmpty(articles.get(position).getTitle())) {
            holder.binding.headline.setText(articles.get(position).getTitle());
        } else {
            holder.binding.headline.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(articles.get(position).getAuthor())) {
            holder.binding.authorName.setText(articles.get(position).getAuthor());
        } else {
            holder.binding.authorName.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(articles.get(position).getPublishedAt())) {
            holder.binding.newsDate.setText(getDate(articles.get(position).getPublishedAt()));
        } else {
            holder.binding.newsDate.setVisibility(View.GONE);
        }


        if (!TextUtils.isEmpty(articles.get(position).getUrlToImage())) {
            Glide.with(context)
                    .load(articles.get(position).getUrlToImage())
                    .thumbnail(0.5f)
                    .centerCrop()
                    .placeholder(R.drawable.glide_placeholder)
                    .into(holder.binding.newsImage);
        } else {
            holder.binding.newsImage.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_placeholder_violet));
        }

    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public class NewsListViewHolder extends RecyclerView.ViewHolder {

        private NewsListviewBinding binding;

        public NewsListViewHolder(@NonNull NewsListviewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.newsCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onCardClick.newsClick(articles.get(getBindingAdapterPosition()).getUrlToImage(),
                            articles.get(getBindingAdapterPosition()).getTitle(),
                            articles.get(getBindingAdapterPosition()).getDescription(),
                            articles.get(getBindingAdapterPosition()).getAuthor(),
                            getDate(articles.get(getBindingAdapterPosition()).getPublishedAt()));
                }
            });
        }
    }

    public void setNewsList(ArrayList<Articles> articles) {
        this.articles = articles;
        notifyDataSetChanged();
    }

    public interface OnPagingCardClick {
        void newsClick(String imageUrl, String title, String description, String author, String time);
    }

    public String getDate(String apiDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        SimpleDateFormat dateNew = new SimpleDateFormat("dd MMM yyyy");
        try {
            Date date = dateFormat.parse(apiDate);
            return dateNew.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
