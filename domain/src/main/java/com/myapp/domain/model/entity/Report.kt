package com.myapp.domain.model.entity

import java.io.Serializable

/**
 * レポート
 */
data class Report(

    /**
     * FFS式４行日記
     */
    val ffsReport: FfsReport,

    /**
     * 感情日記
     */
    val weatherReport: WeatherReport
) : Serializable