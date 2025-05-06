package com.shuworld.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shuworld.domain.model.Podcast
import com.shuworld.domain.repository.PodcastRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PodcastViewModel @Inject constructor(
    private val repository: PodcastRepository
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val _podcastList = MutableStateFlow<List<Podcast>>(emptyList())
    val podcastList: StateFlow<List<Podcast>> = _searchQuery
        .combine(_podcastList) { query, list ->
            if (query.isBlank()) list
            else list.filter { it.title.contains(query, ignoreCase = true) }
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    init {
        Log.d("siwoo", "observePodcasts() 호출 됨")
        observePodcasts()
    }

    private fun observePodcasts() {
        viewModelScope.launch {
            repository.getPodcasts().collect { podcasts ->
                _podcastList.value = podcasts
            }
        }
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun getPodcastById(id: String): Flow<Podcast> = flow {
        val result = repository.getPodcast(id)
        emit(result)
    }
}