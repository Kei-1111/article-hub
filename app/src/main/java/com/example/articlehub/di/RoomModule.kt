package com.example.articlehub.di

import android.content.Context
import androidx.room.Room
import com.example.articlehub.data.local.room.ArticleDao
import com.example.articlehub.data.local.room.ArticleDatabase
import com.example.articlehub.data.local.room.RoomArticleRepository
import com.example.articlehub.data.local.room.RoomArticleRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun provideArticleDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(context, ArticleDatabase::class.java, "article_database")
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    @Singleton
    fun provideArticleDao(database: ArticleDatabase) = database.articleDao()

    @Provides
    @Singleton
    fun provideRoomArticleRepository(articleDao: ArticleDao): RoomArticleRepository = RoomArticleRepositoryImpl(articleDao)

}