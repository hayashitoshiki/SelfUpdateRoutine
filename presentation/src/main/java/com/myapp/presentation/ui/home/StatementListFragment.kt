package com.myapp.presentation.ui.home

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import dagger.hilt.android.AndroidEntryPoint

/**
 * 宣言一覧画面
 *
 */
@AndroidEntryPoint
class StatementListFragment : BaseDetailListFragment()

/**
 * 振り返り_事実画面
 *
 */
@ExperimentalMaterialApi
@Composable
fun StatementListScreen(statementList: List<ReportDetail>) {
    ReportDetailListContent(statementList)
}