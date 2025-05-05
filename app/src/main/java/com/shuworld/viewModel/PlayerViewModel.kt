package com.shuworld.viewModel

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.shuworld.domain.model.Episode
import com.shuworld.domain.repository.EpisodeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayerViewModel @Inject constructor(
    private val episodeRepository: EpisodeRepository,
    @ApplicationContext private val context: Context,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val episodeId: String = checkNotNull(savedStateHandle["episodeId"])

    private val _episode = MutableStateFlow<Episode?>(null)
    val episode: StateFlow<Episode?> = _episode

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean> = _isPlaying

    private val player: ExoPlayer = ExoPlayer.Builder(context).build()

    init {
        viewModelScope.launch {
            episodeRepository.getEpisodeById(episodeId).collect {
                _episode.value = it
                it?.audioUrl?.let { url ->
                    player.setMediaItem(MediaItem.fromUri(url))
                    player.prepare()
                }
            }
        }
    }

    fun togglePlayPause() {
        if (player.isPlaying) {
            player.pause()
            _isPlaying.value = false
        } else {
            player.play()
            _isPlaying.value = true
        }
    }

    override fun onCleared() {
        super.onCleared()
        player.release()
    }
}