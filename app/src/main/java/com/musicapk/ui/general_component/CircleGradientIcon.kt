package com.musicapk.ui.general_component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import com.musicapk.ui.theme.AppColors
import com.musicapk.ui.theme.Dimens
import com.musicapk.ui.theme.styles.gradientButtonBackground

@Composable
fun CircleGradientIcon(
    icon: ImageVector,
    onClick: (() -> Unit)? = null
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(Dimens.cornerRadiusFull))
            .gradientButtonBackground()
            .size(Dimens.iconSizeLarge)
            .then(
                if (onClick != null) {
                    Modifier.clickable { onClick() }
                } else {
                    Modifier
                }
            )
    ) {
        Icon(
            imageVector = icon,
            contentDescription = "",
            modifier = Modifier
                .align(Alignment.Center)
                .size(Dimens.iconSizeDefault),
            tint = AppColors.White
        )
    }
}