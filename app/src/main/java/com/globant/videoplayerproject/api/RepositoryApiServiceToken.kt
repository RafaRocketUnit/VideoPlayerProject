package com.globant.videoplayerproject.api

import com.globant.videoplayerproject.model.AccessToken
import kotlinx.coroutines.Deferred
import retrofit2.http.POST
import retrofit2.http.Query

interface  RepositoryApiServiceToken {
    @POST("token")
    fun getAccessToken(@Query("client_id") clientId: String, @Query("client_secret") clientSecret: String, @Query("grant_type") type: String ):
            Deferred<AccessToken>
}