package com.myapp.presentation.ui.home

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.myapp.presentation.ui.remember.RememberViewModel
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
fun StatementListScreen(
    navController: NavHostController
) {
    val statementList = listOf<ReportDetail>()
    ReportDetailListContent(statementList)
}