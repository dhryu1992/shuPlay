package com.shuworld.data.repository

import com.shuworld.data.local.PodcastDao
import com.shuworld.data.mapper.toDomain
import com.shuworld.data.remote.PodcastApi
import com.shuworld.domain.model.Podcast
import javax.inject.Inject
import com.shuworld.domain.repository.PodcastRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PodcastRepositoryImpl @Inject constructor(
    private val api: PodcastApi,
    private val dao: PodcastDao
) : PodcastRepository {
    override fun getPodcasts(): Flow<List<Podcast>> =
        dao.getAll().map { it.map { it.toDomain() } }


    override suspend fun getPodcast(id: String): Podcast {
        return api.getPodcasts().find { it.id == id }?.toDomain()
            ?: throw IllegalArgumentException("Podcast not found")
    }
}