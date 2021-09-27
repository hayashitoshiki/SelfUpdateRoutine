package com.myapp.presentation.ui

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.AppLaunchChecker
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.myapp.domain.usecase.AlarmNotificationUseCase
import com.myapp.presentation.R
import com.myapp.presentation.databinding.ActivityMainBinding
import com.myapp.presentation.ui.diary.AlarmNotificationReceiver
import dagger.hilt.android.AndroidEntryPoint
import java.time.OffsetDateTime
import javax.inject.Inject
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var alarmNotificationUseCase: AlarmNotificationUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        if (AppLaunchChecker.hasStartedFromLauncher(this)) {
            Timber.tag(this.javaClass.simpleName)
                .d("２回目以降起動")
        } else {
            Timber.tag(this.javaClass.simpleName)
                .d("アプリ初回起動")
            setAlarmAndBack()
        }

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(
            R.id.nav_host_fragment_content_main
        )
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.nav_home, R.id.nav_setting, R.id.nav_constitution), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    // 通知バーアラーム表示設定
    private fun setAlarmAndBack() {
        val alarmManager = this.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, AlarmNotificationReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            this, AlarmNotificationReceiver.NOTIFICATION_ID, intent, PendingIntent.FLAG_UPDATE_CURRENT
        )
        val date = alarmNotificationUseCase.getNextAlarmDateTime()
        val alarmTimeMilli = date.toEpochSecond(OffsetDateTime.now().offset) * 1000
        val clockInfo = AlarmManager.AlarmClockInfo(alarmTimeMilli, null)
        alarmManager.setAlarmClock(clockInfo, pendingIntent)
        Timber.tag(this.javaClass.simpleName)
            .d("*******************")
        Timber.tag(this.javaClass.simpleName)
            .d("アラーム登録日時= %s", date)
        Timber.tag(this.javaClass.simpleName)
            .d("*******************")
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
