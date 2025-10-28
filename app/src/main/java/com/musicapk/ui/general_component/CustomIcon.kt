package com.musicapk.ui.general_component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.musicapk.ui.theme.AppColors
import com.musicapk.ui.theme.Dimens

@Composable
fun CustomIcon(
    icon: ImageVector,
    modifier: Modifier = Modifier,
    tint: Color = AppColors.White,
    onClick: (() -> Unit)? = null
) {
    Icon(
        imageVector = icon,
        contentDescription = "",
        modifier = modifier
            .size(Dimens.iconSizeDefault)
            .then(
                if (onClick != null) {
                    Modifier.clickable { onClick() }
                } else {
                    Modifier
                }
            ),
        tint = tint
    )
}