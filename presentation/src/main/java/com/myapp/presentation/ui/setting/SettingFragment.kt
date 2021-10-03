package com.myapp.presentation.ui.setting

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.myapp.presentation.R
import com.myapp.presentation.databinding.FragmentSettingBinding
import com.myapp.presentation.ui.diary.AlarmNotificationReceiver
import com.myapp.presentation.utils.base.BaseFragment
import com.myapp.presentation.utils.base.Status
import com.myapp.presentation.utils.expansion.observeAtOnce
import com.myapp.presentation.utils.expansion.text
import dagger.hilt.android.AndroidEntryPoint
import es.dmoral.toasty.Toasty
import timber.log.Timber
import java.time.LocalDateTime
import java.time.OffsetDateTime

/**
 * 設定画面
 */
@AndroidEntryPoint
class SettingFragment : BaseFragment() {

    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!
    val viewModel: SettingViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_setting, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.updateState.observe(viewLifecycleOwner, { onUpdateDateStateChanged(it) })
        viewModel.beforeDate.observeAtOnce(viewLifecycleOwner, { initTimePicker(it) })
        viewModel.alarmModeExplanation.observe(viewLifecycleOwner, { binding.txtModeExplanation.text = getString(it) })
        viewModel.alarmMode.observe(viewLifecycleOwner, {
            binding.modeDiffValue.text = getString(viewModel.beforeAlarmMode.text) + " -> " + getString(it.text)
        })

        binding.timePicker.also {
            it.setIs24HourView(true)
            it.setOnTimeChangedListener { timePicker, hourOfDay, minute ->
                if (hourOfDay in 0..14) {
                    timePicker.hour = 18
                }
                if (hourOfDay in 15..17) {
                    timePicker.hour = 23
                }
                if (timePicker.hour != viewModel.hourDate.value) {
                    viewModel.hourDate.value = timePicker.hour
                }
                if (minute != viewModel.minutesDate.value) {
                    viewModel.minutesDate.value = minute
                }
            }
        }

        // 確定ボタン
        binding.btnOk.setOnClickListener {
            viewModel.updateDate()
        }
    }

    // TimPicker初期値設定
    private fun initTimePicker(dataStr: String) {
        binding.timePicker.hour = dataStr.substring(0, 2)
            .toInt()
        binding.timePicker.minute = dataStr.substring(3, 5)
            .toInt()
    }

    // アラーム設定ステータス監視
    private fun onUpdateDateStateChanged(state: Status<LocalDateTime>) = when (state) {
        is Status.Loading -> {
        }
        is Status.Success -> {
            setAlarmAndBack(state.data)
        }
        is Status.Failure -> {
            Toasty.error(requireContext(), state.throwable.message!!, Toast.LENGTH_SHORT, true)
                .show()
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
        Timber.tag(this.javaClass.simpleName)
            .d("*******************")
        Timber.tag(this.javaClass.simpleName)
            .d("アラーム登録日時= %s", date)
        Timber.tag(this.javaClass.simpleName)
            .d("*******************")
        Toasty.success(requireContext(), "アラーム設定を更新しました！", Toast.LENGTH_SHORT, true)
            .show()
        findNavController().popBackStack()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
