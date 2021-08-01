package com.myapp.presentation.ui.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.myapp.common.getDateTimeNow
import com.myapp.domain.usecase.SettingUseCase
import com.myapp.presentation.utill.Status
import java.time.LocalDateTime
import java.time.LocalTime

/**
 * 設定画面 画面ロジック
 */
class SettingViewModel(private val settingUseCase: SettingUseCase) : ViewModel() {

    private val _beforeDate = MutableLiveData("")
    val beforeDate: LiveData<String> = _beforeDate

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

    }

    // 時間更新
    fun updateDate() {
        val hour = hourDate.value ?: return
        val minutes = minutesDate.value ?: return
        val seconds = secondsDate.value ?: return
        val date = getDateTimeNow().with(LocalTime.of(hour, minutes, seconds))
        settingUseCase.updateAlarmDate(date)
        _updateState.value = Status.Success(date)
    }
}