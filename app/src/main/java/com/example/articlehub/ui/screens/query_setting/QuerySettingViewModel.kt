package com.example.articlehub.ui.screens.query_setting

import QiitaTagList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.articlehub.data.local.data_store.DataStoreRepository
import com.example.articlehub.model.UserQuery
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuerySettingViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(QuerySettingUiState())
    val uiState: StateFlow<QuerySettingUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val userQuery = dataStoreRepository.getUserQuery()
            _uiState.update { currentState ->
                currentState.copy(
                    qiitaQueryList = userQuery.qiitaQuery
                        .split(" OR ")
                        .map { it.replace("tag:", "") }
                        .toMutableList(),
                    zennQuery = userQuery.zennQuery,
                    noteQuery = userQuery.noteQuery
                )
            }
        }
    }

    fun filterTags(query: String): MutableList<String> {
        _uiState.update { currentState ->
            currentState.copy(
                filteredQiitaTagList = if (query.isEmpty()) {
                    QiitaTagList.tagList
                } else {
                    QiitaTagList.tagList
                        .filter { it.contains(query, ignoreCase = true) }
                        .toMutableList()
                }
            )
        }
        return _uiState.value.filteredQiitaTagList
    }

    fun updateQiitaQueryUserInput(input: String) {
        _uiState.update { currentState ->
            currentState.copy(
                qiitaQueryUserInput = input
            )
        }
    }

    fun updateZennQueryUserInput(input: String) {
        _uiState.update { currentState ->
            currentState.copy(
                zennQueryUserInput = input
            )
        }
    }

    fun updateNoteQueryUserInput(input: String) {
        _uiState.update { currentState ->
            currentState.copy(
                noteQueryUserInput = input
            )
        }
    }

    fun removeQiitaQuery(query: String) {
        _uiState.update { currentState ->
            currentState.copy(
                qiitaQueryList = currentState.qiitaQueryList.toMutableList().apply { remove(query) }
            )
        }
    }

    fun setExpandSuggestions(expandSuggestions: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(
                expandSuggestions = expandSuggestions
            )
        }
    }

    fun qiitaOnDone(query: String) {
        _uiState.update { currentState ->
            currentState.copy(
                qiitaQueryUserInput = "",
                qiitaQueryList = if (!currentState.qiitaQueryList.contains(query)) {
                    currentState.qiitaQueryList.toMutableList().apply {
                        add(query)
                    }
                } else {
                    currentState.qiitaQueryList
                },
                filteredQiitaTagList = QiitaTagList.tagList,
                expandSuggestions = false
            )
        }
    }

    fun zennOnDone(query: String) {
        _uiState.update { currentState ->
            currentState.copy(
                zennQueryUserInput = "",
                zennQuery = query
            )
        }
    }

    fun noteOnDone(query: String) {
        _uiState.update { currentState ->
            currentState.copy(
                noteQueryUserInput = "",
                noteQuery = query
            )
        }
    }

    fun saveUserQuery() {
        viewModelScope.launch {
            dataStoreRepository.saveUserQuery(
                UserQuery(
                    qiitaQuery = if (_uiState.value.qiitaQueryList.isNotEmpty()) {
                        _uiState.value.qiitaQueryList
                            .map { "tag:$it" }
                            .joinToString(separator = " OR ")
                    } else {
                        ""
                    },
                    zennQuery = _uiState.value.zennQuery,
                    noteQuery = _uiState.value.noteQuery
                )
            )
        }
    }
}