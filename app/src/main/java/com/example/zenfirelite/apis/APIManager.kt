package com.example.zenfirelite.apis

import FieldOptionsDeserializer
import com.example.zenfirelite.apis.datamodels.FieldDetails
import com.example.zenfirelite.apis.datamodels.FieldOptions
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object APIManager {
    private const val BASE_URL = "https://services-copilot.zentrades.pro/api/"

    //create logger
    private val logger = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    // Create cURL logging interceptor
    private val curlLogger = CurlLoggingInterceptor()

    // create okHttp instance
    private val okHttp: OkHttpClient.Builder = OkHttpClient.Builder()
        .addInterceptor(logger)
        .addInterceptor(curlLogger)

    val gson = GsonBuilder()
        .registerTypeAdapter(FieldOptions::class.java, FieldOptionsDeserializer())
        .create()

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttp.build())
            .build()
    }

    val apiInterface: ApiInterface by lazy {
        retrofit.create(ApiInterface::class.java)
    }
}