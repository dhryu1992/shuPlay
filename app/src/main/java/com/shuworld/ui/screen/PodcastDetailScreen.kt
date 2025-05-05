package com.shuworld.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.shuworld.viewModel.PodcastDetailViewModel
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import coil3.compose.AsyncImage
import com.shuworld.viewModel.EpisodeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PodcastDetailScreen(
    podcastId: String,
    podcastViewModel: PodcastDetailViewModel = hiltViewModel<PodcastDetailViewModel, PodcastDetailViewModel.Factory>(
        key = podcastId
    ) { it.create(podcastId) },
    episodeViewModel: EpisodeViewModel = hiltViewModel()
) {
    val podcast by podcastViewModel.podcast.collectAsState()
    val episodes by episodeViewModel.episodes.collectAsState()

    val isPlaying by episodeViewModel.isPlaying.collectAsState()
    val currentPosition by episodeViewModel.currentPosition.collectAsState()
    val duration by episodeViewModel.duration.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Podcast Detail") },
                navigationIcon = {
                    IconButton(onClick = { /* TODO: Back navigation */ }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            podcast?.let { currentPodcast ->
                AsyncImage(
                    model = currentPodcast.imageUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(16 / 9f)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text("🎙️ ${currentPodcast.title}", style = MaterialTheme.typography.headlineSmall)
                Spacer(modifier = Modifier.height(8.dp))

                Text(currentPodcast.description, style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.height(24.dp))

                HorizontalDivider()
                Spacer(modifier = Modifier.height(8.dp))

                Text("🎧 Episodes", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(8.dp))

                episodes.forEach { episode ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    ) {
                        Text(episode.title, style = MaterialTheme.typography.bodyLarge)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(episode.description, style = MaterialTheme.typography.bodySmall)

                        Spacer(modifier = Modifier.height(8.dp))

                        Slider(
                            value = currentPosition.toFloat(),
                            onValueChange = {},
                            valueRange = 0f..duration.toFloat()
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            if (isPlaying) {
                                Button(onClick = { episodeViewModel.pause() }) {
                                    Text("⏸ Pause")
                                }
                            } else {
                                Button(onClick = { episodeViewModel.play(episode.audioUrl) }) {
                                    Text("▶️ Play")
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                }
            } ?: CircularProgressIndicator()
        }
    }
}