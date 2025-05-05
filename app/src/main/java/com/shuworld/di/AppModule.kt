package com.shuworld.di

import android.content.Context
import androidx.room.Room
import com.shuworld.data.local.database.PodcastDatabase
import com.shuworld.data.local.dao.PodcastDao
import com.shuworld.data.local.dao.EpisodeDao
import com.shuworld.data.local.dao.RecentEpisodeDao
import com.shuworld.data.remote.api.EpisodeApi
import com.shuworld.data.remote.api.PodcastApi
import com.shuworld.data.repository.EpisodeRepositoryImpl
import com.shuworld.data.repository.PodcastRepositoryImpl
import com.shuworld.data.repository.RecentEpisodeRepositoryImpl
import com.shuworld.domain.repository.EpisodeRepository
import com.shuworld.domain.repository.PodcastRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.GsonConverterFactory
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    private val BASE_URL = "http://your.api.url"

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun providePodcastApi(retrofit: Retrofit): PodcastApi {
        return retrofit.create(PodcastApi::class.java)
    }

    @Provides
    @Singleton
    fun provideEpisodeApi(retrofit: Retrofit): EpisodeApi {
        return retrofit.create(EpisodeApi::class.java)
    }

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): PodcastDatabase = Room.databaseBuilder(
        context, PodcastDatabase::class.java, "shuplay_database"
    ).build()

    @Provides
    fun providePodcastDao(database: PodcastDatabase): PodcastDao = database.podCastDao()

    @Provides
    @Singleton
    fun providePodcastRepository(
        api: PodcastApi,
        dao: PodcastDao
    ): PodcastRepository = PodcastRepositoryImpl(api, dao)

    @Provides
    fun provideEpisodeDao(database: PodcastDatabase): EpisodeDao = database.episodeDao()

    @Provides
    @Singleton
    fun provideEpisodeRepository(
        api: EpisodeApi,
        dao: EpisodeDao
    ): EpisodeRepository = EpisodeRepositoryImpl(api, dao)

    @Provides
    fun provideRecentEpisodeDao(database: PodcastDatabase): RecentEpisodeDao = database.RecentEpisodeDao()

    @Provides
    @Singleton
    fun provideRecentEpisodeRepository(
        dao: RecentEpisodeDao
    ) : RecentEpisodeRepositoryImpl = RecentEpisodeRepositoryImpl(dao)
}