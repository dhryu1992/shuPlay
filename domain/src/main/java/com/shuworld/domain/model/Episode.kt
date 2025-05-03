package com.shuworld.domain.model

data class Episode(
    val id: String,
    val title: String,
    val audioUrl: String,
    val duration: Long,
    val publishedDate: String,
)
