package com.myapp.presentation.ui.diary

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.myapp.presentation.utils.theme.ComposeSampleTheme
import dagger.hilt.android.AndroidEntryPoint

/**
 * 振り返り画面 Activity
 */
@AndroidEntryPoint
class DiaryActivity : AppCompatActivity() {

    private val ffsFactViewModel: FfsFactViewModel by viewModels()
    private val ffsFindViewModel: FfsFindViewModel by viewModels()
    private val ffsLearnViewModel: FfsLearnViewModel by viewModels()
    private val ffsStatementViewModel: FfsStatementViewModel by viewModels()
    private val ffsResultViewModel: FfsResultViewModel by viewModels()
    private val weatherAssessmentViewModel: WeatherAssessmentViewModel by viewModels()
    private val weatherReasonViewModel: WeatherReasonViewModel by viewModels()
    private val weatherImproveViewModel: WeatherImproveViewModel by viewModels()
    private val weatherResultViewModel: WeatherResultViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ComposeSampleTheme {
                ComposeBaseApp(
                    ffsFactViewModel, ffsFindViewModel, ffsLearnViewModel, ffsStatementViewModel, ffsResultViewModel,
                    weatherAssessmentViewModel, weatherReasonViewModel, weatherImproveViewModel, weatherResultViewModel
                )
            }
        }
    }
}

/**
 * ベース画面
 *
 */
@Composable
fun ComposeBaseApp(
    ffsFactViewModel: FfsFactViewModel,
    ffsFindViewModel: FfsFindViewModel,
    ffsLearnViewModel: FfsLearnViewModel,
    ffsStatementViewModel: FfsStatementViewModel,
    ffsResultViewModel: FfsResultViewModel,
    weatherAssessmentViewModel: WeatherAssessmentViewModel,
    weatherReasonViewModel: WeatherReasonViewModel,
    weatherImproveViewModel: WeatherImproveViewModel,
    weatherResultViewModel: WeatherResultViewModel
) {
    ComposeSampleTheme {
        val navController = rememberNavController()
        Scaffold(backgroundColor = Color(0xfff5f5f5)) {
            AppNavHost(
                navController, ffsFactViewModel, ffsFindViewModel, ffsLearnViewModel, ffsStatementViewModel, ffsResultViewModel,
                weatherAssessmentViewModel, weatherReasonViewModel, weatherImproveViewModel, weatherResultViewModel
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeSampleTheme {
        DiaryActivity()
    }
}
