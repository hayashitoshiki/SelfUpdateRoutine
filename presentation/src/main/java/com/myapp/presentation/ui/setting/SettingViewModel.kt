package com.myapp.presentation.ui.setting


import com.myapp.common.getStrHHmm
import com.myapp.common.getStrHMMddEHHmm
import com.myapp.domain.dto.NextAlarmTimeInputDto
import com.myapp.domain.model.value.AlarmMode
import com.myapp.domain.usecase.SettingUseCase
import com.myapp.presentation.R
import com.myapp.presentation.utils.base.BaseAacViewModel
import com.myapp.presentation.utils.expansion.explanation
import com.myapp.presentation.utils.expansion.getLightTextColorByEnable
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * 設定画面 画面ロジック
 */
@HiltViewModel
class SettingViewModel @Inject constructor(private val settingUseCase: SettingUseCase) :
    BaseAacViewModel<SettingContract.State, SettingContract.Effect, SettingContract.Event>() {

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
            val beforeData = alarmDate.getStrHHmm()
            val alarmTimeDiff = "$beforeData -> $beforeData"
           setState {
                copy(
                    beforeDate = beforeData,
                    hourDate = alarmDate.hour,
                    minutesDate = alarmDate.minute,
                    secondsDate = alarmDate.second,
                    beforeAlarmMode = alarmMode,
                    alarmMode = alarmMode,
                    nextAlarmTime = alarmDate.getStrHMMddEHHmm(),
                    alarmTimeDiff = alarmTimeDiff,
                    alarmTimeDiffColor = R.color.text_color_light_secondary
                )
            }
        }.onFailure { setEffect { SettingContract.Effect.ShowError(it) } }

    }

    // アラーム時間変更
    private fun changeAlarmHour(value: Int) {
        setState { copy(hourDate = value) }
        changeAlarmTime()
        changeEnableButton()
    }

    // アラーム分変更
    private fun changeAlarmMinute(value: Int) {
        setState { copy(minutesDate = value) }
        changeAlarmTime()
        changeEnableButton()
    }

    // アラームモード変更
    private fun changeAlarmMode(value: AlarmMode) {
        val state = state.value ?: return
        val alarmModeDiffColor = getLightTextColorByEnable(state.beforeAlarmMode == value)
        setState { copy(alarmMode = value, alarmModeExplanation = value.explanation, alarmModeDiffColor = alarmModeDiffColor) }
        changeEnableButton()
    }

    // アラーム設定時間の差分取得
    private fun changeAlarmTime() {
        val state = state.value ?: return
        val alarmTimeDiff = state.beforeDate + " -> " + String.format("%02d", state.hourDate) + ":" + String.format("%02d", state.minutesDate)
        val alarmTimeDiffColor = getLightTextColorByEnable(alarmTimeDiff.take(5) == alarmTimeDiff.takeLast(5))
        val nextAlarmTimeInputDto = NextAlarmTimeInputDto(state.hourDate, state.minutesDate, state.secondsDate, state.alarmMode)

        runCatching { settingUseCase.getNextAlarmDate(nextAlarmTimeInputDto) }
            .onSuccess { setState { copy(alarmTimeDiff = alarmTimeDiff, alarmTimeDiffColor = alarmTimeDiffColor, nextAlarmTime = it.getStrHMMddEHHmm()) } }
            .onFailure { SettingContract.Effect.ShowError(it) }
    }

    // 確定ボタン活性非活性
    private fun changeEnableButton() {
        val state = state.value ?: return
        val isEnableConfirmButton = state.beforeAlarmMode != state.alarmMode || state.alarmTimeDiff.take(5) != state.alarmTimeDiff.takeLast(5)
        setState { copy(isEnableConfirmButton = isEnableConfirmButton) }
    }

    // 時間更新
    private fun updateDate() {
        val state = state.value ?: return
        val nextAlarmTimeInputDto = NextAlarmTimeInputDto(state.hourDate, state.minutesDate, state.secondsDate, state.alarmMode)
        runCatching { settingUseCase.updateAlarmDate(nextAlarmTimeInputDto) }
            .onSuccess { setEffect { SettingContract.Effect.NextNavigation(it) } }
            .onFailure { setEffect { SettingContract.Effect.ShowError(it) } }
    }
}
