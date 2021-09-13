package com.github.stonybean.myglow.network

import com.github.stonybean.myglow.repository.GlowRepository
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Created by Joo on 2021/09/10
 * - OkHttp 설정
 * - GSON (JSON parsing)
 * - Retrofit 기본 설정
 */
@Module
@InstallIn(SingletonComponent::class)
object BaseService {
    private val BASE_URL = "https://s3.ap-northeast-2.amazonaws.com/public.glowday.com/test/app/"

    // okhttp log
    @Singleton
    @Provides
    fun httpLoggingInterceptor() =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    // json parsing
    @Singleton
    @Provides
    fun gson() = GsonBuilder()
        .setLenient()
        .create()

    @Singleton
    @Provides
    fun client() = OkHttpClient.Builder()
        .addInterceptor(httpLoggingInterceptor())
        .build()

    @Singleton
    @Provides
    fun getClient(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson()))
            .client(okHttpClient)
            .build()

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): GlowAPI = retrofit.create(GlowAPI::class.java)

    @Singleton
    @Provides
    fun provideRepository(glowAPI: GlowAPI) = GlowRepository(glowAPI)
}