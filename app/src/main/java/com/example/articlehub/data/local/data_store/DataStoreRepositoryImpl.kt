package com.example.articlehub.data.local.data_store

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.articlehub.model.UserQuery
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class DataStoreRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : DataStoreRepository{

    private companion object {
        val KEY_QIITA_QUERY = stringPreferencesKey("qiita_query")
        val KEY_ZENN_QUERY = stringPreferencesKey("zenn_query")
        val KEY_NOTE_QUERY = stringPreferencesKey("note_query")
    }

    override suspend fun saveUserQuery(userQuery: UserQuery) {
        dataStore.edit { preferences ->
            preferences[KEY_QIITA_QUERY] = userQuery.qiitaQuery
            preferences[KEY_ZENN_QUERY] = userQuery.zennQuery
            preferences[KEY_NOTE_QUERY] = userQuery.noteQuery
        }
    }

    override suspend fun getUserQuery(): UserQuery {
        return dataStore.data
            .catch {
                if(it is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw it
                }
            }.map { preferences ->
                UserQuery(
                    qiitaQuery = preferences[KEY_QIITA_QUERY] ?: "",
                    zennQuery = preferences[KEY_ZENN_QUERY] ?: "",
                    noteQuery = preferences[KEY_NOTE_QUERY] ?: ""
                )
            }.first()
    }
}