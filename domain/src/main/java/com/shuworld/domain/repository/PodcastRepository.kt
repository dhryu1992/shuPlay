package com.shuworld.domain.repository

import com.shuworld.domain.model.Podcast
import kotlinx.coroutines.flow.Flow

interface PodcastRepository {
    fun getPodcasts(): Flow<List<Podcast>>
    suspend fun getPodcast(id: String): Podcast
}