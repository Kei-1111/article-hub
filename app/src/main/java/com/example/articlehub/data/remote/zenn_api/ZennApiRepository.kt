package com.example.articlehub.data.remote.zenn_api

import androidx.paging.PagingData
import com.example.articlehub.model.ZennArticle
import kotlinx.coroutines.flow.Flow

interface ZennApiRepository {

    fun getDataStream(): Flow<PagingData<ZennArticle>>

}