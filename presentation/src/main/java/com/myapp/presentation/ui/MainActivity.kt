package com.myapp.presentation.ui

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import com.myapp.domain.usecase.AlarmNotificationUseCase
import com.myapp.presentation.R
import com.myapp.presentation.ui.account.SignInScreen
import com.myapp.presentation.ui.diary.AlarmNotificationReceiver
import com.myapp.presentation.ui.diary.AppNavHost
import com.myapp.presentation.ui.diary.MainAppNavHost
import com.myapp.presentation.ui.diary.Screens
import com.myapp.presentation.utils.component.BackButtonAppBar
import com.myapp.presentation.utils.component.Drawer
import com.myapp.presentation.utils.component.MenuButtonAppBar
import com.myapp.presentation.utils.theme.ComposeSampleTheme
import com.myapp.presentation.utils.theme.backgroundColor
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.time.OffsetDateTime
import javax.inject.Inject
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
//    private var _binding: ActivityMainBinding? = null
//    private val binding get() = _binding!!

    private val viewModel: MainViewModel by viewModels()
    @Inject
    lateinit var alarmNotificationUseCase: AlarmNotificationUseCase

    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ComposeSampleTheme {
                ComposeMainBaseApp(viewModel)
            }
        }


//        WindowCompat.setDecorFitsSystemWindows(window, false)
//        _binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//        val navViewHeaderBinding : NavHeaderMainBinding = DataBindingUtil.inflate(layoutInflater, R.layout.nav_header_main, binding.navView, false)
//        navViewHeaderBinding.viewModel = viewModel
//        navViewHeaderBinding.lifecycleOwner = this
//        setSupportActionBar(binding.appBarMain.toolbar)
//        binding.navView.addHeaderView(navViewHeaderBinding.root)
//        if (AppLaunchChecker.hasStartedFromLauncher(this)) {
//            Timber.tag(this.javaClass.simpleName)
//                .d("２回目以降起動")
//        } else {
//            Timber.tag(this.javaClass.simpleName)
//                .d("アプリ初回起動")
//            setAlarmAndBack()
//        }
//
//        val drawerLayout: DrawerLayout = binding.drawerLayout
//        val navView: NavigationView = binding.navView
//        val navController = findNavController(
//            R.id.nav_host_fragment_content_main
//        )
//        appBarConfiguration = AppBarConfiguration(
//            setOf(
//                R.id.nav_home,
//                R.id.nav_setting,
//                R.id.nav_account
//            ), drawerLayout
//        )
//        setupActionBarWithNavController(navController, appBarConfiguration)
//        navView.setupWithNavController(navController)
    }

//    override fun onSupportNavigateUp(): Boolean {
//        val navController = findNavController(R.id.nav_host_fragment_content_main)
//        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
//    }
//
//    // 通知バーアラーム表示設定
//    private fun setAlarmAndBack() {
//        val alarmManager = this.getSystemService(Context.ALARM_SERVICE) as AlarmManager
//        val intent = Intent(this, AlarmNotificationReceiver::class.java)
//        val pendingIntent = PendingIntent.getBroadcast(
//            this, AlarmNotificationReceiver.NOTIFICATION_ID, intent, PendingIntent.FLAG_UPDATE_CURRENT
//        )
//        val date = alarmNotificationUseCase.getNextAlarmDateTime()
//        val alarmTimeMilli = date.toEpochSecond(OffsetDateTime.now().offset) * 1000
//        val clockInfo = AlarmManager.AlarmClockInfo(alarmTimeMilli, null)
//        alarmManager.setAlarmClock(clockInfo, pendingIntent)
//        Timber.tag(this.javaClass.simpleName)
//            .d("*******************")
//        Timber.tag(this.javaClass.simpleName)
//            .d("アラーム登録日時= %s", date)
//        Timber.tag(this.javaClass.simpleName)
//            .d("*******************")
//    }

//    override fun onDestroy() {
//        super.onDestroy()
//        _binding = null
//    }
}

/**
 * ベース画面
 *
 */
@ExperimentalMaterialApi
@Composable
fun ComposeMainBaseApp(viewModel: MainViewModel) {
    ComposeSampleTheme {
        val navController = rememberNavController()
        val scope = rememberCoroutineScope()
        val scaffoldState = rememberScaffoldState()
        val currentScreen = viewModel.state.value.currentScreen
        // ヘッダー定義
        val topBar: @Composable () -> Unit = {
            when (currentScreen) {
                Screens.DrawerScreens.HOME_SCREEN -> {
                    MenuButtonAppBar(
                        title = "",
                        onButtonClicked = {
                            scope.launch {
                                scaffoldState.drawerState.open()
                            }
                        }
                    )
                }
                Screens.DrawerScreens.ACCOUNT_SCREEN, Screens.DrawerScreens.SETTING_SCREEN -> {
                    MenuButtonAppBar(
                        title = stringResource(id = currentScreen.resourceId),
                        onButtonClicked = {
                            scope.launch {
                                scaffoldState.drawerState.open()
                            }
                        }
                    )
                }
                else -> {
                    BackButtonAppBar(title = stringResource(id = currentScreen.resourceId), onButtonClicked = {
                        navController.popBackStack()
                    })
                }
            }
        }
        Scaffold(
            backgroundColor = backgroundColor,
            topBar = { topBar() },
            scaffoldState = scaffoldState,
            drawerContent = {
                Drawer { route ->
                    scope.launch {
                        scaffoldState.drawerState.close()
                    }
                    navController.navigate(route) {
                        popUpTo = navController.graph.startDestinationId
                        launchSingleTop = true
                    }
                }
            },
            drawerGesturesEnabled = scaffoldState.drawerState.isOpen,
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(it)
            ) {
                MainAppNavHost(navController) {
                    viewModel.setEvent(MainContract.Event.OnMoveScreen(it))
                }
            }
        }
    }
}


@Preview
@Composable
fun DrawerPreview() {
    ComposeSampleTheme {
        Drawer {}
    }
}