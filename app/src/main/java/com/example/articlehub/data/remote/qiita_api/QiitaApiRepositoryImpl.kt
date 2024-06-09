package com.example.articlehub.data.remote.qiita_api

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.articlehub.model.QiitaArticle
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class QiitaApiRepositoryImpl @Inject constructor(
    private val qiitaArticlePagingSource: QiitaArticlePagingSource,
): QiitaApiRepository {

    override fun getDataStream(): Flow<PagingData<QiitaArticle>> {
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            pagingSourceFactory = { qiitaArticlePagingSource }
        ).flow
    }
}