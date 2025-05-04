package com.shuworld.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.shuworld.viewModel.PodcastDetailViewModel
import androidx.compose.runtime.getValue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PodcastDetailScreen(
    podcastId: String,
    viewModel: PodcastDetailViewModel = hiltViewModel<PodcastDetailViewModel, PodcastDetailViewModel.Factory>(
        key = podcastId
    ) {
        it.create(podcastId)
    }
) {
    val podcast by viewModel.podcast.collectAsState()
    val currentPodcast = podcast
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Podcast Detail") }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(padding)
                .padding(16.dp)
        ) {
            if (currentPodcast != null) {
                Text("ID: ${currentPodcast.id}", style = MaterialTheme.typography.headlineSmall)
                Spacer(modifier = Modifier.height(16.dp))
                Text("여기에 상세 내용이 표시됩니다", style = MaterialTheme.typography.bodyMedium)
            } else {
                CircularProgressIndicator()
            }
        }
    }
}