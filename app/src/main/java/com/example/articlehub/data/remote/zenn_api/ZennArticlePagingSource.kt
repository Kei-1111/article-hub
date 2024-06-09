package com.example.articlehub.data.remote.zenn_api

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.articlehub.data.local.data_store.DataStoreRepository
import com.example.articlehub.model.ZennArticle
import javax.inject.Inject
import javax.inject.Named

class ZennArticlePagingSource @Inject constructor(
   private val zennApi: ZennApi,
   private val dataStoreRepository: DataStoreRepository
) : PagingSource<Int, ZennArticle>() {

   override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ZennArticle> {
      val page = params.key ?: 1
      val userQuery = dataStoreRepository.getUserQuery()
      return try {
         val items = zennApi.getArticles(page = page)
         LoadResult.Page(
            data = items.articles.filter { it.title.contains(userQuery.zennQuery) },
            prevKey = if(page == 1) null else page -1,
            nextKey = if(items.articles.isEmpty()) null else page + 1
         )
      } catch (e: Exception) {
         LoadResult.Error(e)
      }
   }

   override fun getRefreshKey(state: PagingState<Int, ZennArticle>): Int? {
        return state.anchorPosition
   }
}