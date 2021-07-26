package com.myapp.domain.model.entity

import com.myapp.domain.model.value.ReportDateTime

/**
 * FFS式４行日記
 */
class FfsReport(
    /**
     * 日付
     */
    val dataTime: ReportDateTime,

    /**
     * 事象
     */
    val factComment: String,

    /**
     * 発見
     */
    val findComment: String,

    /**
     * 学び
     */
    val learnComment: String,

    /**
     * 宣言
     */
    val statementComment: String
)