package com.myapp.domain.model.entity

import com.myapp.domain.model.value.HeartScore
import com.myapp.domain.model.value.ReportDateTime

/**
 * 感情日記
 */
class WeatherReport(
    /**
     * 日付
     */
    val dataTime: ReportDateTime,

    /**
     * 今日の天気（感情）
     */
    val heartScore: HeartScore,

    /**
     * その理由
     */
    val reasonComment: String,

    /**
     * 明日への改善点
     */
    val improveComment: String
)