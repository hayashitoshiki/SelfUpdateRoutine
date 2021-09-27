package com.myapp.presentation.ui.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.myapp.domain.dto.NextAlarmTimeInputDto
import com.myapp.domain.model.value.AlarmMode
import com.myapp.domain.usecase.SettingUseCase
import com.myapp.presentation.R
import com.myapp.presentation.utils.Status
import com.myapp.presentation.utils.explanation
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import javax.inject.Inject

/**
 * 設定画面 画面ロジック
 */
@HiltViewModel
class SettingViewModel @Inject constructor(private val settingUseCase: SettingUseCase) : ViewModel() {

    // 変更前のアラーム時間
    private val _beforeDate = MediatorLiveData<String>()
    val beforeDate: LiveData<String> = _beforeDate

    // 変更後のアラーム時間
    val hourDate = MutableLiveData<Int>()
    val minutesDate = MutableLiveData<Int>()
    val secondsDate = MutableLiveData<Int>()

    // 変更前と変更後のアラーム時間の変化
    private val _alarmTimeDiff = MediatorLiveData<String>()
    val alarmTimeDiff: LiveData<String> = _alarmTimeDiff

    // 変更前と変更後のアラーム時間の変化の文字色
    private val _alarmTimeDiffColor = MediatorLiveData<Int>()
    val alarmTimeDiffColor: LiveData<Int> = _alarmTimeDiffColor

    // 次回のアラーム時間
    private val _nextAlarmTime = MediatorLiveData<String>()
    val nextAlarmTime: LiveData<String> = _nextAlarmTime

    // 変更前のアラームモード
    var beforeAlarmMode: AlarmMode

    // 変更後のアラームモード
    private val _alarmMode = MutableLiveData<AlarmMode>()
    val alarmMode: LiveData<AlarmMode> = _alarmMode

    // 変更前と変更後のアラームモードの変化の文字色
    private val _alarmModeDiffColor = MediatorLiveData<Int>()
    val alarmModeDiffColor: LiveData<Int> = _alarmModeDiffColor

    // アラームモード説明
    private val _alarmModeExplanation = MediatorLiveData<Int>()
    val alarmModeExplanation: LiveData<Int> = _alarmModeExplanation

    // 更新ボタン活性非活性制御
    private val _isEnableConfirmButton = MediatorLiveData<Boolean>()
    val isEnableConfirmButton: LiveData<Boolean> = _isEnableConfirmButton

    // アラーム更新ステータス
    private val _updateState = MutableLiveData<Status<LocalDateTime>>()
    val updateState: LiveData<Status<LocalDateTime>> = _updateState

    init {
        settingUseCase.getAlarmDate()
            .let {
                _beforeDate.value = String.format("%02d", it.hour) + ":" + String.format("%02d", it.minute)
                hourDate.value = it.hour
                minutesDate.value = it.minute
                secondsDate.value = it.second
            }
        settingUseCase.getAlarmMode()
            .let {
                beforeAlarmMode = it
                _alarmMode.value = it
            }

        _alarmTimeDiff.addSource(hourDate) {
            setDiffAlarmTime()
            setNextAlarmDate()
        }
        _alarmTimeDiff.addSource(minutesDate) {
            setDiffAlarmTime()
            setNextAlarmDate()
        }
        _alarmTimeDiffColor.addSource(_alarmTimeDiff) {
            _alarmTimeDiffColor.value = if (it.take(5) == it.takeLast(5)) {
                R.color.text_color_light_secondary
            } else {
                R.color.text_color_light_primary
            }
        }
        _alarmModeDiffColor.addSource(_alarmMode) {
            _alarmModeDiffColor.value = if (beforeAlarmMode == it) {
                R.color.text_color_light_secondary
            } else {
                R.color.text_color_light_primary
            }
        }
        _isEnableConfirmButton.addSource(_alarmTimeDiffColor) { changeEnableButton() }
        _isEnableConfirmButton.addSource(_alarmModeDiffColor) { changeEnableButton() }
        _alarmModeExplanation.addSource(_alarmMode) { _alarmModeExplanation.value = it.explanation }
    }

    // 時間更新
    fun updateDate() {
        val hour = hourDate.value ?: run {
            _updateState.value = Status.Failure(IllegalAccessError("時間データが入っていません"))
            return
        }
        val minute = minutesDate.value ?: run {
            _updateState.value = Status.Failure(IllegalAccessError("分データが入っていません"))
            return
        }
        val second = secondsDate.value ?: run {
            _updateState.value = Status.Failure(IllegalAccessError("秒データが入っていません"))
            return
        }
        val mode = alarmMode.value ?: run {
            _updateState.value = Status.Failure(IllegalAccessError("アラートモードデータが入っていません"))
            return
        }
        val nextAlarmTimeInputDto = NextAlarmTimeInputDto(hour, minute, second, mode)
        val date = settingUseCase.updateAlarmDate(nextAlarmTimeInputDto)
        _updateState.value = Status.Success(date)
    }

    // アラームモード設定
    fun setAlarmMode(mode: AlarmMode) {
        _alarmMode.value = mode
    }

    // アラーム設定時間の差分取得
    private fun setDiffAlarmTime() {
        _alarmTimeDiff.value =
            beforeDate.value + " -> " + String.format("%02d", hourDate.value) + ":" + String.format("%02d", minutesDate.value)
    }

    // 次回のアラーム時刻設定
    private fun setNextAlarmDate() {
        val hour = hourDate.value ?: run {
            _updateState.value = Status.Failure(IllegalAccessError("時間データが入っていません"))
            return
        }
        val minute = minutesDate.value ?: run {
            _updateState.value = Status.Failure(IllegalAccessError("分データが入っていません"))
            return
        }
        val second = secondsDate.value ?: run {
            _updateState.value = Status.Failure(IllegalAccessError("秒データが入っていません"))
            return
        }
        val mode = alarmMode.value ?: run {
            _updateState.value = Status.Failure(IllegalAccessError("アラートモードデータが入っていません"))
            return
        }
        val nextAlarmTimeInputDto = NextAlarmTimeInputDto(hour, minute, second, mode)
        val date = settingUseCase.getNextAlarmDate(nextAlarmTimeInputDto)
        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("M月d日(E) HH:mm", Locale.JAPANESE)
        _nextAlarmTime.value = date.format(formatter)
    }

    // 確定ボタン活性非活性
    private fun changeEnableButton() {
        _isEnableConfirmButton.value =
            _alarmModeDiffColor.value == R.color.text_color_light_primary || _alarmTimeDiffColor.value == R.color.text_color_light_primary
    }
}
