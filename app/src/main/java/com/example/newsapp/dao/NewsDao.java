package com.example.newsapp.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.newsapp.model.Articles;
import java.util.ArrayList;

@Dao
public interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ArrayList<Articles> articles);

    @Query("Select * from news_table")
    LiveData<ArrayList<Articles>> getAllNews();

    @Query("Delete from news_table")
    void deleteAll();
}
