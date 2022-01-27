package com.myapp.presentation.ui.home

import kotlinx.serialization.Serializable

/**
 * レポート項目別詳細
 *
 * @property value 項目内容
 * @property date 登録時付
 */
@Serializable
data class ReportDetail(
    val value: String,
    val date: String
)

