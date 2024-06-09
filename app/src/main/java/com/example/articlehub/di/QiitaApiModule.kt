package com.example.articlehub.di

import com.example.articlehub.data.local.data_store.DataStoreRepository
import com.example.articlehub.data.remote.qiita_api.QiitaApi
import com.example.articlehub.data.remote.qiita_api.QiitaApiRepository
import com.example.articlehub.data.remote.qiita_api.QiitaApiRepositoryImpl
import com.example.articlehub.data.remote.qiita_api.QiitaArticlePagingSource
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
object QiitaApiModule {

    @Provides
    @Singleton
    @Named("Qiita")
    fun provideQiitaRetrofit(): Retrofit {
        return Retrofit
            .Builder()
            .addConverterFactory(Json{ignoreUnknownKeys = true}.asConverterFactory("application/json".toMediaType()))
            .baseUrl("https://qiita.com/api/v2/")
            .build()
    }

    @Provides
    @Singleton
    fun provideQiitaApi(@Named("Qiita") retrofit: Retrofit): QiitaApi = retrofit.create(QiitaApi::class.java)

    @Provides
    @Singleton
    fun provideQiitaArticlePagingSource(
        qiitaApi: QiitaApi,
        dataStoreRepository: DataStoreRepository
    ): QiitaArticlePagingSource = QiitaArticlePagingSource(qiitaApi, dataStoreRepository)

    @Provides
    @Singleton
    fun provideQiitaApiRepository(
        qiitaArticlePagingSource: QiitaArticlePagingSource,
    ): QiitaApiRepository = QiitaApiRepositoryImpl(qiitaArticlePagingSource)
}