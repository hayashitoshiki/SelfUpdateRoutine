package com.myapp.presentation.ui.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.myapp.domain.dto.NextAlarmTimeInputDto
import com.myapp.domain.model.value.AlarmMode
import com.myapp.domain.usecase.SettingUseCase
import com.myapp.presentation.utill.Status
import java.time.LocalDateTime

/**
 * 設定画面 画面ロジック
 */
class SettingViewModel(private val settingUseCase: SettingUseCase) : ViewModel() {

    private val _beforeDate = MutableLiveData("")
    val beforeDate: LiveData<String> = _beforeDate

    private val _alarmMode = MutableLiveData<AlarmMode>()
    val alarmMode: LiveData<AlarmMode> = _alarmMode

    private val _updateState = MutableLiveData<Status<LocalDateTime>>()
    val updateState: LiveData<Status<LocalDateTime>> = _updateState

    val hourDate = MutableLiveData(22)
    val minutesDate = MutableLiveData(0)
    val secondsDate = MutableLiveData(0)

    init {
        settingUseCase.getAlarmDate()
            .apply {
                var beforeString = this.hour.toString() + "時"
                if (this.minute != 0) {
                    beforeString += this.minute.toString() + "分"
                }
                if (this.second != 0) {
                    beforeString += this.second.toString() + "秒"
                }
                _beforeDate.value = beforeString
                hourDate.value = this.hour
                minutesDate.value = this.minute
                secondsDate.value = this.second
            }
        _alarmMode.value = settingUseCase.getAlarmMode()
    }

    // 時間更新
    fun updateDate() {
        val hour = hourDate.value ?: return
        val minute = minutesDate.value ?: return
        val second = secondsDate.value ?: return
        val mode = alarmMode.value ?: return
        val nextAlarmTimeInputDto = NextAlarmTimeInputDto(hour, minute, second, mode)
        val date = settingUseCase.updateAlarmDate(nextAlarmTimeInputDto)
        _updateState.value = Status.Success(date)
    }

    // アラームモード設定
    fun setAlarmMode(mode: AlarmMode) {
        _alarmMode.value = mode
    }
}