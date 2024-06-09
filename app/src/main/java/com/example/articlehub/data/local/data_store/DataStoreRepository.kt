package com.example.articlehub.data.local.data_store

import com.example.articlehub.model.UserQuery
import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {

    suspend fun saveUserQuery(userQuery: UserQuery)

    suspend fun getUserQuery(): UserQuery
}