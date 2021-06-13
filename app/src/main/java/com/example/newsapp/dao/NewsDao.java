package com.example.newsapp.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.newsapp.model.Articles;
import java.util.List;

@Dao
public interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<Articles> articles);

    @Query("Select * from news_table")
    LiveData<List<Articles>> getAllNews();

    @Query("Delete from news_table")
    void deleteAll();
}
