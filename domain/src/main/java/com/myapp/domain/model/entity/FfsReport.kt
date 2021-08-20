package com.myapp.domain.model.entity

import com.myapp.domain.model.value.ReportDateTime

/**
 * FFS式４行日記
 *
 * @property dataTime 日付
 * @property factComment 事象
 * @property findComment 発見
 * @property learnComment 学び
 * @property statementComment 宣言
 */
data class FfsReport(
    val dataTime: ReportDateTime,
    val factComment: String,
    val findComment: String,
    val learnComment: String,
    val statementComment: String
)