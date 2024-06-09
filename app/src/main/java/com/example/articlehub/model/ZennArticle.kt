package com.example.articlehub.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ZennResponse(
    @SerialName("articles")
    val articles: List<ZennArticle>
)

@Serializable
data class ZennArticle (
    @SerialName(value = "id")
    val id: Int,

    @SerialName(value = "title")
    val title: String,

    @SerialName(value = "path")
    val path: String,

    @SerialName(value = "published_at")
    val publishedAt: String,

    @SerialName(value = "user")
    val zennUser: ZennUser,
)

@Serializable
data class ZennUser(
    @SerialName("username")
    val userName: String,

    @SerialName("avatar_small_url")
    val avatarSmallUrl: String,
)
