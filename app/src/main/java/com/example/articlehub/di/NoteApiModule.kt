package com.example.articlehub.di

import com.example.articlehub.data.local.data_store.DataStoreRepository
import com.example.articlehub.data.remote.note_api.NoteApi
import com.example.articlehub.data.remote.note_api.NoteApiRepository
import com.example.articlehub.data.remote.note_api.NoteApiRepositoryImpl
import com.example.articlehub.data.remote.note_api.NoteArticlePaging
import com.example.articlehub.model.NoteArticle
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NoteApiModule {

    @Provides
    @Singleton
    @Named("Note")
    fun provideNoteRetrofit(): Retrofit {
        return Retrofit
            .Builder()
            .addConverterFactory(Json { ignoreUnknownKeys = true }.asConverterFactory("application/json".toMediaType()))
            .baseUrl("https://note.com/api/v3/")
            .build()
    }

    @Provides
    @Singleton
    fun provideNoteApi(@Named("Note") retrofit: Retrofit): NoteApi = retrofit.create(NoteApi::class.java)

    @Provides
    @Singleton
    fun provideNoteArticlePagingSource(
        noteApi: NoteApi,
        dataStoreRepository: DataStoreRepository
    ): NoteArticlePaging = NoteArticlePaging(noteApi, dataStoreRepository)

    @Provides
    @Singleton
    fun provideNoteApiRepository(
        noteArticlePaging: NoteArticlePaging
    ): NoteApiRepository = NoteApiRepositoryImpl(noteArticlePaging)
}