package com.shuworld.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.shuworld.data.local.entity.RecentEpisodeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RecentEpisodeDao {

    @Query("SELECT * FROM recent_episodes ORDER BY playedAt DESC LIMIT 10")
    fun getRecentEpisodes(): Flow<List<RecentEpisodeEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecentEpisode(episode: RecentEpisodeEntity)
}