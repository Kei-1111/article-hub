package com.example.articlehub.ui.screens.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.articlehub.BuildConfig
import com.example.articlehub.data.local.room.RoomArticleRepository
import com.example.articlehub.data.remote.note_api.NoteApiRepository
import com.example.articlehub.data.remote.qiita_api.QiitaApiRepository
import com.example.articlehub.data.remote.zenn_api.ZennApiRepository
import com.example.articlehub.model.Article
import com.example.articlehub.model.ArticleSource
import com.example.articlehub.until.toArticle
import com.google.ai.client.generativeai.GenerativeModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Url
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val qiitaApiRepository: QiitaApiRepository,
    private val zennApiRepository: ZennApiRepository,
    private val noteApiRepository: NoteApiRepository,
    private val roomArticleRepository: RoomArticleRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    lateinit var qiitaArticleList: Flow<PagingData<Article>>
    lateinit var zennArticleList: Flow<PagingData<Article>>
    lateinit var noteArticleList: Flow<PagingData<Article>>

    init {
        viewModelScope.launch {
            refreshQiitaArticleList()
            refreshZennArticleList()
            refreshNoteArticleList()
        }
    }

    fun refreshQiitaArticleList() {
        qiitaArticleList = qiitaApiRepository.getDataStream()
            .map { pagingData -> pagingData.map { it.toArticle() } }
            .cachedIn(viewModelScope)
    }

    fun refreshZennArticleList() {
        zennArticleList = zennApiRepository.getDataStream()
            .map { pagingData -> pagingData.map { it.toArticle() } }
            .cachedIn(viewModelScope)
    }

    fun refreshNoteArticleList() {
        noteArticleList = noteApiRepository.getDataStream()
            .map { pagingData -> pagingData.map { it.toArticle() } }
            .cachedIn(viewModelScope)
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

    fun selectQiitaTab() {
        _uiState.update {
            it.copy(selectedTab = ArticleSource.QIITA)
        }
    }

    fun selectZennTab() {
        _uiState.update {
            it.copy(selectedTab = ArticleSource.ZENN)
        }
    }

    fun selectNoteTab() {
        _uiState.update {
            it.copy(selectedTab = ArticleSource.NOTE)
        }
    }

    fun expandDropdownMenu() {
        _uiState.update {
            it.copy(isExpanded = true)
        }
    }

    fun closeDropdownMenu() {
        _uiState.update {
            it.copy(isExpanded = false)
        }
    }
}
