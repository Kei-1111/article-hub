package com.example.articlehub.ui.screens.home

import androidx.datastore.preferences.protobuf.Api
import androidx.paging.PagingData
import com.example.articlehub.model.ApiResult
import com.example.articlehub.model.Article
import com.example.articlehub.model.ArticleSource
import kotlinx.coroutines.flow.Flow

data class HomeUiState(
    val selectedTab: ArticleSource = ArticleSource.QIITA,
    val isExpanded: Boolean = false,
)