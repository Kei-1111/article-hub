package com.example.articlehub.data.remote.zenn_api

import com.example.articlehub.model.ZennResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ZennApi {

    @GET("articles?order=latest")
    suspend fun getArticles(
        @Query ("page") page: Int,
    ): ZennResponse
}