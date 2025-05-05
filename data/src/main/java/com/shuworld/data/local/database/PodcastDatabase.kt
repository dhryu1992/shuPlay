package com.shuworld.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.shuworld.data.local.dao.EpisodeDao
import com.shuworld.data.local.dao.PodcastDao
import com.shuworld.data.local.dao.RecentEpisodeDao
import com.shuworld.data.local.entity.EpisodeEntity
import com.shuworld.data.local.entity.PodcastEntity
import com.shuworld.data.local.entity.RecentEpisodeEntity

@Database(entities = [PodcastEntity::class, EpisodeEntity::class, RecentEpisodeEntity::class], version = 1)
abstract class PodcastDatabase : RoomDatabase() {
    abstract fun podCastDao(): PodcastDao
    abstract fun episodeDao(): EpisodeDao
    abstract fun RecentEpisodeDao(): RecentEpisodeDao
}