package com.globant.videoplayerproject.model

data class DataStream(
    val id: String,
    val user_id: String,
    val user_name: String,
    val game_id: String,
    val type: String,
    val title: String,
    val viewer_count: Int,
    val started_at: String,
    val language: String,
    val thumbnail_url: String
)