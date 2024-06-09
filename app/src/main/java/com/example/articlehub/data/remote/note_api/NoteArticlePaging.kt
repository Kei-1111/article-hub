package com.example.articlehub.data.remote.note_api

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.articlehub.data.local.data_store.DataStoreRepository
import com.example.articlehub.model.NoteArticle
import javax.inject.Inject

class NoteArticlePaging @Inject constructor(
    private val noteApi: NoteApi,
    private val dataStoreRepository: DataStoreRepository
) : PagingSource<Int, NoteArticle>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NoteArticle> {
        val page = params.key ?: 0
        val userQuery = dataStoreRepository.getUserQuery()
        return try {
            val items = noteApi.getArticles(
                query = userQuery.noteQuery,
                size = params.loadSize,
                start = page * params.loadSize
            )
            LoadResult.Page(
                data = items.data.notes.contents,
                prevKey = if (page == 0) null else page - 1,
                nextKey = if (items.data.notes.contents.isEmpty()) null else page + 1,
            )
        } catch (e: Exception) {
            Log.e("NOTE", "load: $e")
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, NoteArticle>): Int? {
        return state.anchorPosition
    }

}