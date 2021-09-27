package com.myapp.presentation.ui.home

import com.myapp.domain.model.value.ReportDateTime
import java.io.Serializable

/**
 * レポート項目別リスト
 *
 * @property statementList レポート詳細リスト
 */
data class ReportDetailList(
    val statementList: List<ReportDetail>
) : Serializable

/**
 * レポート項目別詳細
 *
 * @property value 項目内容
 * @property date 登録時付
 */
data class ReportDetail(
    val value: String,
    val date: ReportDateTime
) : Serializable
