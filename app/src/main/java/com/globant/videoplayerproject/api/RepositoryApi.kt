package com.globant.videoplayerproject.api

object RepositoryApi {
    val retrofitService: RepositoryApiService by lazy {
        retrofit.create(RepositoryApiService::class.java)
    }

    val retrofitServiceToken: RepositoryApiServiceToken by lazy {
        retrofitToken.create(RepositoryApiServiceToken::class.java)
    }
}