package com.example.articlehub.data.remote.note_api

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.articlehub.model.NoteArticle
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NoteApiRepositoryImpl @Inject constructor(
    private val noteArticlePaging: NoteArticlePaging
): NoteApiRepository {

    override fun getDataStream(): Flow<PagingData<NoteArticle>> {
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            pagingSourceFactory = { noteArticlePaging }
        ).flow
    }
}