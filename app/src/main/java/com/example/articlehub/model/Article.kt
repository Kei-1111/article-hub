package com.example.articlehub.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "articles")
data class Article(
    @PrimaryKey
    val id: String = "",
    val title: String = "",
    val url: String = "",
    val createdDate: String = "",
    val userName: String = "",
    val userProfileImageUrl: String = "",
    val tags: List<Tag> = emptyList(),
    var isFavorite: Boolean = false,
    val source: String
) {
    var favoriteState by mutableStateOf(isFavorite)
        private set


    fun addFavorite() {
        favoriteState = true
        isFavorite = true
    }

    fun deleteFavorite() {
        favoriteState = false
        isFavorite = false
    }
}
