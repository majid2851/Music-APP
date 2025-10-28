package com.musicapk.presentation.player.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.QueueMusic
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import com.musicapk.ui.theme.AppColors
import com.musicapk.ui.theme.Dimens
import com.musicapk.ui.theme.Strings
import com.musicapk.ui.theme.styles.gradientScreenBackground

@Composable
fun SongImage(
    artworkUri: String?,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(Dimens.songPlayerImageHeight)
            .fillMaxWidth()
            .clip(RoundedCornerShape(Dimens.cornerRadiusMedium))
            .background(AppColors.DarkGray.copy(alpha = 0.3f)),
        contentAlignment = Alignment.Center
    ) {
        if (artworkUri != null) {
            AsyncImage(
                model = artworkUri,
                contentDescription = Strings.songArtwork,
                modifier = Modifier
                    .height(Dimens.songPlayerImageHeight)
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop
            )
        } else {
            Icon(
                imageVector = Icons.Default.MusicNote,
                contentDescription = Strings.songArtwork,
                modifier = Modifier.size(Dimens.iconSizeXXL),
                tint = AppColors.White.copy(alpha = 0.5f)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SongImagePreview() {
    SongImage(
        artworkUri = null,
        modifier = Modifier
            .gradientScreenBackground()
            .padding(Dimens.paddingMedium)
    )
}