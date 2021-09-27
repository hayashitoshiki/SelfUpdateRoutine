package com.myapp.domain.dto

/**
 * 未確定の全ての振り返り日記データの受け渡し用Dto
 *
 * @property factComment  FFS式４行日記_事象
 * @property findComment FFS式４行日記_発見
 * @property learnComment FFS式４行日記_学び
 * @property statementComment FFS式４行日記_宣言
 * @property heartScore 感情日記_今日の天気（感情）
 * @property reasonComment 感情日記_その理由
 * @property improveComment 感情日記_明日への改善点
 */
class AllReportInputDto(
    val factComment: String,
    val findComment: String,
    val learnComment: String,
    val statementComment: String,
    val heartScore: Int,
    val reasonComment: String,
    val improveComment: String
)
