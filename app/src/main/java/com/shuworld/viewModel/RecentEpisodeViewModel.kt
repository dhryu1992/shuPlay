package com.shuworld.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shuworld.data.local.entity.RecentEpisodeEntity
import com.shuworld.data.repository.RecentEpisodeRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class RecentEpisodeViewModel @Inject constructor(
    private val repository: RecentEpisodeRepositoryImpl
) : ViewModel() {

    val recentEpisodes: StateFlow<List<RecentEpisodeEntity>> =
        repository.getRecentEpisodes().stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000), emptyList()
        )
}