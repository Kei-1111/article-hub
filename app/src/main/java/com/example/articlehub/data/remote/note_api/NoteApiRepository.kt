package com.example.articlehub.data.remote.note_api

import androidx.paging.PagingData
import com.example.articlehub.model.NoteArticle
import kotlinx.coroutines.flow.Flow

interface NoteApiRepository {

    fun getDataStream(): Flow<PagingData<NoteArticle>>
}