package com.shuworld.di

import android.content.Context
import androidx.room.Room
import com.shuworld.data.local.PodcastDatabase
import com.shuworld.data.local.PodcastDao
import com.shuworld.data.remote.PodcastApi
import com.shuworld.data.repository.PodcastRepositoryImpl
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

    @Provides
    @Singleton
    fun providePodcastApi(): PodcastApi {
        return Retrofit.Builder().baseUrl("http://your.api.url")
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(PodcastApi::class.java)
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
}