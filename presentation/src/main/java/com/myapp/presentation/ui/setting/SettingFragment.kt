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
import com.myapp.domain.model.value.AlarmMode
import com.myapp.presentation.R
import com.myapp.presentation.databinding.FragmentSettingBinding
import com.myapp.presentation.ui.diary.AlarmNotificationReceiver
import com.myapp.presentation.utils.base.BaseAacFragment
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
class SettingFragment : BaseAacFragment<SettingContract.State, SettingContract.Effect, SettingContract.Event>() {

    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!
    override val viewModel: SettingViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_setting, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.setEvent(SettingContract.Event.CreatedView)
        viewModel.state.value?.let { state ->
            binding.timePicker.hour = state.beforeDate.substring(0, 2).toInt()
            binding.timePicker.minute = state.beforeDate.substring(3, 5).toInt()
            binding.alarmValue.text = state.alarmTimeDiff
            binding.alarmValue.setTextColor(resources.getColor(state.alarmTimeDiffColor, null))
            binding.alarmTitle.setTextColor(resources.getColor(state.alarmTimeDiffColor, null))
            binding.nextTitle.setTextColor(resources.getColor(state.alarmTimeDiffColor, null))
            binding.nextValue.setTextColor(resources.getColor(state.alarmTimeDiffColor, null))
            binding.nextValue.text = state.nextAlarmTime
            binding.radioHard.isChecked = state.alarmMode == AlarmMode.HARD
            binding.radioNormal.isChecked = state.alarmMode == AlarmMode.NORMAL
            binding.modeDiffValue.text = getString(state.beforeAlarmMode.text) + " -> " + getString(state.alarmMode.text)
            binding.modeTitle.setTextColor(resources.getColor(state.alarmModeDiffColor, null))
            binding.modeDiffValue.setTextColor(resources.getColor(state.alarmModeDiffColor, null))
            binding.txtModeExplanation.text = requireContext().getString(state.alarmModeExplanation)
            binding.btnOk.isEnabled = state.isEnableConfirmButton
        }
    }

    // Viewの各値変更
    override fun changedState(state: SettingContract.State) {
        viewModel.cashState.let { cash ->
            if (cash == null || state.beforeDate != cash.beforeDate) {
                binding.timePicker.hour = state.beforeDate.substring(0, 2).toInt()
                binding.timePicker.minute = state.beforeDate.substring(3, 5).toInt()
            }
            if (cash == null || state.alarmTimeDiff != cash.alarmTimeDiff) {
                binding.alarmValue.text = state.alarmTimeDiff
            }
            if (cash == null || state.alarmTimeDiffColor != cash.alarmTimeDiffColor) {
                binding.alarmValue.setTextColor(resources.getColor(state.alarmTimeDiffColor, null))
                binding.alarmTitle.setTextColor(resources.getColor(state.alarmTimeDiffColor, null))
                binding.nextTitle.setTextColor(resources.getColor(state.alarmTimeDiffColor, null))
                binding.nextValue.setTextColor(resources.getColor(state.alarmTimeDiffColor, null))
            }
            if (cash == null || state.nextAlarmTime != cash.nextAlarmTime) {
                binding.nextValue.text = state.nextAlarmTime
            }
            if (cash == null || state.alarmMode != cash.alarmMode) {
                binding.radioHard.isChecked = state.alarmMode == AlarmMode.HARD
                binding.radioNormal.isChecked = state.alarmMode == AlarmMode.NORMAL
                binding.modeDiffValue.text = getString(state.beforeAlarmMode.text) + " -> " + getString(state.alarmMode.text)
            }
            if (cash == null || state.alarmModeDiffColor != cash.alarmModeDiffColor) {
                binding.modeTitle.setTextColor(resources.getColor(state.alarmModeDiffColor, null))
                binding.modeDiffValue.setTextColor(resources.getColor(state.alarmModeDiffColor, null))
            }
            if (cash == null || state.alarmModeExplanation != cash.alarmModeExplanation) {
                binding.txtModeExplanation.text = requireContext().getString(state.alarmModeExplanation)
            }
            if (cash == null || state.isEnableConfirmButton != cash.isEnableConfirmButton) {
                binding.btnOk.isEnabled = state.isEnableConfirmButton
            }
        }
    }

    // イベント発火
    override fun setEvent() {

        // アラート時間
        binding.timePicker.also {
            it.setIs24HourView(true)
            it.setOnTimeChangedListener { timePicker, hourOfDay, minute ->
                if (hourOfDay in 0..14) timePicker.hour = 18
                if (hourOfDay in 15..17) timePicker.hour = 23

                val state = viewModel.state.value
                if (state == null || timePicker.hour != state.hourDate) { viewModel.setEvent(SettingContract.Event.OnChangeAlarmHour(timePicker.hour)) }
                if (state == null || minute != state.minutesDate) { viewModel.setEvent(SettingContract.Event.OnChangeAlarmMinute(minute)) }
            }
        }

        // アラートモード
        binding.radioHard.setOnClickListener { viewModel.setEvent(SettingContract.Event.OnChangeAlarmMode(AlarmMode.HARD)) }
        binding.radioNormal.setOnClickListener { viewModel.setEvent(SettingContract.Event.OnChangeAlarmMode(AlarmMode.NORMAL)) }

        // 確定ボタン
        binding.btnOk.setOnClickListener { viewModel.setEvent(SettingContract.Event.OnClickNextButton) }
    }

    // イベント発火
    override fun setEffect(effect: SettingContract.Effect) = when(effect) {
        is SettingContract.Effect.ShowError -> Toasty.error(requireContext(), effect.throwable.message!!, Toast.LENGTH_SHORT, true).show()
        is SettingContract.Effect.NextNavigation -> setAlarmAndBack(effect.value)
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
