package com.myapp.presentation.ui.remember

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavHostController
import androidx.navigation.fragment.navArgs
import com.myapp.common.getDateTimeNow
import com.myapp.domain.model.entity.FfsReport
import com.myapp.domain.model.entity.Report
import com.myapp.domain.model.entity.WeatherReport
import com.myapp.domain.model.value.HeartScore
import com.myapp.domain.model.value.ReportDateTime
import com.myapp.presentation.R
import com.myapp.presentation.utils.component.ListMainDarkText
import com.myapp.presentation.utils.component.ListSectionDarkText
import com.myapp.presentation.utils.component.ListSubDarkText
import com.myapp.presentation.utils.expansion.text
import com.myapp.presentation.utils.theme.LightBlue50
import com.myapp.presentation.utils.theme.LightBlue100
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * 過去の振り返り詳細画面
 *
 */
@AndroidEntryPoint
class RememberFragment : Fragment() {
//
//    private val args: RememberFragmentArgs by navArgs()
    private val lastYearFfsReport = FfsReport(ReportDateTime(getDateTimeNow()), "fact", "find", "learn", "statement")
    private val lastYearWeatherReport = WeatherReport(ReportDateTime(getDateTimeNow()), HeartScore(50),"reason", "improve")
    private val report = Report(lastYearFfsReport, lastYearWeatherReport)


    @Inject
    lateinit var viewModelFactory: RememberViewModel.Factory
    private val viewModel: RememberViewModel by viewModels {
        RememberViewModel.provideFactory(viewModelFactory, report)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                val state = viewModel.state.value
                RememberScreenContent(state)
            }
        }
    }
}

/**
 * 振り返り_事実画面
 *
 */
@ExperimentalMaterialApi
@Composable
fun RememberScreen(
    navController: NavHostController,
    viewModel: RememberViewModel
) {
    val state = viewModel.state.value
    RememberScreenContent(state)
}

/**
 * 設定画面表示用コンテンツ
 *
 */
@Composable
private fun RememberScreenContent(
    state: RememberContract.State
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        backgroundColor = Color.Transparent
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Card(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                elevation = 8.dp
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    LightBlue50,
                                    LightBlue100
                                )
                            )
                        )
                        .padding(8.dp)
                    ) {
                    ListSectionDarkText(text = stringResource(id = R.string.title_report_ffs))
                    AnswerItem(
                        title = stringResource(id = R.string.question_fact),
                        value = state.factComment
                    )
                    AnswerItem(
                        title = stringResource(id = R.string.question_find),
                        value = state.findComment
                    )
                    AnswerItem(
                        title = stringResource(id = R.string.question_learn),
                        value = state.learnComment
                    )
                    AnswerItem(
                        title = stringResource(id = R.string.question_statement),
                        value = state.statementComment
                    )
                    ListSectionDarkText(
                        text = stringResource(id = R.string.title_report_emotions),
                        modifier = Modifier.padding(top = 16.dp)
                    )
                    AnswerItem(
                        title = stringResource(id = R.string.question_assessment),
                        value = stringResource(id = state.heartScoreComment.text)
                    )
                    AnswerItem(
                        title = stringResource(id = R.string.question_reason),
                        value = state.reasonComment
                    )
                    AnswerItem(
                        title = stringResource(id = R.string.question_improve),
                        value = state.improveComment
                    )
                }
            }
        }
    }
}

@Composable
private fun AnswerItem(
    title: String,
    value: String
) {
    Column {
        ListMainDarkText(
            text = title,
            modifier = Modifier.padding(top = 8.dp)
        )
        ListSubDarkText(
            text = value,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}