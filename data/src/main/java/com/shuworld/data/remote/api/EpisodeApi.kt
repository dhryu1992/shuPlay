package com.shuworld.data.remote.api

import com.shuworld.data.remote.model.EpisodeDto
import retrofit2.http.GET
import retrofit2.http.Query

interface EpisodeApi {
    @GET("episodes")
    suspend fun getEpisodes(
        @Query("podcastId") podcastId: String
    ): List<EpisodeDto>
}