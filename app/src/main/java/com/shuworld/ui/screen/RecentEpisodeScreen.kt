package com.shuworld.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.shuworld.viewModel.RecentEpisodeViewModel
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecentEpisodeScreen(
    viewModel: RecentEpisodeViewModel = hiltViewModel(),
    onEpisodeClick: (String) -> Unit
) {
    val recentEpisodes by viewModel.recentEpisodes.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("최근 재생 목록") })
        }
    ) { padding ->
        LazyColumn(modifier = Modifier.padding(padding)) {
            items(recentEpisodes) { episode ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onEpisodeClick(episode.audioUrl) }
                        .padding(16.dp)
                ) {
                    Text(text = episode.title, style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = episode.description, style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}