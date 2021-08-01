package com.myapp.presentation.utill

import androidx.annotation.DrawableRes
import com.myapp.domain.model.value.HeartScore
import com.myapp.presentation.R


@get: DrawableRes
val HeartScore.img: Int
    get() = when (this.data) {
        in 0..20 -> R.drawable.ic_rain_96dp
        in 21..40 -> R.drawable.ic_rain_and_cloudy_96dp
        in 41..60 -> R.drawable.ic_cloudy_96dp
        in 61..80 -> R.drawable.ic_cloudy_and_sunny_96dp
        in 81..100 -> R.drawable.ic_sunny_96dp
        else -> R.drawable.ic_cloudy_96dp
    }
