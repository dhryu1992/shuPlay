package com.shuworld.data.remote.model

data class EpisodeDto(
    val id: String,
    val podcastId: String,
    val title: String,
    val audioUrl: String,
    val description: String,
    val imageUrl: String,
)