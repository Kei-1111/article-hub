package com.example.articlehub.data.remote.qiita_api

import com.example.articlehub.model.QiitaArticle
import retrofit2.http.GET
import retrofit2.http.Query

interface QiitaApi {

    @GET("items")
    suspend fun getArticlesByTag(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
        @Query("query") query: String
    ): List<QiitaArticle>
}