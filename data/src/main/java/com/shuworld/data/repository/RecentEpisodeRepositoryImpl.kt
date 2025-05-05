package com.shuworld.data.repository

import com.shuworld.data.local.dao.RecentEpisodeDao
import com.shuworld.data.local.entity.RecentEpisodeEntity
import com.shuworld.domain.model.Episode
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RecentEpisodeRepositoryImpl @Inject constructor(
    private val dao: RecentEpisodeDao
) {
    fun getRecentEpisodes(): Flow<List<RecentEpisodeEntity>> = dao.getRecentEpisodes()

    suspend fun addRecentEpisode(episode: Episode) {
        dao.insertRecentEpisode(
            RecentEpisodeEntity(
                id = episode.id,
                title = episode.title,
                description = episode.description,
                imageUrl = episode.imageUrl,
                audioUrl = episode.audioUrl
            )
        )
    }
}