package com.myapp.presentation.ui.home

import android.content.Intent
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavHostController
import com.myapp.presentation.R
import com.myapp.presentation.ui.Screens
import com.myapp.presentation.ui.diary.*
import com.myapp.presentation.utils.component.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.serialization.*
import kotlinx.serialization.json.Json
import timber.log.Timber

/**
 * ホーム画面
 *
 */
@ExperimentalMaterialApi
@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: HomeViewModel
) {

    // effect
    val context = LocalContext.current
    val state = viewModel.state.value
    LaunchedEffect(true) {
        viewModel.effect.onEach { effect ->
            when (effect) {
                is HomeContract.Effect.ChangeFabEnable -> { }
                is HomeContract.Effect.DiaryReportNavigation -> {
                    val intent = Intent(context, DiaryActivity::class.java)
                    context.startActivity(intent)
                }
                is HomeContract.Effect.LearnListNavigation -> {
                    effect.value
                        .map{ ReportDetail(it.ffsReport.learnComment, it.ffsReport.dataTime.toMdDate()) }
                        .also {
                            val jsonList = Json.encodeToString(it)
                            navController.navigate(Screens.LearnListScreen.route +"/$jsonList")
                        }
                }
                is HomeContract.Effect.StatementListNavigation -> {
                    effect.value
                        .map{ ReportDetail(it.ffsReport.statementComment, it.ffsReport.dataTime.toMdDate()) }
                        .also{
                            val jsonList = Json.encodeToString(it)
                            navController.navigate(Screens.StatementListScreen.route +"/$jsonList")
                        }
                }
                is HomeContract.Effect.OnDestroyView -> {}
                is HomeContract.Effect.ShowError -> Timber.tag(this.javaClass.simpleName).d(effect.throwable.message)
            }
        }.collect()
    }


    HomeContent(viewModel, state)
}



@ExperimentalMaterialApi
@Composable
fun HomeContent(
    viewModel: HomeViewModel,
    state: HomeContract.State
) {
    ConstraintLayout(
        modifier = Modifier.fillMaxSize(),
    ) {
        val (header, fab, mainCard, bottomList)  = createRefs()
        // ヘッダー
        Row(
            modifier = Modifier.constrainAs(header) {
                top.linkTo(parent.top, margin = 8.dp)
                start.linkTo(parent.start, margin = 16.dp)
                end.linkTo(fab.start, margin = 8.dp)
            }
        ) {
            Column {
                HomeSubTitleText(text = stringResource(id = R.string.title_home_sub))
                HomeTitleText(
                    text = stringResource(id = R.string.title_home_head),
                    modifier = Modifier.padding(start = 4.dp)
                )
            }
        }
        // メインカード
        Column(
            modifier = Modifier
                .fillMaxSize()
                .constrainAs(mainCard) {
                    top.linkTo(header.bottom)
                    bottom.linkTo(bottomList.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    height = Dimension.fillToConstraints
                }
        ) {
            CommonCard(modifier = Modifier.padding(16.dp).fillMaxSize()) {
                when (state.mainContainerType) {
                    HomeFragmentMainContainerType.NotReport -> {
                        PrimaryColorButton(
                            onClick = { viewModel.setEvent(HomeContract.Event.OnClickReportButton) },
                            text = stringResource(id = R.string.bth_daly_input),
                        )
                    }
                    HomeFragmentMainContainerType.Report -> {
                        ListSubDarkText(text = stringResource(id = R.string.title_daily_mission))
                        ListMainDarkText(text = state.improve)
                    }
                    HomeFragmentMainContainerType.Vision -> {
                        SectionDarkText(text = "TODO : ミッションステートメントは今後削除")
                    }
                }
            }
        }
        // リスト
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(bottomList) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        ) {
            SectionDarkText(text = stringResource(id = R.string.title_report_list), modifier = Modifier.padding(start = 16.dp))
            if (state.reportList.isNotEmpty()) {
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                ) {
                    state.reportList.forEach { report ->
                        CommonCard(
                            modifier = Modifier.padding(16.dp),
                            onClick = { viewModel.setEvent(HomeContract.Event.OnClickReportCard(report)) }
                        ) {
                            ListSubDarkText(text = report.ffsReport.dataTime.toMdDate())
                            ListSectionDarkText(text = stringResource(id = R.string.title_item_short_learn))
                            ListMainDarkText(text = report.ffsReport.learnComment)
                            ListSectionDarkText(text = stringResource(id = R.string.title_item_short_statement))
                            ListMainDarkText(text = report.ffsReport.statementComment)
                        }
                    }
                }
            } else {
                MainLightText(text = stringResource(id = R.string.txt_no_report_list))
            }
        }

        // fabボタン
        Column(
            modifier = Modifier
                .constrainAs(fab) {
                    top.linkTo(header.top, margin = 8.dp)
                    end.linkTo(parent.end, margin = 8.dp)
                },
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (state.isFabVisibility) {
                FloatingActionButton(onClick = { viewModel.setEvent(HomeContract.Event.OnClickFabButton) }) {
                    Icon(painter = painterResource(id = R.drawable.ic_content_detail_24), contentDescription = "")
                }
                if (state.isFabCheck) {
                    FloatingActionButton(
                        onClick = { viewModel.setEvent(HomeContract.Event.OnClickFabStatementButton) },
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .size(40.dp)
                    ) {
                        Icon(painter = painterResource(id = R.drawable.ic_statement_20), contentDescription = "")
                    }
                    FloatingActionButton(
                        onClick = { viewModel.setEvent(HomeContract.Event.OnClickFabLearnButton) },
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .size(40.dp)
                    ) {
                        Icon(painter = painterResource(id = R.drawable.ic_learn_20), contentDescription = "")
                    }
                }
            }
        }
    }
}
