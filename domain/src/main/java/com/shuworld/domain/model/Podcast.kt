package com.shuworld.domain.model

data class Podcast (
    val id: String,
    val title: String,
    val description: String,
    val imageUrl: String,
    val episodes: List<Episode>,
    val audioUrl: String,
)