package com.globant.videoplayerproject.model

data class DataVideo(
    val id: String,
    val user_id: String,
    val user_name: String,
    val title: String,
    val description: String,
    val created_at: String,
    val published_at: String,
    val url: String,
    val thumbnail_url: String,
    val viewable: String,
    val view_count: Int,
    val language: String,
    val type: String,
    val duration: String
)