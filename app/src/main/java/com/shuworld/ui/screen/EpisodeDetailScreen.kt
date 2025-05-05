package com.shuworld.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
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
import com.shuworld.viewModel.EpisodeDetailViewModel
import androidx.compose.runtime.getValue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EpisodeDetailScreen(
    episodeId: String,
    viewModel: EpisodeDetailViewModel = hiltViewModel()
) {
    val episode by viewModel.episode.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Episode Detail") })
        }
    ) { padding ->
        episode?.let {
            Column(
                modifier = Modifier
                    .padding(padding)
                    .padding(16.dp)
            ) {
                Text(it.title, style = MaterialTheme.typography.headlineSmall)
                Spacer(modifier = Modifier.height(8.dp))
                Text(it.description, style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.height(16.dp))

                Button(onClick = { viewModel.play(it.audioUrl) }) {
                    Text("재생")
                }

                Button(onClick = { viewModel.stop() }) {
                    Text("정지")
                }
            }
        } ?: CircularProgressIndicator(modifier = Modifier.padding(16.dp))
    }
}