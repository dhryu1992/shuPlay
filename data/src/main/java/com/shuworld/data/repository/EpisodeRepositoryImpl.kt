package com.shuworld.data.repository

import com.shuworld.data.local.dao.EpisodeDao
import com.shuworld.data.mapper.toDomain
import com.shuworld.data.mapper.toEntity
import com.shuworld.data.remote.api.EpisodeApi
import com.shuworld.domain.model.Episode
import com.shuworld.domain.repository.EpisodeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class EpisodeRepositoryImpl @Inject constructor(
    private val api: EpisodeApi,
    private val dao: EpisodeDao,
) : EpisodeRepository {
    override fun getEpisodes(podcastId: String): Flow<List<Episode>> {
        return dao.getEpisodesByPodcast(podcastId).map { list ->
            list.map { it.toDomain() }
        }
    }

    override suspend fun refreshEpisodes(podcastId: String) {
        val episodes = api.getEpisodes(podcastId).map { it.toEntity() }

        dao.deleteEpisodesByPodcast(podcastId)
        dao.insertEpisodes(episodes)
    }

    override fun getEpisodeById(episodeId: String): Flow<Episode?> {
        return dao.getEpisodeById(episodeId).map { it?.toDomain() }
    }
}