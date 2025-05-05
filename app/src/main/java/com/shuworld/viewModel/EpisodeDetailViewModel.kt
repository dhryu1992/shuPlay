package com.shuworld.viewModel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shuworld.domain.model.Episode
import com.shuworld.domain.repository.EpisodeRepository
import com.shuworld.player.ExoPlayerManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class EpisodeDetailViewModel @Inject constructor(
    private val repository: EpisodeRepository,
    private val player: ExoPlayerManager,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val episodeId: String = checkNotNull(savedStateHandle["episodeId"])

    val episode: StateFlow<Episode?> = repository.getEpisodeById(episodeId)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    fun play(url: String) {
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