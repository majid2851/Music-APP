package com.musicapk.ui.theme.styles

import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import com.musicapk.ui.theme.Dimens

fun Modifier.screenPaddings(): Modifier = this
    .padding(
        end = Dimens.paddingMedium,
        start = Dimens.paddingMedium,
        top = Dimens.paddingExtraLarge,
    )