package com.globant.videoplayerproject.api

import com.globant.videoplayerproject.model.Stream
import com.globant.videoplayerproject.model.TopGames
import com.globant.videoplayerproject.model.Video
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

interface RepositoryApiService {
    @Headers("Client-Id: xzpd1f4527fu8fct7p7own0pgi35v5")
    @GET("helix/games/top")
    fun getTopGamesAsync(
        @Header("Authorization") accessToken: String,
        @Query("first") gamesToLoad: Int
    ):
            Deferred<TopGames>

    @Headers("Client-Id: xzpd1f4527fu8fct7p7own0pgi35v5")
    @GET("helix/streams")
    fun getTopStreamsAsync(
        @Header("Authorization") accessToken: String,
        @Query("game_id") gameId: String
    ):
            Deferred<Stream>

    @Headers("Client-Id: xzpd1f4527fu8fct7p7own0pgi35v5")
    @GET("helix/videos")
    fun getVideosAsync(
        @Header("Authorization") accessToken: String,
        @Query("user_id") userId: String
    ):
            Deferred<Video>
}