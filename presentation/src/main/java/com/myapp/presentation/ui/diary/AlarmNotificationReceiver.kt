package com.myapp.presentation.ui.diary

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import com.myapp.domain.usecase.AlarmNotificationUseCase
import com.myapp.domain.usecase.SettingUseCase
import com.myapp.presentation.R
import org.koin.java.KoinJavaComponent.inject
import timber.log.Timber
import java.time.OffsetDateTime
import java.util.*

/**
 * 通知バー表示
 */
class AlarmNotificationReceiver : BroadcastReceiver() {

    private val alarmNotificationUseCase by inject<AlarmNotificationUseCase>(AlarmNotificationUseCase::class.java)
    private val settingUseCase by inject<SettingUseCase>(SettingUseCase::class.java)

    companion object {
        const val NOTIFICATION_ID = 0
        const val CHANNEL_ID = "primary_notification_channel"
        const val CHANNEL_NAME = "Stand up notification"
    }

    private lateinit var notificationManager: NotificationManager

    override fun onReceive(
        context: Context,
        intent: Intent
    ) {
        Timber.tag(this.javaClass.simpleName)
            .d("通知バー用BroadcastReceiver実行")
        notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (alarmNotificationUseCase.checkAlarmNotificationEnable()) {
            createNotificationChannel()
            deliverNotification(context)
        }
        setNextAlarm(context)
    }

    // 通知表示
    private fun deliverNotification(context: Context) {
        val fullScreenIntent = Intent(context, DiaryActivity::class.java)
        val fullScreenPendingIntent = PendingIntent.getActivity(context, 0, fullScreenIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        val contentIntent = Intent(context, DiaryActivity::class.java)
        val contentPendingIntent =
            PendingIntent.getActivity(context, NOTIFICATION_ID, contentIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_sunny_96dp)
            .setContentTitle("日記")
            .setContentText("今日の振り返りを記入してください")
            .setContentIntent(contentPendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setFullScreenIntent(fullScreenPendingIntent, true)
            .setAutoCancel(true)
        notificationManager.notify(NOTIFICATION_ID, builder.build())
    }

    // チャンネル登録
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH).also {
                it.enableLights(true)
                it.lightColor = Color.RED
                it.enableVibration(true)
                it.description = "AlarmManager Tests"
            }
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    // 次回のアラーム設定
    private fun setNextAlarm(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmNotificationReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, NOTIFICATION_ID, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        val nextAlertTime = alarmNotificationUseCase.getNextAlarmDateTime()
        val settingAlarmTime = settingUseCase.getAlarmDate()
        if (nextAlertTime.toLocalTime() == settingAlarmTime.toLocalTime()) {
            val calendar: Calendar = Calendar.getInstance()
                .apply {
                    this.set(Calendar.DAY_OF_MONTH, nextAlertTime.dayOfMonth)
                    this.set(Calendar.HOUR_OF_DAY, nextAlertTime.hour)
                    this.set(Calendar.MINUTE, nextAlertTime.minute)
                    this.set(Calendar.SECOND, nextAlertTime.second)
                }
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
        } else {
            val alarmTimeMilli = nextAlertTime.toEpochSecond(OffsetDateTime.now().offset) * 1000
            val clockInfo = AlarmManager.AlarmClockInfo(alarmTimeMilli, null)
            alarmManager.setAlarmClock(clockInfo, pendingIntent)
        }
    }
}