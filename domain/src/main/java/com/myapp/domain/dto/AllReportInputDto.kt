package com.myapp.domain.dto

/**
 * 未確定の全ての振り返り日記データの受け渡し用Dto
 */
class AllReportInputDto(

    /**
     * FFS式４行日記_事象
     */
    val factComment: String,

    /**
     * FFS式４行日記_発見
     */
    val findComment: String,

    /**
     * FFS式４行日記_学び
     */
    val learnComment: String,

    /**
     * FFS式４行日記_宣言
     */
    val statementComment: String,

    /**
     * 感情日記_今日の天気（感情）
     */
    val heartScore: Int,

    /**
     * 感情日記_その理由
     */
    val reasonComment: String,

    /**
     * 感情日記_明日への改善点
     */
    val improveComment: String
)