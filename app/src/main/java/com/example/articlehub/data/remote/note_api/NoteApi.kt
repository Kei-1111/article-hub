package com.example.articlehub.data.remote.note_api

import com.example.articlehub.model.NoteResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NoteApi {

    @GET("searches")
    suspend fun getArticles(
        @Query("context") context: String = "note",
        @Query("q") query: String,
        @Query("size") size: Int,
        @Query("start") start: Int,
        @Query("sort") sort: String = "new"
    ): NoteResponse
}