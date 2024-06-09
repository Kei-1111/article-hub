package com.example.articlehub.ui.screens.article

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ArticleViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(ArticleUiState())

    fun updateArticle(url: String, title: String) {
        _uiState.update { currentState ->
            currentState.copy(
                articleUrl = url,
                pageTitle = title
            )
        }
    }

    fun shareArticleUrl(context: Context) {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, "${_uiState.value.pageTitle}\n${_uiState.value.articleUrl}")
        }
        context.startActivity(
            Intent.createChooser(intent, "記事を共有する")
        )
    }
}