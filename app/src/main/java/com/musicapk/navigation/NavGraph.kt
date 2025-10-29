package com.musicapk.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.musicapk.presentation.play_list.MusicPlayList
import com.musicapk.presentation.player.Player
import com.musicapk.presentation.splash.SplashScreen
import com.musicapk.presentation.songs.Songs
import android.app.Activity

@Composable
fun NavGraph(
    navController: NavHostController,
    startDestination: String = Screen.Splash.route,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val activity = context as? Activity
    
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(route = Screen.Splash.route) {
            SplashScreen(
                onGetStartedClick = {
                    navController.navigate(Screen.Songs.route) {
                        popUpTo(Screen.Splash.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(route = Screen.Songs.route) {
            Songs(
                onPlayListClick = { playlistId ->
                    navController.navigate(Screen.PlayList.createRoute(playlistId))
                },
                onSongClick = {
                    navController.navigate(Screen.Player.route)
                },
                onExitApp = {
                    activity?.finish()
                }
            )
        }
        
        composable(
            route = Screen.PlayList.route,
            arguments = listOf(
                navArgument("playlistId") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val playlistId = backStackEntry.arguments?.getString("playlistId") ?: ""
            MusicPlayList(
                playListId = playlistId,
                onSongClick = {
                    navController.navigate(Screen.Player.route)
                }
            )
        }

        composable(route=Screen.Player.route){
            Player(
                onBackClick = {
                    navController.navigateUp()
                }
            )
        }


    }
}

