package com.shuworld.player

import android.content.Context
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExoPlayerManager @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean> = _isPlaying

    private val _currentPosition = MutableStateFlow(0L)
    val currentPosition: StateFlow<Long> = _currentPosition

    private val _duration = MutableStateFlow(0L)
    val duration : StateFlow<Long> = _duration

    private val player = ExoPlayer.Builder(context).build().apply {
        addListener(object: Player.Listener {
            override fun onIsPlayingChanged(isPlaying: Boolean) {
                _isPlaying.value = isPlaying
            }

            override fun onPlaybackStateChanged(playbackState: Int) {
                if (playbackState == Player.STATE_READY) {
                    _duration.value = duration
                }
            }
        })
    }

    private var updateJob: Job? = null

    fun play(url: String) {
        val mediaItem = MediaItem.fromUri(url)
        player.setMediaItem(mediaItem)
        player.prepare()
        player.play()

        updateJob?.cancel()
        updateJob = CoroutineScope(Dispatchers.Default).launch {
            while(true) {
                _currentPosition.value = player.currentPosition
                delay(500L)
            }
        }
    }

    fun pause() {
        player.pause()
    }

    fun stop() {
        player.stop()
    }

    fun release() {
        updateJob?.cancel()
        player.release()
    }
}