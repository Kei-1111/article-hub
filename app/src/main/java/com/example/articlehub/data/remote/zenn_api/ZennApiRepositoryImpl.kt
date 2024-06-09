package com.example.articlehub.data.remote.zenn_api

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.articlehub.model.ZennArticle
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ZennApiRepositoryImpl @Inject constructor (
    private val zennArticlePagingSource: ZennArticlePagingSource
) : ZennApiRepository {

    override fun getDataStream() : Flow<PagingData<ZennArticle>> {
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            pagingSourceFactory = { zennArticlePagingSource }
        ).flow
    }
}