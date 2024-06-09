package com.example.articlehub.ui.screens.favorite

import com.example.articlehub.model.Article

data class FavoriteUiState(
    val favoriteArticleList: List<Article> = emptyList(),
    val isExpanded: Boolean = false
)