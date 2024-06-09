package com.example.articlehub.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.articlehub.model.Article
import com.example.articlehub.until.Converters

@Database(entities = [Article::class], version = 5, exportSchema = false)
@TypeConverters(Converters::class)
abstract class ArticleDatabase : RoomDatabase() {
    abstract fun articleDao(): ArticleDao
}