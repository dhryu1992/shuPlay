package com.shuworld.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.shuworld.viewModel.PodcastViewModel
import com.shuworld.ui.component.PodcastItem
import androidx.compose.runtime.getValue
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.OutlinedTextField
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PodcastScreen(
    viewModel: PodcastViewModel = hiltViewModel(),
    navigateToDetail: (String) -> Unit
) {
    val searchQuery by viewModel.searchQuery.collectAsState()
    val podcastList by viewModel.podcastList.collectAsState()

    Scaffold(
        topBar = {
            Column {
                TopAppBar(title = { Text("ShuPlay Podcasts") })
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = viewModel::updateSearchQuery,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    placeholder = { Text("팟캐스트 검색") },
                    singleLine = true
                )
            }
        }
    ) { padding ->
        LazyColumn(contentPadding = padding) {
            items(podcastList) { podcast ->
                PodcastItem(podcast = podcast, onClick = {
                    navigateToDetail(podcast.id)
                })
            }
        }
    }
}