package com.musicapk.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Songs : Screen("songs")
    object PlayList : Screen("playList/{playlistId}") {
        fun createRoute(playlistId: String) = "playList/$playlistId"
    }
    object Player : Screen("players")
}



















