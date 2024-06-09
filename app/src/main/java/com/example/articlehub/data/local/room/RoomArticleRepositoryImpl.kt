package com.example.articlehub.data.local.room

import com.example.articlehub.model.Article
import javax.inject.Inject


class RoomArticleRepositoryImpl @Inject constructor(
    private val articleDao: ArticleDao
): RoomArticleRepository {

    override suspend fun insert(article: Article) {
        articleDao.insert(article)
    }

    override suspend fun delete(article: Article) {
        articleDao.delete(article)
    }

    override fun getAllArticles() = articleDao.getAllArticles()
}