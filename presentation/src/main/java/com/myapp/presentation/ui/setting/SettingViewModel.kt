package com.myapp.presentation.ui.setting


import com.myapp.common.getStrHHmm
import com.myapp.common.getStrHMMddEHHmm
import com.myapp.domain.dto.NextAlarmTimeInputDto
import com.myapp.domain.model.value.AlarmMode
import com.myapp.domain.usecase.SettingUseCase
import com.myapp.presentation.utils.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalTime
import javax.inject.Inject

/**
 * 設定画面 画面ロジック
 */
@HiltViewModel
class SettingViewModel @Inject constructor(private val settingUseCase: SettingUseCase) :
    BaseViewModel<SettingContract.State, SettingContract.Effect, SettingContract.Event>() {

    override fun initState(): SettingContract.State {
        return SettingContract.State()
    }

    override fun handleEvents(event: SettingContract.Event) {
        when (event) {
            is SettingContract.Event.CreatedView -> initAlarmData()
            is SettingContract.Event.OnChangeAlarmHour -> changeAlarmHour(event.value)
            is SettingContract.Event.OnChangeAlarmMinute -> changeAlarmMinute(event.value)
            is SettingContract.Event.OnChangeAlarmMode -> changeAlarmMode(event.value)
            is SettingContract.Event.OnClickNextButton -> updateDate()
        }
    }

    // アラームモード初期化
    private fun initAlarmData() {
        runCatching {
            val alarmMode = settingUseCase.getAlarmMode()
            val alarmDate = settingUseCase.getAlarmDate()
           setState {
                copy(
                    beforeTime = alarmDate,
                    afterTime = LocalTime.of(alarmDate.hour, alarmDate.minute, alarmDate.second),
                    beforeAlarmMode = alarmMode,
                    afterAlarmMode = alarmMode,
                    nextAlarmTime = alarmDate.getStrHMMddEHHmm()
                )
            }
        }.onFailure { setEffect { SettingContract.Effect.ShowError(it) } }

    }

    // アラーム時間変更
    private fun changeAlarmHour(hour: Int) {
        val alarmTime = LocalTime.of(hour, state.value.afterTime.minute, state.value.afterTime.second)
        changeAlarmTime(alarmTime)
        changeEnableButton()
    }

    // アラーム分変更
    private fun changeAlarmMinute(minute: Int) {
        val alarmTime = LocalTime.of(state.value.afterTime.hour, minute, state.value.afterTime.second)
        changeAlarmTime(alarmTime)
        changeEnableButton()
    }

    // アラームモード変更
    private fun changeAlarmMode(value: AlarmMode) {
        setState { copy(afterAlarmMode = value) }
        changeEnableButton()
    }

    // アラーム設定時間の差分取得
    private fun changeAlarmTime(alarmTime: LocalTime) {
       val nextAlarmTimeInputDto = NextAlarmTimeInputDto(alarmTime.hour, alarmTime.minute, alarmTime.second, state.value.afterAlarmMode)

        runCatching { settingUseCase.getNextAlarmDate(nextAlarmTimeInputDto) }
            .onSuccess { setState { copy(afterTime = alarmTime, nextAlarmTime = it.getStrHMMddEHHmm()) } }
            .onFailure { SettingContract.Effect.ShowError(it) }
    }

    // 確定ボタン活性非活性
    private fun changeEnableButton() {
        val isEnableConfirmButton = state.value.beforeAlarmMode != state.value.afterAlarmMode || state.value.beforeTime.getStrHHmm() != state.value.afterTime.getStrHHmm()
        setState { copy(isEnableConfirmButton = isEnableConfirmButton) }
    }

    // 時間更新
    private fun updateDate() {
        val nextAlarmTimeInputDto = NextAlarmTimeInputDto(state.value.afterTime.hour, state.value.afterTime.minute, state.value.afterTime.second, state.value.afterAlarmMode)
        runCatching { settingUseCase.updateAlarmDate(nextAlarmTimeInputDto) }
            .onSuccess { setEffect { SettingContract.Effect.NextNavigation(it) } }
            .onFailure { setEffect { SettingContract.Effect.ShowError(it) } }
    }
}
