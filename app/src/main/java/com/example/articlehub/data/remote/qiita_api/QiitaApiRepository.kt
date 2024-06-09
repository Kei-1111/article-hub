package com.example.articlehub.data.remote.qiita_api

import androidx.paging.PagingData
import com.example.articlehub.model.QiitaArticle
import kotlinx.coroutines.flow.Flow

interface QiitaApiRepository {

    fun getDataStream(): Flow<PagingData<QiitaArticle>>

}