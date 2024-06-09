package com.example.articlehub.ui.screens.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.articlehub.data.local.room.RoomArticleRepository
import com.example.articlehub.model.Article
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val roomArticleRepository: RoomArticleRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(FavoriteUiState())
    val uiState: StateFlow<FavoriteUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            roomArticleRepository.getAllArticles().collect { articleList ->
                _uiState.update { currentState ->
                    currentState.copy(favoriteArticleList = articleList)
                }
            }
        }
    }

    fun addArticleToFavorite(article: Article) {
        viewModelScope.launch {
            roomArticleRepository.insert(article)
        }
    }

    fun deleteArticleFromFavorite(article: Article) {
        viewModelScope.launch {
            roomArticleRepository.delete(article)
        }
    }
}