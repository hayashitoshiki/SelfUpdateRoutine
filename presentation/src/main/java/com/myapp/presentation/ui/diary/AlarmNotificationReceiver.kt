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
import com.myapp.domain.usecase.SettingUseCase
import com.myapp.presentation.R
import org.koin.java.KoinJavaComponent.inject
import timber.log.Timber
import java.util.*

/**
 * 通知バー表示
 */
class AlarmNotificationReceiver : BroadcastReceiver() {

    private val settingUseCase by inject<SettingUseCase>(SettingUseCase::class.java)

    companion object {
        const val TAG = "AlarmReceiver"
        const val NOTIFICATION_ID = 0
        const val PRIMARY_CHANNEL_ID = "primary_notification_channel"
    }

    private lateinit var notificationManager: NotificationManager

    override fun onReceive(
        context: Context,
        intent: Intent
    ) {
        Timber.tag(TAG)
            .d("Received intent : $intent")
        notificationManager = context.getSystemService(
            Context.NOTIFICATION_SERVICE
        ) as NotificationManager

        createNotificationChannel()
        deliverNotification(context)
        setNextAlarm(context)
    }

    // 通史表示
    private fun deliverNotification(context: Context) {
        val fullScreenIntent = Intent(context, DiaryActivity::class.java)
        val fullScreenPendingIntent = PendingIntent.getActivity(
            context, 0, fullScreenIntent, PendingIntent.FLAG_UPDATE_CURRENT
        )

        val contentIntent = Intent(context, DiaryActivity::class.java)
        val contentPendingIntent = PendingIntent.getActivity(
            context, NOTIFICATION_ID, contentIntent, PendingIntent.FLAG_UPDATE_CURRENT
        )
        val builder = NotificationCompat.Builder(context, PRIMARY_CHANNEL_ID)
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
            val notificationChannel = NotificationChannel(
                PRIMARY_CHANNEL_ID, "Stand up notification", NotificationManager.IMPORTANCE_HIGH
            )
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.enableVibration(true)
            notificationChannel.description = "AlarmManager Tests"
            notificationManager.createNotificationChannel(
                notificationChannel
            )
        }
    }

    // 次の日にアラーム設定
    private fun setNextAlarm(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmNotificationReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context, NOTIFICATION_ID, intent, PendingIntent.FLAG_UPDATE_CURRENT
        )
        val calendar: Calendar = Calendar.getInstance()
            .apply {
                val date = settingUseCase.getAlarmDate()
                this.add(Calendar.DAY_OF_MONTH, 1)
                this.set(Calendar.HOUR_OF_DAY, date.hour)
                this.set(Calendar.MINUTE, date.minute)
                this.set(Calendar.SECOND, date.second)
            }
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent
        )
    }
}