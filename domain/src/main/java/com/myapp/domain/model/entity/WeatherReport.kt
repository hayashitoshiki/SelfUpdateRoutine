package com.myapp.domain.model.entity

import com.myapp.domain.model.value.HeartScore
import com.myapp.domain.model.value.ReportDateTime

/**
 * 感情日記
 *
 * @property dataTime 日付
 * @property heartScore 今日の天気（感情）
 * @property reasonComment その理由
 * @property improveComment 明日への改善点
 */
data class WeatherReport(
    val dataTime: ReportDateTime,
    val heartScore: HeartScore,
    val reasonComment: String,
    val improveComment: String
)
