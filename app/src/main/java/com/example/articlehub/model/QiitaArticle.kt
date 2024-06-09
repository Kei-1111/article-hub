package com.example.articlehub.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class QiitaArticle(
    @SerialName(value = "id")
    val id: String,

    @SerialName(value = "title")
    val title: String,

    @SerialName(value = "url")
    val url: String,

    @SerialName(value = "created_at")
    val createdDate: String,

    @SerialName(value = "user")
    val qiitaUser: QiitaUser,

    @SerialName(value = "tags")
    val tags: List<Tag>
)

@Serializable
data class QiitaUser(
    @SerialName("name")
    val name: String,

    @SerialName("profile_image_url")
    val profileImageUrl: String,
)

@Serializable
data class Tag(
    @SerialName(value = "name")
    val name: String,
)

