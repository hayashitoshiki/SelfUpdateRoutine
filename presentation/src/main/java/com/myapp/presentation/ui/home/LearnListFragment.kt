package com.myapp.presentation.ui.home

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import dagger.hilt.android.AndroidEntryPoint

/**
 * 宣言一覧画面
 *
 */
@AndroidEntryPoint
class LearnListFragment : BaseDetailListFragment()


/**
 * 振り返り_事実画面
 *
 */
@ExperimentalMaterialApi
@Composable
fun LearnListScreen(
    navController: NavHostController
) {
    val statementList = listOf<ReportDetail>()
    ReportDetailListContent(statementList)
}
