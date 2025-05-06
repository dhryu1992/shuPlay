package com.shuworld

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.shuworld.ui.screen.EpisodeDetailScreen
import com.shuworld.ui.screen.PlayerScreen
import com.shuworld.ui.screen.PodcastDetailScreen
import com.shuworld.ui.screen.PodcastScreen
import com.shuworld.ui.screen.RecentEpisodeScreen
import com.shuworld.ui.theme.ShuPlayTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ShuPlayTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = "podcast_list"
                ) {
                    composable("podcast_list") {
                        PodcastScreen(navigateToDetail = { podcastId ->
                            navController.navigate("podcast_detail/$podcastId")
                        })
                    }

                    composable("episode_detail/{episodeId}") { backStackEntry ->
                        val episodeId =
                            backStackEntry.arguments?.getString("episodeId") ?: return@composable
                        EpisodeDetailScreen(episodeId)
                    }

                    composable("episode_player/{episodeId}") { backStackEntry ->
                        val episodeId = backStackEntry.arguments?.getString("episodeId") ?: ""
                        PlayerScreen(episodeId = episodeId)
                    }

                    composable("podcast_detail/{podcastId}") { backStackEntry ->
                        val podcastId =
                            backStackEntry.arguments?.getString("podcastId") ?: return@composable
                        PodcastDetailScreen(podcastId = podcastId)
                    }

                    composable("recent_episodes") {
                        RecentEpisodeScreen { episodeId ->
                            navController.navigate("episode_player/$episodeId")
                        }
                    }
                }
            }
        }
    }
}