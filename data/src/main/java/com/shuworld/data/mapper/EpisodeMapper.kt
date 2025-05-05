package com.shuworld.data.mapper

import com.shuworld.data.local.entity.EpisodeEntity
import com.shuworld.data.remote.model.EpisodeDto
import com.shuworld.domain.model.Episode

fun EpisodeDto.toEntity(): EpisodeEntity = EpisodeEntity(
    id = id,
    podcastId = podcastId,
    title = title,
    audioUrl = audioUrl,
    description = description,
)

fun EpisodeEntity.toDomain(): Episode = Episode(
    id = id,
    podcastId = podcastId,
    title = title,
    audioUrl = audioUrl,
    description = description,
)

