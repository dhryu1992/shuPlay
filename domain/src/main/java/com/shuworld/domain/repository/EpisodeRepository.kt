package com.shuworld.domain.repository

import com.shuworld.domain.model.Episode
import kotlinx.coroutines.flow.Flow

interface EpisodeRepository {
    fun getEpisodes(podcastId: String): Flow<List<Episode>>
    suspend fun refreshEpisodes(podcastId: String)
}