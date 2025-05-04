package com.shuworld.data.repository

import com.shuworld.data.local.PodcastDao
import com.shuworld.data.mapper.toDomain
import com.shuworld.data.mapper.toEntity
import com.shuworld.data.remote.PodcastApi
import com.shuworld.domain.model.Podcast
import javax.inject.Inject
import com.shuworld.domain.repository.PodcastRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow

class PodcastRepositoryImpl @Inject constructor(
    private val api: PodcastApi,
    private val dao: PodcastDao,
) : PodcastRepository {
    override fun getPodcasts(): Flow<List<Podcast>> = flow {
        val local = dao.getAllPodcasts().firstOrNull().orEmpty()
        emit(local.map { it.toDomain() })

        try {
            val remote = api.getPodcasts()
            dao.clearAll()
            dao.insertPodcasts(remote.map { it.toEntity() })
            emit(remote.map { it.toDomain() })
            // todo: API 실패 시
        }
    }

    override suspend fun getPodcast(id: String): Podcast {
        val local = dao.getAllPodcasts().firstOrNull()?.find { it.id == id }
        if (local != null) return local.toDomain()

        val remote = api.getPodcasts().find { it.id == id }
        if (remote != null) {
            dao.insertPodcasts(listOf(remote.toEntity()))
            return remote.toDomain()
        }

        throw IllegalArgumentException("Podcast not found")
    }
}