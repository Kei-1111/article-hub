package com.example.articlehub.data.local.room

import com.example.articlehub.model.Article
import kotlinx.coroutines.flow.Flow

interface RoomArticleRepository {

    suspend fun insert(article: Article)

    suspend fun delete(article: Article)

    fun getAllArticles(): Flow<List<Article>>
}