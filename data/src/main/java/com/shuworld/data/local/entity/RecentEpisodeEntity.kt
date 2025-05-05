package com.shuworld.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recent_episodes")
data class RecentEpisodeEntity(
    @PrimaryKey val id: String,
    val title: String,
    val description: String,
    val imageUrl: String,
    val audioUrl: String,
    val playedAt: Long = System.currentTimeMillis()
)