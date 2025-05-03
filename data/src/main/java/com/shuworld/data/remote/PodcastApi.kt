package com.shuworld.data.remote

import retrofit2.http.GET

interface PodcastApi {
    @GET("podcasts.json")
    suspend fun getPodcasts(): List<PodcastDto>
}