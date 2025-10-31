package com.musicapk.domain.model

data class Album(
    val id: String,
    val name: String,
    val artist: String,
    val artworkUri: String?,
    val songCount: Int,
    val songs: List<Song> = emptyList()
)









