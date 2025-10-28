package com.musicapk.presentation.songs.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.PlaylistPlay
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.musicapk.R
import com.musicapk.domain.model.Playlist
import com.musicapk.ui.theme.styles.gradientScreenBackground
import com.musicapk.ui.theme.AppColors
import com.musicapk.ui.theme.Dimens
import com.musicapk.ui.theme.FontSizes
import com.musicapk.ui.theme.MusicApkTheme
import com.musicapk.ui.theme.Strings


@Composable
fun MainCategoryDetail(
    playlists: List<Playlist>,
    onPlayListClick: (Playlist) -> Unit,
    onCreatePlaylistClick: () -> Unit
) {
    LazyRow {
        // Add playlist button
        item {
            CreatePlaylistItem(
                onClick = onCreatePlaylistClick
            )
        }
        
        // Show existing playlists
        items(playlists) { playlist ->
            MainCategoryDetailItem(
                playlist = playlist,
                onPlayListClick = { onPlayListClick(playlist) }
            )
        }
        
        // Show message if no playlists
        if (playlists.isEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .width(200.dp)
                        .padding(Dimens.paddingMedium)
                ) {
                    Text(
                        text = Strings.noPlaylists,
                        color = AppColors.LightGray,
                        fontSize = FontSizes.small,
                        fontFamily = FontFamily.SansSerif
                    )
                }
            }
        }
    }
}

@Composable
fun CreatePlaylistItem(
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .clickable { onClick() }
            .padding(end = Dimens.paddingMedium),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .width(Dimens.mainCategoryImgWidth)
                .height(Dimens.mainCategoryImgHeight)
                .clip(RoundedCornerShape(Dimens.cornerRadiusMedium))
                .border(
                    width = 2.dp,
                    color = AppColors.White,
                    shape = RoundedCornerShape(Dimens.cornerRadiusMedium)
                )
                .background(AppColors.DarkGray.copy(alpha = 0.3f)),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = Strings.createPlaylist,
                    modifier = Modifier.size(48.dp),
                    tint = AppColors.White
                )
                Text(
                    text = Strings.createPlaylist,
                    color = AppColors.White,
                    fontSize = FontSizes.small,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.SansSerif
                )
            }
        }
    }
}

@Composable
fun MainCategoryDetailItem(
    playlist: Playlist,
    onPlayListClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .clickable { onPlayListClick() }
            .padding(end = Dimens.paddingMedium)
    ) {
        // Playlist cover (using default image or first song artwork)
        Box(
            modifier = Modifier
                .width(Dimens.mainCategoryImgWidth)
                .height(Dimens.mainCategoryImgHeight)
                .clip(RoundedCornerShape(Dimens.cornerRadiusMedium))
                .background(AppColors.DarkGray.copy(alpha = 0.5f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.PlaylistPlay,
                contentDescription = playlist.name,
                modifier = Modifier.size(Dimens.iconSizeExtraLarge),
                tint = AppColors.White.copy(alpha = 0.7f)
            )
        }

        Text(
            modifier = Modifier
                .padding(
                    top = Dimens.paddingSmall,
                    start = Dimens.paddingSmall,
                ),
            text = playlist.name,
            color = AppColors.White,
            fontSize = FontSizes.medium,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.SansSerif,
            maxLines = 1
        )

        Text(
            modifier = Modifier
                .padding(start = Dimens.paddingSmall),
            text = playlist.description ?: "${playlist.songs.size} songs",
            color = AppColors.LightGray,
            fontSize = FontSizes.extraSmall,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.SansSerif,
            maxLines = 1
        )
    }
}

@Preview(
    name = "Main Category Detail Item",
    showBackground = true
)
@Composable
private fun MainCategoryDetailItemPreview() {
    MusicApkTheme(darkTheme = true, dynamicColor = false) {
        Box(
            modifier = Modifier
                .gradientScreenBackground()
                .padding(Dimens.paddingMedium)
        ) {
            val samplePlaylist = Playlist(
                id = "1",
                name = "R&B Playlist",
                description = "Chill your mind",
                songs = emptyList(),
                createdAt = 0L,
                updatedAt = 0L
            )
            MainCategoryDetailItem(
                playlist = samplePlaylist,
                onPlayListClick = {}
            )
        }
    }
}

@Preview(
    name = "Main Category Detail List",
    showBackground = true
)
@Composable
private fun MainCategoryDetailPreview() {
    MusicApkTheme(darkTheme = true, dynamicColor = false) {
        Box(
            modifier = Modifier
                .gradientScreenBackground()
                .padding(Dimens.paddingMedium)
        ) {
            val samplePlaylists = listOf(
                Playlist(
                    id = "1",
                    name = "R&B Playlist",
                    description = "Chill your mind",
                    songs = emptyList(),
                    createdAt = 0L,
                    updatedAt = 0L
                ),
                Playlist(
                    id = "2",
                    name = "Workout Mix",
                    description = null,
                    songs = emptyList(),
                    createdAt = 0L,
                    updatedAt = 0L
                )
            )
            MainCategoryDetail(
                playlists = samplePlaylists,
                onPlayListClick = {},
                onCreatePlaylistClick = {}
            )
        }
    }
}