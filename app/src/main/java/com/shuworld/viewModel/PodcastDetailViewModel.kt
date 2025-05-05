package com.shuworld.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.shuworld.domain.model.Episode
import com.shuworld.domain.model.Podcast
import com.shuworld.domain.repository.EpisodeRepository
import com.shuworld.domain.repository.PodcastRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = PodcastDetailViewModel.Factory::class)
class PodcastDetailViewModel @AssistedInject constructor(
    private val podcastRepository: PodcastRepository,
    private val episodeRepository: EpisodeRepository,
    @Assisted private val podcastId: String,
    @ApplicationContext private val context: Context,
) : ViewModel() {

    private val _podcast = MutableStateFlow<Podcast?>(null)
    val podcast: StateFlow<Podcast?> = _podcast

    val episodes: StateFlow<List<Episode>> = episodeRepository.getEpisodes(podcastId)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val player: ExoPlayer = ExoPlayer.Builder(context).build()

    init {
        viewModelScope.launch {
            val result = podcastRepository.getPodcast(podcastId)
            _podcast.value = result

            // URL 연결
            player.setMediaItem(MediaItem.fromUri(result.audioUrl))
            player.prepare()
        }
    }

    fun play() {
        player.play()
    }

    fun pause() {
        player.pause()
    }

    override fun onCleared() {
        super.onCleared()
        player.release()
    }

    //    val podcast: StateFlow<Podcast?> = flow {
//        emit(repository.getPodcast(podcastId))
//    }.stateIn(
//        scope = viewModelScope,
//        started = SharingStarted.WhileSubscribed(5_000),
//        initialValue = null
//    )

    @AssistedFactory
    interface Factory {
        fun create(podcastId: String): PodcastDetailViewModel
    }
}
