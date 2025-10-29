package com.musicapk.data.mapper

import com.musicapk.data.local.entity.PlaylistEntity
import com.musicapk.data.local.entity.PlaylistWithSongs
import com.musicapk.domain.model.Playlist

fun PlaylistWithSongs.toDomain(): Playlist {
    return Playlist(
        id = playlist.id,
        name = playlist.name,
        description = playlist.description,
        songs = songs.toDomain(),
        createdAt = playlist.createdAt,
        updatedAt = playlist.updatedAt
    )
}

fun Playlist.toEntity(): PlaylistEntity {
    return PlaylistEntity(
        id = id,
        name = name,
        description = description,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}

fun List<PlaylistWithSongs>.toDomainPlaylists(): List<Playlist> = map { it.toDomain() }

