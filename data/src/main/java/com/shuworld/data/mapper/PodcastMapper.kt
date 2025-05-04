package com.shuworld.data.mapper

import com.shuworld.data.local.PodcastEntity
import com.shuworld.data.remote.PodcastDto
import com.shuworld.domain.model.Podcast

fun PodcastDto.toDomain(): Podcast =
    Podcast(
        id = id,
        title = title,
        description = description,
        imageUrl = imageUrl,
        audioUrl = audioUrl,
        episodes = emptyList(),
    )

fun PodcastEntity.toDomain(): Podcast =
    Podcast(
        id = id,
        title = title,
        description = description,
        imageUrl = imageUrl,
        audioUrl = audioUrl,
        episodes = emptyList(),
    )

fun PodcastDto.toEntity(): PodcastEntity =
    PodcastEntity(
        id = id,
        title = title,
        description = description,
        imageUrl = imageUrl,
        audioUrl = audioUrl,
    )