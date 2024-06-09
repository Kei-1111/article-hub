package com.example.articlehub.ui.screens.query_setting

import QiitaTagList

data class QuerySettingUiState(
    val qiitaQueryUserInput: String = "",
    val qiitaQueryList: MutableList<String> = mutableListOf(),
    val filteredQiitaTagList: MutableList<String> = QiitaTagList.tagList,
    val expandSuggestions: Boolean = false,
    val zennQueryUserInput: String = "",
    val zennQuery: String = "",
    val noteQueryUserInput: String = "",
    val noteQuery: String = "",
)