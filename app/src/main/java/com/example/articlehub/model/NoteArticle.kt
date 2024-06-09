package com.example.articlehub.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NoteResponse(
    @SerialName("data")
    val data: NoteData
)

@Serializable
data class NoteData(
    @SerialName("notes")
    val notes: Notes
)

@Serializable
data class Notes(
    @SerialName("contents")
    val contents: List<NoteArticle>
)


@Serializable
data class NoteArticle(
    @SerialName("id")
    val id: Int,

    @SerialName("name")
    val title: String,

    @SerialName("key")
    val key: String,

    @SerialName("publish_at")
    val publishAt: String,

    @SerialName("user")
    val noteUser: NoteUesr,
)

@Serializable
data class NoteUesr(
    @SerialName("name")
    val name: String,

    @SerialName("urlname")
    val urlName: String,

    @SerialName("user_profile_image_path")
    val userImageProfilePath: String,
)
