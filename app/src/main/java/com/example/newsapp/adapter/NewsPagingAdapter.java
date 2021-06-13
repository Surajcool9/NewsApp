package com.example.newsapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.newsapp.R;
import com.example.newsapp.databinding.NewsListviewBinding;
import com.example.newsapp.model.NewsModel;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class NewsPagingAdapter extends PagedListAdapter<NewsModel.Article, NewsPagingAdapter.NewsPagingViewHolder > {
    private Context context;
    private OnPagingCardClick onPagingCardClick;
    private SimpleDateFormat dateFormat;

    public NewsPagingAdapter(Context context, OnPagingCardClick onPagingCardClick) {
        super(POST_COMPARATOR);
        this.context = context;
        this.onPagingCardClick = onPagingCardClick;
    }

    private static final DiffUtil.ItemCallback<NewsModel.Article> POST_COMPARATOR = new DiffUtil.ItemCallback<NewsModel.Article>() {
       @Override
       public boolean areItemsTheSame(@NonNull NewsModel.Article oldItem, @NonNull NewsModel.Article newItem) {
           return oldItem.getTitle() == newItem.getTitle();
       }

       @SuppressLint("DiffUtilEquals")
       @Override
       public boolean areContentsTheSame(@NonNull NewsModel.Article oldItem, @NonNull NewsModel.Article newItem) {
           return oldItem.equals(newItem);
       }
   };
    @NonNull
    @Override
    public NewsPagingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NewsPagingViewHolder(DataBindingUtil.inflate(LayoutInflater.from(context),
                R.layout.news_listview,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull NewsPagingViewHolder holder, int position) {
        holder.bind(Objects.requireNonNull(getItem(position)));
    }

    public class NewsPagingViewHolder extends RecyclerView.ViewHolder{

        private NewsListviewBinding binding;
        public NewsPagingViewHolder(@NonNull NewsListviewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        private void bind(NewsModel.Article article) {
            if(!TextUtils.isEmpty(article.getTitle())) {
                binding.headline.setText(article.getTitle());
            } else {
                binding.headline.setVisibility(View.GONE);
            }

            if(!TextUtils.isEmpty(article.getAuthor())){
                binding.authorName.setText(article.getAuthor());
            } else {
                binding.authorName.setVisibility(View.GONE);
            }

            if(!TextUtils.isEmpty(article.getPublishedAt())) {
               binding.newsDate.setText(getDate(article.getPublishedAt()));
            } else {
                binding.newsDate.setVisibility(View.GONE);
            }


            if(!TextUtils.isEmpty(article.getUrlToImage())) {
                Glide.with(context)
                        .load(article.getUrlToImage())
                        .thumbnail(0.5f)
                        .centerCrop()
                        .placeholder(R.drawable.glide_placeholder)
                        .into(binding.newsImage);
            } else {
                binding.newsImage.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_placeholder_violet));
            }


            binding.newsCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onPagingCardClick.newsClick(article.getUrlToImage(),
                            article.getTitle(),
                            article.getDescription(),
                            article.getAuthor(),
                            getDate(article.getPublishedAt()));
                }
            });
        }
    }

    public interface OnPagingCardClick {
        void newsClick(String imageUrl, String title, String description, String author, String time);
    }

    public String getDate(String apiDate) {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
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
