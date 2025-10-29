package com.musicapk.presentation.songs.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.musicapk.presentation.songs.model.MainTabEnum
import com.musicapk.ui.theme.styles.gradientButtonBackground
import com.musicapk.ui.theme.styles.gradientScreenBackground
import com.musicapk.ui.theme.AppColors
import com.musicapk.ui.theme.Dimens
import com.musicapk.ui.theme.FontSizes
import com.musicapk.ui.theme.MusicApkTheme

@Composable
fun MainTabs(
    selectedTab: MainTabEnum = MainTabEnum.PLAYLISTS,
    onTabSelected: (MainTabEnum) -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())
            .padding(
                top = Dimens.paddingLarge,
                bottom = Dimens.paddingMedium,
            ),
        horizontalArrangement = Arrangement.spacedBy(Dimens.paddingMedium)
    ) {
        MainTabEnum.entries.forEach { tab ->
            MainTabItem(
                title = tab.title,
                isSelected = tab == selectedTab,
                onClick = { onTabSelected(tab) }
            )
        }
    }
}

@Composable
private fun MainTabItem(
    title: String,
    isSelected: Boolean = false,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clickable { onClick() }
            .padding(horizontal = Dimens.paddingSmall),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = title,
            modifier = Modifier.padding(
                bottom = Dimens.paddingSmall
            ),
            fontSize = FontSizes.medium,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.SemiBold,
            color = if (isSelected) AppColors.White else AppColors.LightGray,
            fontFamily = FontFamily.SansSerif,
        )

        Box(
            modifier = Modifier
                .width(80.dp)
                .height(Dimens.dividerNormalThickness)
                .then(
                    if (isSelected) {
                        Modifier.gradientButtonBackground()
                    } else {
                        Modifier
                    }
                )
        )
    }
}

@Preview(
    name = "Main Tabs",
    showBackground = true
)
@Composable
private fun MainTabsPreview() {
    MusicApkTheme(darkTheme = true, dynamicColor = false) {
        Box(
            modifier = Modifier
                .gradientScreenBackground()
                .padding(Dimens.paddingMedium)
        ) {
            MainTabs(
                selectedTab = MainTabEnum.PLAYLISTS,
                onTabSelected = {}
            )
        }
    }
}

