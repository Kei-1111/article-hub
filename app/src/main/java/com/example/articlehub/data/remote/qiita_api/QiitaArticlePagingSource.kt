package com.example.articlehub.data.remote.qiita_api

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.articlehub.data.local.data_store.DataStoreRepository
import com.example.articlehub.model.QiitaArticle
import javax.inject.Inject

class QiitaArticlePagingSource @Inject constructor(
    private val qiitaApi: QiitaApi,
    private val dataStoreRepository: DataStoreRepository
) : PagingSource<Int, QiitaArticle>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, QiitaArticle> {
        val page = params.key ?: 1
        val userQuery = dataStoreRepository.getUserQuery()
        return try {
            val items = qiitaApi.getArticlesByTag(
                page = page,
                perPage = params.loadSize,
                query = userQuery.qiitaQuery
            )
            LoadResult.Page(
                data = items,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (items.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, QiitaArticle>): Int? {
        return state.anchorPosition
    }
}