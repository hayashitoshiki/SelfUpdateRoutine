package com.myapp.presentation.ui.home

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable


/**
 * 振り返り_事実画面
 *
 */
@ExperimentalMaterialApi
@Composable
fun StatementListScreen(statementList: List<ReportDetail>) {
    ReportDetailListContent(statementList)
}