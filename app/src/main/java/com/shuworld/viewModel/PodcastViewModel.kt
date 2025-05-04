package com.shuworld.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shuworld.domain.model.Podcast
import com.shuworld.domain.repository.PodcastRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PodcastViewModel @Inject constructor(
    private val repository: PodcastRepository
) : ViewModel() {

    private val _podcastList = MutableStateFlow<List<Podcast>>(emptyList())

    val podcastList: StateFlow<List<Podcast>> = _podcastList

    init {
        observePodcasts()
    }

    private fun observePodcasts() {
        viewModelScope.launch {
            repository.getPodcasts().catch { error ->
                //todo: 에러 핸들링
            }.collect { podcasts -> _podcastList.value = podcasts }
        }
    }

    fun getPodcastById(id: String): Flow<Podcast> = flow {
        val result = repository.getPodcast(id)
        emit(result)
    }
}