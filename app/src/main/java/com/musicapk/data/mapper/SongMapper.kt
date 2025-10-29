package com.musicapk.data.mapper

import com.musicapk.data.local.entity.SongEntity
import com.musicapk.domain.model.Song

fun SongEntity.toDomain(): Song {
    return Song(
        id = id,
        title = title,
        artist = artist,
        album = album,
        duration = duration,
        artworkUri = artworkUri,
        audioUri = audioUri,
        dateAdded = dateAdded,
        isFavorite = isFavorite
    )
}

fun Song.toEntity(): SongEntity {
    return SongEntity(
        id = id,
        title = title,
        artist = artist,
        album = album,
        duration = duration,
        artworkUri = artworkUri,
        audioUri = audioUri,
        dateAdded = dateAdded,
        isFavorite = isFavorite
    )
}

fun List<SongEntity>.toDomain(): List<Song> = map { it.toDomain() }
fun List<Song>.toEntity(): List<SongEntity> = map { it.toEntity() }

