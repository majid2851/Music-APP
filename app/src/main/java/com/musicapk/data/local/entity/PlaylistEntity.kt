package com.musicapk.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "playlists")
data class PlaylistEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val description: String?,
    val createdAt: Long,
    val updatedAt: Long
)

