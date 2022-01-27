package com.myapp.presentation.ui.diary

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
import com.myapp.presentation.ui.MainActivity
import com.myapp.presentation.ui.account.AccountScreen
import com.myapp.presentation.ui.account.SignInScreen
import com.myapp.presentation.ui.account.SignUpScreen
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

    object FFS_FIND_SCREEN : Screens("find_screen_navigate", R.string.title_item_short_find)
    object FFS_FACT_SCREEN : Screens("ffs_fact_screen_navigate", R.string.title_item_short_fact)
    object FFS_LEARN_SCREEN : Screens( "ffs_learn_screen_navigate", R.string.title_item_short_learn)
    object FFS_STATEMENT_SCREEN : Screens("ffs_statement_screen_navigate", R.string.title_item_short_statement)
    object FFS_RESULT_SCREEN : Screens("ffs_result_screen_navigate", R.string.title_item_short_ffs_result)
    object WEATHER_ASSESSMENT_SCREEN : Screens("weather_assessment_screen_navigate", R.string.title_item_short_fact)
    object WEATHER_REASON_SCREEN : Screens("weather_reason_screen_navigate", R.string.title_item_short_learn)
    object WEATHER_IMPROVE_SCREEN : Screens("weather_improve_screen_navigate", R.string.title_item_short_statement)
    object WEATHER_RESULT_SCREEN : Screens("weather_result_screen_navigate", R.string.title_item_short_wather_result)

    // MainActivity

    sealed class DrawerScreens(
        route: String,
        @StringRes resourceId: Int,
        @DrawableRes val icon: Int
    ) : Screens(route, resourceId) {
        object HOME_SCREEN : DrawerScreens("home_screen_navigate", R.string.menu_home, R.drawable.ic_home_48)
        object SETTING_SCREEN : DrawerScreens("setting_navigate", R.string.menu_setting, R.drawable.ic_menu_settings_48)
        object ACCOUNT_SCREEN : DrawerScreens("account_navigate", R.string.menu_account, R.drawable.ic_account_48)
    }

    object STATEMENT_LIST_SCREEN : Screens("statement_list_screen_navigate", R.string.menu_constitution_statement_list)
    object LEARN_LIST_SCREEN : Screens("learn_screen_navigate", R.string.menu_constitution_learn_list)
    object REPORT_DETAIL_SCREEN : Screens("report_detail_navigate", R.string.menu_remember)
    object SIGN_IN_SCREEN : Screens("sign_in_navigate", R.string.menu_account)
    object SIGN_UP_SCREEN : Screens("sign_up_navigate", R.string.menu_account)

}

/**
 * NavigationHost
 *
 * @param navController ナビゲーションAPI
 */
@ExperimentalMaterialApi
@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(
        navController = navController, startDestination = Screens.FFS_FACT_SCREEN.route
    ) {
        composable(route = Screens.FFS_FACT_SCREEN.route) { FfsFactScreen(navController, hiltViewModel()) }
        composable(route = Screens.FFS_FIND_SCREEN.route) { FfsFindScreen(navController, hiltViewModel()) }
        composable(route = Screens.FFS_LEARN_SCREEN.route) { FfsLearnScreen(navController, hiltViewModel()) }
        composable(route = Screens.FFS_STATEMENT_SCREEN.route) { FfsStatementScreen(navController, hiltViewModel()) }
        composable(route = Screens.FFS_RESULT_SCREEN.route) { FfsResultScreen(navController, hiltViewModel()) }
        composable(route = Screens.WEATHER_ASSESSMENT_SCREEN.route) { WeatherAssessmentScreen(navController, hiltViewModel()) }
        composable(route = Screens.WEATHER_REASON_SCREEN.route) { WeatherReasonScreen(navController, hiltViewModel()) }
        composable(route = Screens.WEATHER_IMPROVE_SCREEN.route) { WeatherImproveScreen(navController, hiltViewModel()) }
        composable(route = Screens.WEATHER_RESULT_SCREEN.route) { WeatherResultScreen(hiltViewModel()) }
    }
}

/**
 * NavigationHost
 *
 * @param navController ナビゲーションAPI
 */
@ExperimentalMaterialApi
@Composable
fun MainAppNavHost(navController: NavHostController, setScreen: (Screens) ->Unit) {
    NavHost(
        navController = navController, startDestination = Screens.DrawerScreens.HOME_SCREEN.route
    ) {
        composable(route = Screens.DrawerScreens.HOME_SCREEN.route) {
            setScreen(Screens.DrawerScreens.HOME_SCREEN)
            HomeScreen(navController, hiltViewModel())
        }
        composable(
            route = Screens.STATEMENT_LIST_SCREEN.route + "/{reportDetailList}",
            arguments = listOf(navArgument("reportDetailList") { type = NavType.StringType })
        ) { backStackEntry ->
            backStackEntry.arguments?.getString("reportDetailList")?.let{
                val reportList = Json.decodeFromString<List<ReportDetail>>(it)
                setScreen(Screens.STATEMENT_LIST_SCREEN)
                StatementListScreen(reportList)
            }
        }
        composable(
            route = Screens.LEARN_LIST_SCREEN.route + "/{reportDetailList}",
            arguments = listOf(navArgument("reportDetailList") { type = NavType.StringType })
        ) { backStackEntry ->
            backStackEntry.arguments?.getString("reportDetailList")?.let{
                val reportList = Json.decodeFromString<List<ReportDetail>>(it)
                setScreen(Screens.LEARN_LIST_SCREEN)
                LearnListScreen(reportList)
            }
        }
        composable(
            route = Screens.REPORT_DETAIL_SCREEN.route + "/{report}",
            arguments = listOf(navArgument("report") { type = NavType.StringType })
        ) { backStackEntry ->
            backStackEntry.arguments?.getString("report")?.let{
                val report = Json.decodeFromString<Report>(it)
                setScreen(Screens.REPORT_DETAIL_SCREEN)
                RememberScreen(rememberViewModel(report))
            }
        }
        composable(route = Screens.DrawerScreens.SETTING_SCREEN.route) {
            setScreen(Screens.DrawerScreens.SETTING_SCREEN)
            SettingScreen(navController, hiltViewModel())
        }
        composable(route = Screens.DrawerScreens.ACCOUNT_SCREEN.route) {
            setScreen(Screens.DrawerScreens.ACCOUNT_SCREEN)
            AccountScreen(navController, hiltViewModel())
        }
        composable(route = Screens.SIGN_IN_SCREEN.route) {
            setScreen(Screens.SIGN_IN_SCREEN)
            SignInScreen(navController, hiltViewModel())
        }
        composable(route = Screens.SIGN_UP_SCREEN.route) {
            setScreen(Screens.SIGN_UP_SCREEN)
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