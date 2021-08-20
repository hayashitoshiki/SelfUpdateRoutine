package com.myapp.domain.model.entity

import java.io.Serializable

/**
 * レポート
 *
 * @property ffsReport FFS式４行日記
 * @property weatherReport 感情日記
 */
data class Report(
    val ffsReport: FfsReport,
    val weatherReport: WeatherReport
) : Serializable