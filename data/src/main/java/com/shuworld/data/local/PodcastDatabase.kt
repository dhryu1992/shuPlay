package com.shuworld.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [PodcastEntity::class], version = 1)
abstract class PodcastDatabase : RoomDatabase() {
    abstract fun podCastDao(): PodcastDao
}