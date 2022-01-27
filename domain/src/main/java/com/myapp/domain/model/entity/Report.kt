package com.myapp.domain.model.entity

import kotlinx.serialization.Serializable

/**
 * レポート
 *
 * @property ffsReport FFS式４行日記
 * @property weatherReport 感情日記
 */
@Serializable
data class Report(
    val ffsReport: FfsReport,
    val weatherReport: WeatherReport
)
