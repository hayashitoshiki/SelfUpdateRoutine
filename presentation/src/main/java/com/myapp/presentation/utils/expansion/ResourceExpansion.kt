package com.myapp.presentation.utils.expansion

/**
 * Resource拡張用ファイル
 */

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.myapp.domain.model.value.AlarmMode
import com.myapp.domain.model.value.HeartScore
import com.myapp.presentation.R

/**
 * 活性状態に応じた白系のテキストカラーを返す
 *
 * @param enable 活性・非活性
 * @return テキストカラー
 */
fun getLightTextColorByEnable(enable: Boolean) : Int {
    return if (enable) {
        R.color.text_color_light_secondary
    } else {
        R.color.text_color_light_primary
    }
}
/**
 * 今日の天気画像
 */
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

/**
 * 今日の天気テキスト
 */
@get: StringRes
val HeartScore.text: Int
    get() = when (this.data) {
        in 0..20 -> R.string.weather_rain
        in 21..40 -> R.string.weather_rain_and_cloudy
        in 41..60 -> R.string.weather_cloudy
        in 61..80 -> R.string.weather_cloudy_and_sunny
        in 81..100 -> R.string.weather_sunny
        else -> R.string.weather_cloudy
    }

/**
 * アラームモードの説明
 */
@get: StringRes
val AlarmMode.explanation: Int
    get() = when (this) {
        AlarmMode.NORMAL -> R.string.explanation_alarm_mode_normal
        AlarmMode.HARD -> R.string.explanation_alarm_mode_hard
    }

/**
 * アラームモードの説明
 */
@get: StringRes
val AlarmMode.text: Int
    get() = when (this) {
        AlarmMode.NORMAL -> R.string.radio_btn_alarm_mode_normal
        AlarmMode.HARD -> R.string.radio_btn_alarm_mode_hard
    }
