package com.myapp.domain.model.entity

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
    val emotionsReport: EmotionsReport
)