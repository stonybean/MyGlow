package com.github.stonybean.myglow.network

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Joo on 2021/09/10
 * - OkHttp 설정
 * - GSON (JSON parsing)
 * - Retrofit 기본 설정
 */
class BaseService {
    private val BASE_URL = "https://s3.ap-northeast-2.amazonaws.com/public.glowday.com/test/app/"

    // okhttp log
    private val httpLoggingInterceptor =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    // json parsing
    private val gson = GsonBuilder()
        .setLenient()
        .create()

    private val client = OkHttpClient.Builder()
        .addInterceptor(httpLoggingInterceptor)
        .build()

    fun getClient(): Retrofit? =
        Retrofit.Builder().baseUrl(BASE_URL).client(client)
            .addConverterFactory(GsonConverterFactory.create(gson)).build()
}