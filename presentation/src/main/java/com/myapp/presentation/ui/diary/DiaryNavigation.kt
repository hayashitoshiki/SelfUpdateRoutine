package com.myapp.presentation.ui.diary

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.myapp.presentation.R

/**
 * 画面定義
 *
 * @property group Bottomタブ
 * @property route 遷移パス
 * @property resourceId ナビゲーションタイトル
 * @property imgRes ナビゲーションアイコン
 */
enum class Screens(
    val group: Group,
    val route: String,
    @StringRes val resourceId: Int,
    val imgRes: ImageVector
) {

    FFS_FIND_SCREEN(
        Group.Diary, "find_screen_navigate", R.string.title_item_short_find, Icons.Filled.Home
    ),
    FFS_FACT_SCREEN(
        Group.Diary, "ffs_fact_screen_navigate", R.string.title_item_short_fact, Icons.Filled.Info
    ),
    FFS_LEARN_SCREEN(
        Group.Diary, "ffs_learn_screen_navigate", R.string.title_item_short_learn, Icons.Filled.Email
    ),
    FFS_STATEMENT_SCREEN(
        Group.Diary, "ffs_statement_screen_navigate", R.string.title_item_short_statement, Icons.Filled.Email
    ),
    FFS_RESULT_SCREEN(
        Group.Diary, "ffs_result_screen_navigate", R.string.title_item_short_ffs_result, Icons.Filled.Email
    ),
    WEATHER_ASSESSMENT_SCREEN(
        Group.Diary, "weather_assessment_screen_navigate", R.string.title_item_short_fact, Icons.Filled.Info
    ),
    WEATHER_REASON_SCREEN(
        Group.Diary, "weather_reason_screen_navigate", R.string.title_item_short_learn, Icons.Filled.Email
    ),
    WEATHER_IMPROVE_SCREEN(
        Group.Diary, "weather_improve_screen_navigate", R.string.title_item_short_statement, Icons.Filled.Email
    ),
    WEATHER_RESULT_SCREEN(
        Group.Diary, "weather_result_screen_navigate", R.string.title_item_short_wather_result, Icons.Filled.Email
    ), ;

    enum class Group {
        Diary;
    }

}

/**
 * NavigationHost
 *
 * @param navController ナビゲーションAPI
 */
@Composable
fun AppNavHost(
    navController: NavHostController,
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
    NavHost(
        navController = navController, startDestination = Screens.FFS_FACT_SCREEN.route
    ) {
        composable(route = Screens.FFS_FACT_SCREEN.route) { FfsFactScreen(navController, ffsFactViewModel) }
        composable(route = Screens.FFS_FIND_SCREEN.route) { FfsFindScreen(navController, ffsFindViewModel) }
        composable(route = Screens.FFS_LEARN_SCREEN.route) { FfsLearnScreen(navController, ffsLearnViewModel) }
        composable(route = Screens.FFS_STATEMENT_SCREEN.route) { FfsStatementScreen(navController, ffsStatementViewModel) }
        composable(route = Screens.FFS_RESULT_SCREEN.route) { FfsResultScreen(navController, ffsResultViewModel) }
        composable(route = Screens.WEATHER_ASSESSMENT_SCREEN.route) {
            WeatherAssessmentScreen(
                navController, weatherAssessmentViewModel
            )
        }
        composable(route = Screens.WEATHER_REASON_SCREEN.route) { WeatherReasonScreen(navController, weatherReasonViewModel) }
        composable(route = Screens.WEATHER_IMPROVE_SCREEN.route) {
            WeatherImproveScreen(
                navController, weatherImproveViewModel
            )
        }
        composable(route = Screens.WEATHER_RESULT_SCREEN.route) { WeatherResultScreen(weatherResultViewModel) }
    }
}
