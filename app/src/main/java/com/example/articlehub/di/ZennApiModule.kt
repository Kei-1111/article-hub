package com.example.articlehub.di

import com.example.articlehub.data.local.data_store.DataStoreRepository
import com.example.articlehub.data.remote.zenn_api.ZennApi
import com.example.articlehub.data.remote.zenn_api.ZennApiRepository
import com.example.articlehub.data.remote.zenn_api.ZennApiRepositoryImpl
import com.example.articlehub.data.remote.zenn_api.ZennArticlePagingSource
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
object ZennApiModule {

    @Provides
    @Singleton
    @Named("Zenn")
    fun provideZennRetrofit(): Retrofit{
        return Retrofit
            .Builder()
            .addConverterFactory(Json{ ignoreUnknownKeys = true }.asConverterFactory("application/json".toMediaType()))
            .baseUrl("https://zenn.dev/api/")
            .build()
    }

    @Provides
    @Singleton
    fun provideZennApi(@Named("Zenn") retrofit: Retrofit): ZennApi = retrofit.create(ZennApi::class.java)

    @Provides
    @Singleton
    fun provideZennArticlePagingSource(
        zennApi: ZennApi,
        dataStoreRepository: DataStoreRepository
    ): ZennArticlePagingSource = ZennArticlePagingSource(zennApi, dataStoreRepository)

    @Provides
    @Singleton
    fun provideZennApiRepository(
        zennArticlePagingSource: ZennArticlePagingSource
    ): ZennApiRepository = ZennApiRepositoryImpl(zennArticlePagingSource)
}