package com.musicapk.presentation.play_list.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.musicapk.R
import com.musicapk.presentation.player.component.BackAndMoreIcons
import com.musicapk.ui.general_component.CircleGradientIcon
import com.musicapk.ui.general_component.CustomIcon
import com.musicapk.ui.theme.AppColors
import com.musicapk.ui.theme.Dimens
import com.musicapk.ui.theme.FontSizes
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.musicapk.presentation.songs.SongsViewModel

@Composable
fun SelectedPlayList(
    playlistId: String,
    viewModel: SongsViewModel = hiltViewModel()
) {
    val playlists by viewModel.uiState.collectAsState()
    val playlist = playlists.playlists.find { it.id == playlistId }
    
    val playlistName = playlist?.name ?: "Unknown Playlist"
    val songCount = playlist?.songs?.size ?: 0
    val artists = playlist?.songs?.map { it.artist }?.distinct()?.take(3)?.joinToString(", ") ?: "Unknown Artist"
    Column(
        modifier = Modifier
            .fillMaxWidth()
    )
    {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimens.paddingLarge)
        ) {
            Text(
                text = playlistName,
                fontSize = FontSizes.large,
                fontWeight = FontWeight.SemiBold,
                fontFamily = FontFamily.SansSerif,
                color = AppColors.White,
            )

            Text(
                text = if (songCount > 0) "$songCount songs • $artists" else "No songs",
                fontSize = FontSizes.small,
                fontWeight = FontWeight.Medium,
                fontFamily = FontFamily.SansSerif,
                color = AppColors.DarkGray,
            )


        }


        Divider()


    }


}

@Preview(
    name = "Selected Music Overview",
    showBackground = true,
    backgroundColor = 0xFF1C1B1B
)
@Composable
private fun SelectedMusicOverviewPreview() {
    SelectedPlayList(playlistId = "")
}
