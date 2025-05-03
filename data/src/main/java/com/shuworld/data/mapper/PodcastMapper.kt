package com.shuworld.data.mapper

import com.shuworld.data.local.PodcastEntity
import com.shuworld.data.remote.PodcastDto
import com.shuworld.domain.model.Podcast

fun PodcastDto.toDomain(): Podcast =
    Podcast(id, title, description, imageUrl, episodes = emptyList())

fun PodcastEntity.toDomain(): Podcast =
    Podcast(id, title, description, imageUrl, episodes = emptyList())

fun Podcast.toEntity(): PodcastEntity =
    PodcastEntity(id, title, description, imageUrl)