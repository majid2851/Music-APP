package com.musicapk.domain.model

data class Song(
    val id: String,
    val title: String,
    val artist: String,
    val album: String,
    val duration: Long, // in milliseconds
    val artworkUri: String?,
    val audioUri: String,
    val dateAdded: Long,
    val isFavorite: Boolean = false
)


