package com.globant.videoplayerproject.api

import com.globant.videoplayerproject.model.TopGames
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers


private const val BASE_URL = "https://api.twitch.tv/helix/"

var gson = GsonBuilder().create()

private val interceptor = HttpLoggingInterceptor().also {
    it.level = HttpLoggingInterceptor.Level.BODY
}
private val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create(gson))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .client(client)
    .build()

interface RepositoryApiService {
    @Headers("Authorization: Bearer cfabdegwdoklmawdzdo98xt2fo512y",
        "Client-Id: uo6dggojyb8d6soh92zknwmi5ej1q2")
    @GET("/games/top")
    fun getTopGamesAsync():
            Deferred<TopGames>
}

object RepositoryApi {
    val retrofitService: RepositoryApiService by lazy {
        retrofit.create(RepositoryApiService::class.java)
    }
}