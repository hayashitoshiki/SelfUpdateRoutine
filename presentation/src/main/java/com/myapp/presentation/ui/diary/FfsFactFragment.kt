package com.myapp.presentation.ui.diary

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.myapp.presentation.R
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*


/**
 * 振り返り_事実画面
 */
class FfsFactFragment : DiaryBaseFragment() {

    override val viewModel: FfsFactViewModel by viewModel()

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding.txtSection.text = requireContext().getString(R.string.section1_1)
        binding.txtTitle.text = requireContext().getString(R.string.title_item_fact)
        binding.btnOk.setOnClickListener {
            setAlarm()
            findNavController().navigate(R.id.action_diaryFactItem_to_diaryFindItem)
        }
    }


    // 通知バーアラーム表示設定(次の日の設定)
    private fun setAlarm() {
        val alarmManager = requireActivity().getSystemService(ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmNotificationReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context, AlarmNotificationReceiver.NOTIFICATION_ID, intent, PendingIntent.FLAG_UPDATE_CURRENT
        )
        val calendar: Calendar = Calendar.getInstance()
            .apply {
                this.add(Calendar.DAY_OF_MONTH, 1)
                this.set(Calendar.HOUR_OF_DAY, 22)
                this.set(Calendar.MINUTE, 0)
                this.set(Calendar.SECOND, 0)
            }
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent
        )
    }
}
