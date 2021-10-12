package com.myapp.presentation.ui.setting

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.myapp.domain.dto.NextAlarmTimeInputDto
import com.myapp.domain.model.value.AlarmMode
import com.myapp.domain.usecase.SettingUseCase
import com.myapp.presentation.R
import com.myapp.presentation.utils.expansion.explanation
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.*
import org.junit.rules.TestRule
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*

/**
 * 設定画面　UIロジック仕様
 *
 */
class SettingViewModelTest {

    private lateinit var settingViewModel: SettingViewModel
    private lateinit var settingUseCase: SettingUseCase
    private val initLocalDataTime = LocalDateTime.now().with(LocalTime.of(22, 20, 0))
    private val updateLocalDataTime = LocalDateTime.now().with(LocalTime.of(22, 25, 0))

    @ExperimentalCoroutinesApi
    private val coroutineDispatcher = TestCoroutineDispatcher()

    @ExperimentalCoroutinesApi
    private val testScope = TestCoroutineScope(coroutineDispatcher)

    // LiveData用
    @Rule
    @JvmField
    val rule: TestRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        Dispatchers.setMain(coroutineDispatcher)
        settingUseCase = mockk<SettingUseCase>().also {
            coEvery { it.getAlarmDate() } returns initLocalDataTime
            coEvery { it.getAlarmMode() } returns AlarmMode.NORMAL
            coEvery { it.updateAlarmDate(any()) } returns updateLocalDataTime
        }
        settingViewModel = SettingViewModel(settingUseCase)
    }

    @ExperimentalCoroutinesApi
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    /**
     * 実行結果比較
     *
     * @param state Stateの期待値
     * @param effect Effectの期待値
     */
    @ExperimentalCoroutinesApi
    private fun result(
        state: SettingContract.State,
        effect: SettingContract.Effect?
    ) = testScope.runBlockingTest {
        val resultState = settingViewModel.state.value
        val resultEffect = settingViewModel.effect.value

        // 比較
        Assert.assertEquals(resultState, state)
        if (resultEffect is SettingContract.Effect.ShowError) {
            val resultMessage = resultEffect.throwable.message
            val message = (effect as SettingContract.Effect.ShowError).throwable.message
            Assert.assertEquals(resultMessage, message)
        } else {
            Assert.assertEquals(resultEffect, effect)
        }
    }

    // region アラーム分変更

    /**
     * アラーム分変更
     *
     * 条件：変更後のアラーム分が元々のアラーム分と異なる
     * 期待結果；
     * ・画面の値
     * 　　・アラームモードがノーマルへ変更されること
     * 　　・アラームモード差分の文字色が非活性濁であること
     * 　　・変更ボタンが非活性状態であること
     * ・画面イベント
     * 　　・何もイベントが発生しないこと
     * ・業務ロジック
     * 　　　ー
     */
    @ExperimentalCoroutinesApi
    @Test
    fun changeAlarmMinuteByNotEqual() = testScope.runBlockingTest {

        // 期待結果
        settingViewModel.setEvent(SettingContract.Event.CreatedView)
        val value = initLocalDataTime.minute + 3
        val expectationsEffect = null
        val alarmTimeDiff = settingViewModel.state.value!!.beforeDate + " -> " + String.format(
            "%02d", settingViewModel.state.value!!.hourDate
        ) + ":" + String.format("%02d", value)
        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("M月d日(E) HH:mm", Locale.JAPANESE)
        val expectationsState = settingViewModel.state.value!!.copy(
            minutesDate = value, alarmTimeDiff = alarmTimeDiff, alarmTimeDiffColor = R.color.text_color_light_primary,
            isEnableConfirmButton = true, nextAlarmTime = updateLocalDataTime.format(formatter)
        )

        // 実施
        settingViewModel.setEvent(SettingContract.Event.OnChangeAlarmMinute(value))

        // 比較
        result(expectationsState, expectationsEffect)
    }

    /**
     * アラーム分変更
     *
     * 条件：変更後のアラーム分が元々のアラーム分と同じ
     * 期待結果；
     * ・画面の値
     * 　　・アラームモードがノーマルへ変更されること
     * 　　・アラームモード差分の文字色が非活性濁であること
     * 　　・変更ボタンが非活性状態であること
     * ・画面イベント
     * 　　・何もイベントが発生しないこと
     * ・業務ロジック
     * 　　　ー
     */
    @ExperimentalCoroutinesApi
    @Test
    fun changeAlarmMinuteByEqual() = testScope.runBlockingTest {

        // 期待結果
        settingViewModel.setEvent(SettingContract.Event.CreatedView)
        val value = initLocalDataTime.minute + 3
        val expectationsEffect = null
        val alarmTimeDiff = settingViewModel.state.value!!.beforeDate + " -> " + String.format(
            "%02d", settingViewModel.state.value!!.hourDate
        ) + ":" + String.format("%02d", initLocalDataTime.minute)
        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("M月d日(E) HH:mm", Locale.JAPANESE)
        val expectationsState = settingViewModel.state.value!!.copy(
            minutesDate = initLocalDataTime.minute, alarmTimeDiff = alarmTimeDiff,
            alarmTimeDiffColor = R.color.text_color_light_secondary, isEnableConfirmButton = false,
            nextAlarmTime = updateLocalDataTime.format(formatter)
        )

        // 実施
        settingViewModel.setEvent(SettingContract.Event.OnChangeAlarmMinute(value))
        settingViewModel.setEvent(SettingContract.Event.OnChangeAlarmMinute(initLocalDataTime.minute))

        // 比較
        result(expectationsState, expectationsEffect)
    }

    // endregion

    // region アラーム時間変更

    /**
     * アラーム時間変更
     *
     * 条件：変更後のアラーム時間が元々のアラーム時間と異なる
     * 期待結果；
     * ・画面の値
     * 　　・アラームモードがノーマルへ変更されること
     * 　　・アラームモード差分の文字色が非活性濁であること
     * 　　・変更ボタンが非活性状態であること
     * ・画面イベント
     * 　　・何もイベントが発生しないこと
     * ・業務ロジック
     * 　　　ー
     */
    @ExperimentalCoroutinesApi
    @Test
    fun changeAlarmHourByNotEqual() = testScope.runBlockingTest {

        // 期待結果
        settingViewModel.setEvent(SettingContract.Event.CreatedView)
        val value = initLocalDataTime.hour + 1
        val expectationsEffect = null
        val alarmTimeDiff = settingViewModel.state.value!!.beforeDate + " -> " + String.format(
            "%02d", value
        ) + ":" + String.format("%02d", settingViewModel.state.value!!.minutesDate)
        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("M月d日(E) HH:mm", Locale.JAPANESE)
        val expectationsState = settingViewModel.state.value!!.copy(
            hourDate = value, alarmTimeDiff = alarmTimeDiff, alarmTimeDiffColor = R.color.text_color_light_primary,
            isEnableConfirmButton = true, nextAlarmTime = updateLocalDataTime.format(formatter)
        )

        // 実施
        settingViewModel.setEvent(SettingContract.Event.OnChangeAlarmHour(value))

        // 比較
        result(expectationsState, expectationsEffect)
    }

    /**
     * アラーム時間変更
     *
     * 条件：変更後のアラーム時間が元々のアラーム時間と同じ
     * 期待結果；
     * ・画面の値
     * 　　・アラームモードがノーマルへ変更されること
     * 　　・アラームモード差分の文字色が非活性濁であること
     * 　　・変更ボタンが非活性状態であること
     * ・画面イベント
     * 　　・何もイベントが発生しないこと
     * ・業務ロジック
     * 　　　ー
     */
    @ExperimentalCoroutinesApi
    @Test
    fun changeAlarmHourByEqual() = testScope.runBlockingTest {

        // 期待結果
        settingViewModel.setEvent(SettingContract.Event.CreatedView)
        val value = initLocalDataTime.hour + 1
        val expectationsEffect = null
        val alarmTimeDiff = settingViewModel.state.value!!.beforeDate + " -> " + String.format(
            "%02d", settingViewModel.state.value!!.hourDate
        ) + ":" + String.format("%02d", initLocalDataTime.minute)
        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("M月d日(E) HH:mm", Locale.JAPANESE)
        val expectationsState = settingViewModel.state.value!!.copy(
            hourDate = initLocalDataTime.hour, alarmTimeDiff = alarmTimeDiff,
            alarmTimeDiffColor = R.color.text_color_light_secondary, isEnableConfirmButton = false,
            nextAlarmTime = updateLocalDataTime.format(formatter)
        )

        // 実施
        settingViewModel.setEvent(SettingContract.Event.OnChangeAlarmHour(value))
        settingViewModel.setEvent(SettingContract.Event.OnChangeAlarmHour(initLocalDataTime.hour))

        // 比較
        result(expectationsState, expectationsEffect)
    }

    // endregion

    // region アラームモード変更

    /**
     * アラームモード変更
     *
     * 条件：変更時のアラームモード：ノーマル、アラームモードの初期値：ノーマル
     * 期待結果；
     * ・画面の値
     * 　　・アラームモードがノーマルへ変更されること
     * 　　・アラームモード差分の文字色が非活性濁であること
     * 　　・変更ボタンが非活性状態であること
     * ・画面イベント
     * 　　・何もイベントが発生しないこと
     * ・業務ロジック
     * 　　　ー
     */
    @ExperimentalCoroutinesApi
    @Test
    fun changeAlarmModeBySetNormalInitNormal() = testScope.runBlockingTest {

        // 期待結果
        settingUseCase = mockk<SettingUseCase>().also {
            coEvery { it.getAlarmDate() } returns LocalDateTime.now()
            coEvery { it.getAlarmMode() } returns AlarmMode.NORMAL
            coEvery { it.updateAlarmDate(any()) } returns updateLocalDataTime
        }
        settingViewModel = SettingViewModel(settingUseCase)
        settingViewModel.setEvent(SettingContract.Event.CreatedView)
        val alarmMode = AlarmMode.NORMAL
        val expectationsEffect = null
        val expectationsState =
            settingViewModel.state.value!!.copy(alarmMode = alarmMode, alarmModeExplanation = alarmMode.explanation)

        // 実施
        settingViewModel.setEvent(SettingContract.Event.OnChangeAlarmMode(alarmMode))

        // 比較
        result(expectationsState, expectationsEffect)
    }

    /**
     * アラームモード変更
     *
     * 条件：変更時のアラームモード：ノーマル、アラームモードの初期値：ノーマル
     * 期待結果；
     * ・画面の値
     * 　　・アラームモードがノーマルへ変更されること
     * 　　・アラームモード差分の文字色が活性濁であること
     * 　　・変更ボタンが活性状態であること
     * ・画面イベント
     * 　　・何もイベントが発生しないこと
     * ・業務ロジック
     * 　　　ー
     */
    @ExperimentalCoroutinesApi
    @Test
    fun changeAlarmModeBySetNormalInitHard() = testScope.runBlockingTest {

        // 期待結果
        settingUseCase = mockk<SettingUseCase>().also {
            coEvery { it.getAlarmDate() } returns LocalDateTime.now()
            coEvery { it.getAlarmMode() } returns AlarmMode.HARD
            coEvery { it.updateAlarmDate(any()) } returns updateLocalDataTime
        }
        settingViewModel = SettingViewModel(settingUseCase)
        settingViewModel.setEvent(SettingContract.Event.CreatedView)
        val alarmMode = AlarmMode.NORMAL
        val expectationsEffect = null
        val expectationsState = settingViewModel.state.value!!.copy(
            alarmMode = alarmMode, alarmModeExplanation = alarmMode.explanation,
            alarmModeDiffColor = R.color.text_color_light_primary, isEnableConfirmButton = true
        )

        // 実施
        settingViewModel.setEvent(SettingContract.Event.OnChangeAlarmMode(alarmMode))

        // 比較
        result(expectationsState, expectationsEffect)
    }

    /**
     * アラームモード変更
     *
     * 条件：変更時のアラームモード：ハード、アラームモードの初期値：ハード
     * 期待結果；
     * ・画面の値
     * 　　・アラームモードがハードへ変更されること
     * 　　・アラームモード差分の文字色が非活性濁であること
     * 　　・変更ボタンが非活性状態であること
     * ・画面イベント
     * 　　・何もイベントが発生しないこと
     * ・業務ロジック
     * 　　　ー
     */
    @ExperimentalCoroutinesApi
    @Test
    fun changeAlarmModeBySetHardInitHard() = testScope.runBlockingTest {

        // 期待結果
        settingUseCase = mockk<SettingUseCase>().also {
            coEvery { it.getAlarmDate() } returns LocalDateTime.now()
            coEvery { it.getAlarmMode() } returns AlarmMode.HARD
            coEvery { it.updateAlarmDate(any()) } returns updateLocalDataTime
        }
        settingViewModel = SettingViewModel(settingUseCase)
        settingViewModel.setEvent(SettingContract.Event.CreatedView)
        val alarmMode = AlarmMode.HARD
        val expectationsEffect = null
        val expectationsState =
            settingViewModel.state.value!!.copy(alarmMode = alarmMode, alarmModeExplanation = alarmMode.explanation)

        // 実施
        settingViewModel.setEvent(SettingContract.Event.OnChangeAlarmMode(alarmMode))

        // 比較
        result(expectationsState, expectationsEffect)
    }

    /**
     * アラームモード変更
     *
     * 条件：変更時のアラームモード：ハード、アラームモードの初期値：ハード
     * 期待結果；
     * ・画面の値
     * 　　・アラームモードがハードへ変更されること
     * 　　・アラームモード差分の文字色が活性濁であること
     * 　　・変更ボタンが活性状態であること
     * ・画面イベント
     * 　　・何もイベントが発生しないこと
     * ・業務ロジック
     * 　　　ー
     */
    @ExperimentalCoroutinesApi
    @Test
    fun changeAlarmModeBySetHardInitNormal() = testScope.runBlockingTest {

        // 期待結果
        settingUseCase = mockk<SettingUseCase>().also {
            coEvery { it.getAlarmDate() } returns LocalDateTime.now()
            coEvery { it.getAlarmMode() } returns AlarmMode.NORMAL
            coEvery { it.updateAlarmDate(any()) } returns updateLocalDataTime
        }
        settingViewModel = SettingViewModel(settingUseCase)
        settingViewModel.setEvent(SettingContract.Event.CreatedView)
        val alarmMode = AlarmMode.HARD
        val expectationsEffect = null
        val expectationsState = settingViewModel.state.value!!.copy(
            alarmMode = alarmMode, alarmModeExplanation = alarmMode.explanation,
            alarmModeDiffColor = R.color.text_color_light_primary, isEnableConfirmButton = true
        )

        // 実施
        settingViewModel.setEvent(SettingContract.Event.OnChangeAlarmMode(alarmMode))

        // 比較
        result(expectationsState, expectationsEffect)
    }

    // endregion

    // region 変更ボタン押下

    /**
     * 変更ボタン押下
     *
     * 条件：なし
     * 期待結果；
     * ・画面の値：何も値が変更されないこと
     * ・画面イベント
     * 　　・画面遷移イベントが走ること
     * ・業務ロジック
     * 　　　更新処理が走ること
     */
    @ExperimentalCoroutinesApi
    @Test
    fun onClickNextButton() = testScope.runBlockingTest {

        // 期待結果
        settingUseCase = mockk<SettingUseCase>().also {
            coEvery { it.getAlarmDate() } returns LocalDateTime.now()
            coEvery { it.getAlarmMode() } returns AlarmMode.NORMAL
            coEvery { it.updateAlarmDate(any()) } returns updateLocalDataTime
        }
        settingViewModel = SettingViewModel(settingUseCase)
        settingViewModel.setEvent(SettingContract.Event.CreatedView)
        val expectationsEffect = SettingContract.Effect.NextNavigation(updateLocalDataTime)
        val expectationsState = settingViewModel.state.value!!.copy()
        val nextAlarmTimeInputDto = NextAlarmTimeInputDto(
            expectationsState.hourDate, expectationsState.minutesDate, expectationsState.secondsDate,
            expectationsState.alarmMode
        )

        // 実施
        settingViewModel.setEvent(SettingContract.Event.OnClickNextButton)

        // 比較
        result(expectationsState, expectationsEffect)
        coVerify(exactly = 1) { (settingUseCase).updateAlarmDate(nextAlarmTimeInputDto) }
    }

    /**
     * 変更ボタン押下
     *
     * 条件：登録処理で例外が発生すること
     * 期待結果；
     * ・画面の値：何も値が変更されないこと
     * ・画面イベント
     * 　　・エラーイベントが走ること
     * ・業務ロジック
     * 　　　更新処理が走ること
     */
    @ExperimentalCoroutinesApi
    @Test
    fun onClickNextButtonByException() = testScope.runBlockingTest {

        // 期待結果
        settingUseCase = mockk<SettingUseCase>().also {
            coEvery { it.getAlarmDate() } returns LocalDateTime.now()
            coEvery { it.getAlarmMode() } returns AlarmMode.NORMAL
            coEvery { it.updateAlarmDate(any()) } throws IllegalArgumentException("test")
        }
        settingViewModel = SettingViewModel(settingUseCase)
        settingViewModel.setEvent(SettingContract.Event.CreatedView)
        val expectationsEffect = SettingContract.Effect.ShowError(IllegalArgumentException("test"))
        val expectationsState = settingViewModel.state.value!!.copy()
        val nextAlarmTimeInputDto = NextAlarmTimeInputDto(
            expectationsState.hourDate, expectationsState.minutesDate, expectationsState.secondsDate,
            expectationsState.alarmMode
        )

        // 実施
        settingViewModel.setEvent(SettingContract.Event.OnClickNextButton)

        // 比較
        result(expectationsState, expectationsEffect)
        coVerify(exactly = 1) { (settingUseCase).updateAlarmDate(nextAlarmTimeInputDto) }
    }


    // endregion
}