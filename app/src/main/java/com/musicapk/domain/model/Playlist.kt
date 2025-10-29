package com.musicapk.domain.model

data class Playlist(
    val id: String,
    val name: String,
    val description: String?,
    val songs: List<Song>,
    val createdAt: Long,
    val updatedAt: Long
)


