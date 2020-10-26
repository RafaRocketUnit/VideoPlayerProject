package com.globant.videoplayerproject.api

import com.globant.videoplayerproject.model.AccessToken
import com.globant.videoplayerproject.model.Stream
import com.globant.videoplayerproject.model.TopGames
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*


private const val BASE_URL = "https://api.twitch.tv/"

private const val BASE_URL_TOKEN = "https://id.twitch.tv/oauth2/"


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

private val retrofitToken = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create(gson))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL_TOKEN)
    .client(client)
    .build()

interface RepositoryApiService {
    @Headers("Client-Id: xzpd1f4527fu8fct7p7own0pgi35v5")
    @GET("helix/games/top")
    fun getTopGamesAsync(@Header("Authorization") accessToken: String, @Query("first") gamesToLoad: Int):
            Deferred<TopGames>

    @Headers("Client-Id: xzpd1f4527fu8fct7p7own0pgi35v5")
    @GET("helix/streams")
    fun getTopStreamsAsync(@Header("Authorization") accessToken: String, @Query("game_id") gameId: String):
            Deferred<Stream>

}

interface  RepositoryApiServiceToken {
    @POST("token")
    fun getAccessToken(@Query("client_id") clientId: String, @Query("client_secret") clientSecret: String, @Query("grant_type") type: String ):
            Deferred<AccessToken>
}

object RepositoryApi {
    val retrofitService: RepositoryApiService by lazy {
        retrofit.create(RepositoryApiService::class.java)
    }

    val retrofitServiceToken: RepositoryApiServiceToken by lazy {
        retrofitToken.create(RepositoryApiServiceToken::class.java)
    }
}