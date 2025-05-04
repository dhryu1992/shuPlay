package com.shuworld.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shuworld.domain.model.Podcast
import com.shuworld.domain.repository.PodcastRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn

@HiltViewModel(assistedFactory = PodcastDetailViewModel.Factory::class)
class PodcastDetailViewModel @AssistedInject constructor(
    private val repository: PodcastRepository,
    @Assisted private val podcastId: String,
) : ViewModel() {

    val podcast: StateFlow<Podcast?> = flow {
        emit(repository.getPodcast(podcastId))
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = null
    )

    @AssistedFactory
    interface Factory {
        fun create(podcastId: String): PodcastDetailViewModel
    }
}
