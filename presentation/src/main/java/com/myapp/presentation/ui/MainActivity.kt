package com.myapp.presentation.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import androidx.navigation.ui.AppBarConfiguration
import com.myapp.domain.usecase.AlarmNotificationUseCase
import com.myapp.presentation.ui.diary.MainAppNavHost
import com.myapp.presentation.ui.diary.Screens
import com.myapp.presentation.ui.remember.RememberViewModel
import com.myapp.presentation.utils.component.BackButtonAppBar
import com.myapp.presentation.utils.component.Drawer
import com.myapp.presentation.utils.component.MenuButtonAppBar
import com.myapp.presentation.utils.theme.ComposeSampleTheme
import com.myapp.presentation.utils.theme.backgroundColor
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.components.ActivityComponent
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @EntryPoint
    @InstallIn(ActivityComponent::class)
    interface ViewModelFactoryProvider {
        fun noteDetailViewModelFactory(): RememberViewModel.Factory
    }

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