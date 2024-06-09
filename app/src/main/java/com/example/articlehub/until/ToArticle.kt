package com.example.articlehub.until

import com.example.articlehub.model.Article
import com.example.articlehub.model.ArticleSource
import com.example.articlehub.model.NoteArticle
import com.example.articlehub.model.QiitaArticle
import com.example.articlehub.model.ZennArticle

fun QiitaArticle.toArticle() = Article(
    id = id,
    title = title,
    url = url,
    createdDate = createdDate,
    userName = qiitaUser.name,
    userProfileImageUrl = qiitaUser.profileImageUrl,
    tags = tags,
    source = ArticleSource.QIITA.name
)

fun ZennArticle.toArticle() = Article(
    id = id.toString(),
    title = title,
    url = "https://zenn.dev$path",
    createdDate = publishedAt,
    userName = zennUser.userName,
    userProfileImageUrl = zennUser.avatarSmallUrl,
    tags = emptyList(),
    source = ArticleSource.ZENN.name
)

fun NoteArticle.toArticle() = Article(
    id = id.toString(),
    title = title,
    url = "https://note.com/${noteUser.urlName}/n/$key",
    createdDate = publishAt,
    userName = noteUser.name,
    userProfileImageUrl = noteUser.userImageProfilePath,
    tags = emptyList(),
    source = ArticleSource.NOTE.name
)