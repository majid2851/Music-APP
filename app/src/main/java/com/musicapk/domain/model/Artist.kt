package com.musicapk.domain.model

data class Artist(
    val id: String,
    val name: String,
    val artworkUri: String?,
    val songCount: Int,
    val albumCount: Int,
    val songs: List<Song> = emptyList()
)







