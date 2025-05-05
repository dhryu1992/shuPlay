package com.shuworld.viewModel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shuworld.data.repository.RecentEpisodeRepositoryImpl
import com.shuworld.domain.model.Episode
import com.shuworld.domain.repository.EpisodeRepository
import com.shuworld.player.ExoPlayerManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EpisodeViewModel @Inject constructor(
    private val episodeRepository: EpisodeRepository,
    private val recentEpisodeRepositoryImpl: RecentEpisodeRepositoryImpl,
    private val player: ExoPlayerManager,
    savedStateHandle: SavedStateHandle,
): ViewModel() {
    private val podcastId: String = checkNotNull(savedStateHandle["podcastId"])

    val episodes: StateFlow<List<Episode>> = episodeRepository
        .getEpisodes(podcastId)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        viewModelScope.launch {
            episodeRepository.refreshEpisodes(podcastId)
        }
    }

    fun play(url:String) {
        viewModelScope.launch {
            val episode = episodes.value.find { it.audioUrl == url }
            if (episode != null) {
                recentEpisodeRepositoryImpl.addRecentEpisode(episode)
            }
        }
        player.play(url)
    }

    fun stop() {
        player.stop()
    }

    override fun onCleared() {
        super.onCleared()
        player.release()
    }
}