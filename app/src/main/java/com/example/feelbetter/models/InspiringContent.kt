package com.example.feelbetter.models

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class InspiringContent(
//    @StringRes val resourceId: Int,
    @StringRes val titleResourceId: Int,
    @StringRes val descResourceId: Int,
    @DrawableRes val imageResourceId: Int,
) {
}