package com.shuworld.domain.model

data class Episode(
    val id: String,
    val podcastId: String,
    val title: String,
    val audioUrl: String,
    val description: String,
    val imageUrl: String,
)