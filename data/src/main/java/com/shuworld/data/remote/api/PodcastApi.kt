package com.shuworld.data.remote.api

import com.shuworld.data.remote.model.PodcastDto
import retrofit2.http.GET

interface PodcastApi {
    @GET("podcasts.json")
    suspend fun getPodcasts(): List<PodcastDto>
}