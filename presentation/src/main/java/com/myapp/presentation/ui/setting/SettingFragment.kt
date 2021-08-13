package com.myapp.presentation.ui.setting

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.myapp.presentation.R
import com.myapp.presentation.databinding.FragmentSettingBinding
import com.myapp.presentation.ui.diary.AlarmNotificationReceiver
import com.myapp.presentation.utill.BaseFragment
import com.myapp.presentation.utill.Status
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.time.LocalDateTime
import java.time.OffsetDateTime


/**
 * 設定画面
 */
class SettingFragment : BaseFragment() {


    private lateinit var binding: FragmentSettingBinding
    val viewModel: SettingViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_setting, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.updateState.observe(viewLifecycleOwner, { onUpdateDateStateChanged(it) })

        binding.npHour.apply {
            this.minValue = 0
            this.maxValue = 23
            this.wrapSelectorWheel = false
        }
        binding.npMinutes.apply {
            this.minValue = 0
            this.maxValue = 59
            this.wrapSelectorWheel = false
        }
        binding.npSeconds.apply {
            this.minValue = 0
            this.maxValue = 59
            this.wrapSelectorWheel = false
        }

        // 確定ボタン
        binding.btnOk.setOnClickListener {
            viewModel.updateDate()
        }
    }

    // アラーム設定ステータス監視
    private fun onUpdateDateStateChanged(state: Status<LocalDateTime>) = when (state) {
        is Status.Loading -> {
        }
        is Status.Success -> {
            setAlarmAndBack(state.data)
        }
        is Status.Failure -> {
        }
        is Status.Non -> {
        }
    }


    // 通知バーアラーム表示設定(次の日の設定)
    private fun setAlarmAndBack(date: LocalDateTime) {
        val alarmManager = requireActivity().getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmNotificationReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context, AlarmNotificationReceiver.NOTIFICATION_ID, intent, PendingIntent.FLAG_UPDATE_CURRENT
        )
        val alarmTimeMilli = date.toEpochSecond(OffsetDateTime.now().offset) * 1000
        val clockInfo = AlarmManager.AlarmClockInfo(alarmTimeMilli, null)
        alarmManager.setAlarmClock(clockInfo, pendingIntent)

        findNavController().popBackStack()
    }

}