package com.shuworld.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.shuworld.data.local.entity.PodcastEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PodcastDao {
    @Query("SELECT * FROM podcasts")
    fun getAllPodcasts(): Flow<List<PodcastEntity>>

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insertPodcasts(podcasts: List<PodcastEntity>)

    @Query("DELETE FROM podcasts")
    suspend fun clearAll()
}