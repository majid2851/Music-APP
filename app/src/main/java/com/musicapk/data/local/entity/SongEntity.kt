package com.musicapk.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "songs")
data class SongEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val artist: String,
    val album: String,
    val duration: Long,
    val artworkUri: String?,
    val audioUri: String,
    val dateAdded: Long,
    val isFavorite: Boolean = false
)

