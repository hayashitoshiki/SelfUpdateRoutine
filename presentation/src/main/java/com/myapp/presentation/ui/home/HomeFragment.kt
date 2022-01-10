package com.myapp.presentation.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavHostController
import com.myapp.presentation.R
import com.myapp.presentation.ui.diary.*
import com.myapp.presentation.utils.component.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import timber.log.Timber

/**
 * ホーム画面
 */
@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels()

    @ExperimentalMaterialApi
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                val state = viewModel.state.value
                LaunchedEffect(true) {
                    viewModel.effect.onEach { effect ->
                        when (effect) {
                            is HomeContract.Effect.ChangeFabEnable -> { }
                            is HomeContract.Effect.DiaryReportNavigation -> {
                                val intent = Intent(context, DiaryActivity::class.java)
                                startActivity(intent)
                            }
                            is HomeContract.Effect.LearnListNavigation -> {
                                effect.value
                                    .map{ ReportDetail(it.ffsReport.learnComment, it.ffsReport.dataTime) }
                                    .let{ ReportDetailList(it)}
//                                    .let{ HomeFragmentDirections.actionNavHomeToNavLearnList(it) }
//                                    .let{ findNavController().navigate(it) }
                            }
                            is HomeContract.Effect.StatementListNavigation -> {
                                effect.value
                                    .map{ ReportDetail(it.ffsReport.statementComment, it.ffsReport.dataTime) }
                                    .let{ ReportDetailList(it) }
//                                    .let{ HomeFragmentDirections.actionNavHomeToNavStatementList(it) }
//                                    .let{ findNavController().navigate(it) }
                            }
                            is HomeContract.Effect.ReportDetailListNavigation -> {
                                effect.value
//                                    .let{ HomeFragmentDirections.actionNavHomeToNavRememner(it) }
//                                    .let{ findNavController().navigate(it) }
                            }
                            is HomeContract.Effect.OnDestroyView -> {}
                            is HomeContract.Effect.ShowError -> Timber.tag(this.javaClass.simpleName).d(effect.throwable.message)
                        }
                    }.collect()
                }
                HomeContent(viewModel, state)
            }
        }
    }

    override fun onDestroyView() {
        viewModel.setEvent(HomeContract.Event.OnDestroyView)
        super.onDestroyView()
    }
}

/**
 * 振り返り_事実画面
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
                 //   val intent = Intent(context, DiaryActivity::class.java)
                 //   startActivity(intent)
                }
                is HomeContract.Effect.LearnListNavigation -> {
                    effect.value
                        .map{ ReportDetail(it.ffsReport.learnComment, it.ffsReport.dataTime) }
                        .let{ ReportDetailList(it)}
//                        .let{ HomeFragmentDirections.actionNavHomeToNavLearnList(it) }
//                        .let{ navController.navigate(Screens.REPORT_DETAIL_SCREEN.route) }
                }
                is HomeContract.Effect.StatementListNavigation -> {
                    effect.value
                        .map{ ReportDetail(it.ffsReport.statementComment, it.ffsReport.dataTime) }
                        .let{ ReportDetailList(it) }
//                        .let{ HomeFragmentDirections.actionNavHomeToNavStatementList(it) }
//                        .let{ navController.navigate(Screens.STATEMENT_LIST_SCREEN.route) }
                }
                is HomeContract.Effect.ReportDetailListNavigation -> {
                    effect.value
//                        .let{ HomeFragmentDirections.actionNavHomeToNavRememner(it) }
//                        .let{ navController.navigate(Screens.LEARN_LIST_SCREEN.route) }
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
            Card(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                elevation = 8.dp
            ) {
                Column(modifier = Modifier
                    .padding(8.dp)
                    .fillMaxSize()) {
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
                        Card(
                            modifier = Modifier.padding(16.dp),
                            elevation = 8.dp,
                            onClick = { viewModel.setEvent(HomeContract.Event.OnClickReportCard(report))}
                        ) {
                            Column(modifier = Modifier.padding(8.dp)) {
                                ListSubDarkText(text = report.ffsReport.dataTime.toMdDate())
                                ListSectionDarkText(text = stringResource(id = R.string.title_item_short_learn))
                                ListMainDarkText(text = report.ffsReport.learnComment)
                                ListSectionDarkText(text = stringResource(id = R.string.title_item_short_statement))
                                ListMainDarkText(text = report.ffsReport.statementComment)
                            }
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


@Composable
fun Drawer(
    modifier: Modifier = Modifier,
    onDestinationClicked: (route: String) -> Unit
) {
    Column(
        modifier
            .fillMaxSize()
            .padding(start = 24.dp, top = 48.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.ic_menu_camera),
            contentDescription = "App icon"
        )
    }
}