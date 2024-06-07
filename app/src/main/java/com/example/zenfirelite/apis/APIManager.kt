package com.example.zenfirelite.apis

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object APIManager {
    private const val BASE_URL ="https://services-copilot.zentrades.pro/api/"

    //create logger
    private val logger = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    // create okHttp instance
    private val okHttp : OkHttpClient.Builder = OkHttpClient.Builder().addInterceptor(logger)

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttp.build())
            .build()
    }

    val apiInterface: ApiInterface by lazy {
        retrofit.create(ApiInterface::class.java)
    }
}