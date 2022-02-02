package com.myapp.presentation.ui

import android.app.Activity
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import com.myapp.domain.model.entity.Report
import com.myapp.presentation.R
import com.myapp.presentation.ui.account.AccountScreen
import com.myapp.presentation.ui.account.SignInScreen
import com.myapp.presentation.ui.account.SignUpScreen
import com.myapp.presentation.ui.diary.*
import com.myapp.presentation.ui.home.*
import com.myapp.presentation.ui.remember.RememberScreen
import com.myapp.presentation.ui.remember.RememberViewModel
import com.myapp.presentation.ui.setting.SettingScreen
import dagger.hilt.android.EntryPointAccessors
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

/**
 * 画面定義
 *
 * @property route 遷移パス
 * @property resourceId ナビゲーションタイトル
 */
sealed class Screens(
    val route: String,
    @StringRes val resourceId: Int
) {

    // DiaryActivity

    object FfsFindScreen : Screens("find_screen_navigate", R.string.title_item_short_find)
    object FfsFactScreen : Screens("ffs_fact_screen_navigate", R.string.title_item_short_fact)
    object FfsLeanScreen : Screens( "ffs_lean_screen_navigate", R.string.title_item_short_learn)
    object FfsStatementScreen : Screens("ffs_statement_screen_navigate", R.string.title_item_short_statement)
    object FfsResultScreen : Screens("ffs_result_screen_navigate", R.string.title_item_short_ffs_result)
    object FfsAssessmentScreen : Screens("weather_assessment_screen_navigate", R.string.title_item_short_fact)
    object WeatherReasonScreen : Screens("weather_reason_screen_navigate", R.string.title_item_short_learn)
    object WeatherImproveScreen : Screens("weather_improve_screen_navigate", R.string.title_item_short_statement)
    object WeatherResultScreen : Screens("weather_result_screen_navigate", R.string.title_item_short_wather_result)

    // MainActivity

    sealed class DrawerScreens(
        route: String,
        @StringRes resourceId: Int,
        @DrawableRes val icon: Int
    ) : Screens(route, resourceId) {
        object HomeScreen : DrawerScreens("home_screen_navigate", R.string.menu_home, R.drawable.ic_home_48)
        object SettingScreen : DrawerScreens("setting_navigate", R.string.menu_setting, R.drawable.ic_menu_settings_48)
        object AccountScreen : DrawerScreens("account_navigate", R.string.menu_account, R.drawable.ic_account_48)
    }

    object StatementListScreen : Screens("statement_list_screen_navigate", R.string.menu_constitution_statement_list)
    object LearnListScreen : Screens("learn_screen_navigate", R.string.menu_constitution_learn_list)
    object ReportDetailScreen : Screens("report_detail_navigate", R.string.menu_remember)
    object SignInScreen : Screens("sign_in_navigate", R.string.menu_account)
    object SignUpScreen : Screens("sign_up_navigate", R.string.menu_account)

}

/**
 * 振り返り日記Activity用 NavigationHost
 *
 * @param navController ナビゲーションAPI
 */
@ExperimentalMaterialApi
@Composable
fun DiaryAppNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screens.FfsFactScreen.route
    ) {
        composable(route = Screens.FfsFactScreen.route) { FfsFactScreen(navController, hiltViewModel()) }
        composable(route = Screens.FfsFindScreen.route) { FfsFindScreen(navController, hiltViewModel()) }
        composable(route = Screens.FfsLeanScreen.route) { FfsLearnScreen(navController, hiltViewModel()) }
        composable(route = Screens.FfsStatementScreen.route) { FfsStatementScreen(navController, hiltViewModel()) }
        composable(route = Screens.FfsResultScreen.route) { FfsResultScreen(navController, hiltViewModel()) }
        composable(route = Screens.FfsAssessmentScreen.route) { WeatherAssessmentScreen(navController, hiltViewModel()) }
        composable(route = Screens.WeatherReasonScreen.route) { WeatherReasonScreen(navController, hiltViewModel()) }
        composable(route = Screens.WeatherImproveScreen.route) { WeatherImproveScreen(navController, hiltViewModel()) }
        composable(route = Screens.WeatherResultScreen.route) { WeatherResultScreen(hiltViewModel()) }
    }
}

/**
 * メインActivity用　NavigationHost
 *
 * @param navController ナビゲーションAPI
 */
@ExperimentalMaterialApi
@Composable
fun MainAppNavHost(navController: NavHostController, setScreen: (Screens) ->Unit) {
    NavHost(
        navController = navController,
        startDestination = Screens.DrawerScreens.HomeScreen.route
    ) {
        composable(route = Screens.DrawerScreens.HomeScreen.route) {
            setScreen(Screens.DrawerScreens.HomeScreen)
            HomeScreen(navController, hiltViewModel())
        }
        composable(
            route = Screens.StatementListScreen.route + "/{reportDetailList}",
            arguments = listOf(navArgument("reportDetailList") { type = NavType.StringType })
        ) { backStackEntry ->
            backStackEntry.arguments?.getString("reportDetailList")?.let{
                val reportList = Json.decodeFromString<List<ReportDetail>>(it)
                setScreen(Screens.StatementListScreen)
                StatementListScreen(reportList)
            }
        }
        composable(
            route = Screens.LearnListScreen.route + "/{reportDetailList}",
            arguments = listOf(navArgument("reportDetailList") { type = NavType.StringType })
        ) { backStackEntry ->
            backStackEntry.arguments?.getString("reportDetailList")?.let{
                val reportList = Json.decodeFromString<List<ReportDetail>>(it)
                setScreen(Screens.LearnListScreen)
                LearnListScreen(reportList)
            }
        }
        composable(
            route = Screens.ReportDetailScreen.route + "/{report}",
            arguments = listOf(navArgument("report") { type = NavType.StringType })
        ) { backStackEntry ->
            backStackEntry.arguments?.getString("report")?.let{
                val report = Json.decodeFromString<Report>(it)
                setScreen(Screens.ReportDetailScreen)
                RememberScreen(rememberViewModel(report))
            }
        }
        composable(route = Screens.DrawerScreens.SettingScreen.route) {
            setScreen(Screens.DrawerScreens.SettingScreen)
            SettingScreen(navController, hiltViewModel())
        }
        composable(route = Screens.DrawerScreens.AccountScreen.route) {
            setScreen(Screens.DrawerScreens.AccountScreen)
            AccountScreen(navController, hiltViewModel())
        }
        composable(route = Screens.SignInScreen.route) {
            setScreen(Screens.SignInScreen)
            SignInScreen(navController, hiltViewModel())
        }
        composable(route = Screens.SignUpScreen.route) {
            setScreen(Screens.SignUpScreen)
            SignUpScreen(navController, hiltViewModel())
        }
    }
}


@Composable
fun rememberViewModel(report: Report): RememberViewModel {
    val factory = EntryPointAccessors.fromActivity(
        LocalContext.current as Activity,
        MainActivity.ViewModelFactoryProvider::class.java
    ).noteDetailViewModelFactory()

    return viewModel(factory = RememberViewModel.provideFactory(factory, report))
}