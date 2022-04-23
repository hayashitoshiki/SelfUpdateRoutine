package com.myapp.presentation.ui.setting

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.myapp.common.getStrHMMddEHHmm
import com.myapp.domain.dto.NextAlarmTimeInputDto
import com.myapp.domain.model.value.AlarmMode
import com.myapp.domain.usecase.SettingUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.test.*
import org.junit.*
import org.junit.rules.TestRule
import java.time.LocalDateTime
import java.time.LocalTime

/**
 * 設定画面　UIロジック仕様
 *
 */
class SettingViewModelTest {

    // mock
    private lateinit var settingViewModel: SettingViewModel
    private lateinit var settingUseCase: SettingUseCase
    private var state = SettingContract.State()

    // data
    private val initLocalDataTime = LocalDateTime.now().with(LocalTime.of(22, 20, 0))
    private val initUpdateLocalDataTime = LocalDateTime.now().with(LocalTime.of(22, 25, 0))
    private var initAlertMode = AlarmMode.NORMAL


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
        setSettingUseCase()
        initViewModel()
    }

    // アラームタイムが初期値を返すようにモック化
    private fun setSettingUseCase() {
        settingUseCase = mockk<SettingUseCase>().also {
            coEvery { it.getAlarmDate() } returns initLocalDataTime
            coEvery { it.getAlarmMode() } returns initAlertMode
            coEvery { it.updateAlarmDate(any()) } returns initUpdateLocalDataTime
            coEvery { it.getNextAlarmDate(any()) } returns initLocalDataTime
        }
    }

    private fun setSettingUseCaseByAlertModeHard() {
        setSettingUseCase()
        initAlertMode = AlarmMode.HARD
        coEvery { settingUseCase.getAlarmMode() } returns initAlertMode
    }

    private fun  setSettingUseCaseByUpdateAlarmDateException() {
        setSettingUseCase()
        coEvery { settingUseCase.updateAlarmDate(any()) } throws IllegalArgumentException("test")
    }

    // ViewMode　初期化
    private fun initViewModel() {
        settingViewModel = SettingViewModel(settingUseCase)
        settingViewModel.setEvent(SettingContract.Event.CreatedView)
        state = state.copy(
            beforeTime = initLocalDataTime,
            afterTime = initLocalDataTime.toLocalTime(),
            beforeAlarmMode = initAlertMode,
            afterAlarmMode = initAlertMode,
            nextAlarmTime = initLocalDataTime.getStrHMMddEHHmm(),
            init = true
        )
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
    private fun result(state: SettingContract.State, effect: SettingContract.Effect? = null) = testScope.runBlockingTest {
        settingViewModel.effect
            .stateIn(
                scope = testScope,
                started = SharingStarted.Eagerly,
                initialValue = null
            )
            .onEach { assertEquals(effect, it) }
        assertEquals(state, settingViewModel.state.value)
    }

    // region アラーム分変更

    /**
     * アラーム分変更
     *
     * 条件：変更後のアラーム分が元々のアラーム分と異なる
     * 期待結果；
     * ・画面の値
     * 　　・アラームモードがノーマルへ変更されること
     * 　　・変更ボタンが非活性状態であること
     * ・画面イベント
     * 　　・何もイベントが発生しないこと
     * ・業務ロジック
     * 　　・次回のアラーム時間取得処理が呼ばれること
     */
    @ExperimentalCoroutinesApi
    @Test
    fun changeAlarmMinuteByNotEqual() = testScope.runBlockingTest {
        // 期待結果
        val value = initLocalDataTime.plusMinutes(3).toLocalTime()
        val expectationsState = state.copy(
            afterTime = value,
            isEnableConfirmButton = true
        )

        // 実施
        settingViewModel.setEvent(SettingContract.Event.OnChangeAlarmMinute(value.minute))

        // 比較
        result(expectationsState)
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
     * 　　・次回のアラーム時間取得処理が呼ばれること
     */
    @ExperimentalCoroutinesApi
    @Test
    fun changeAlarmMinuteByEqual() = testScope.runBlockingTest {
        // 期待結果
        val value = initLocalDataTime.minute + 3
        val expectationsState = state.copy(
            afterTime = initLocalDataTime.toLocalTime(),
            isEnableConfirmButton = false
        )

        // 実施
        settingViewModel.setEvent(SettingContract.Event.OnChangeAlarmMinute(value))
        settingViewModel.setEvent(SettingContract.Event.OnChangeAlarmMinute(initLocalDataTime.minute))

        // 比較
        result(expectationsState)
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
     * 　　・変更ボタンが非活性状態であること
     * ・画面イベント
     * 　　・何もイベントが発生しないこと
     * ・業務ロジック
     * 　　・次回のアラーム時間取得処理が呼ばれること
     */
    @ExperimentalCoroutinesApi
    @Test
    fun changeAlarmHourByNotEqual() = testScope.runBlockingTest {
        // 期待結果
        val value = initLocalDataTime.plusHours(1).toLocalTime()
        val expectationsState = state.copy(
            afterTime = value,
            isEnableConfirmButton = true
        )

        // 実施
        settingViewModel.setEvent(SettingContract.Event.OnChangeAlarmHour(value.hour))

        // 比較
        result(expectationsState)
    }

    /**
     * アラーム時間変更
     *
     * 条件：変更後のアラーム時間が元々のアラーム時間と同じになるよう変奥
     * 期待結果；
     * ・画面の値
     * 　　・アラームモードがノーマルへ変更されること
     * 　　・変更ボタンが非活性状態であること
     * ・画面イベント
     * 　　・何もイベントが発生しないこと
     * ・業務ロジック
     * 　　・次回のアラーム時間取得処理が呼ばれること
     */
    @ExperimentalCoroutinesApi
    @Test
    fun changeAlarmHourByEqual() = testScope.runBlockingTest {
        // 期待結果
        val value = initLocalDataTime.hour + 1
        val expectationsState = state.copy(
            afterTime = initLocalDataTime.toLocalTime(),
            isEnableConfirmButton = false,
        )

        // 実施
        settingViewModel.setEvent(SettingContract.Event.OnChangeAlarmHour(value))
        settingViewModel.setEvent(SettingContract.Event.OnChangeAlarmHour(initLocalDataTime.hour))

        // 比較
        result(expectationsState)
    }

    // endregion

    // region アラームモード変更

    /**
     * アラームモード変更
     *
     * 条件：変更時のアラームモード：ノーマル、アラームモードの初期値：ハード
     * 期待結果；
     * ・画面の値
     * 　　・アラームモードがノーマルへ変更されること
     * 　　・変更ボタンが非活性状態であること
     * ・画面イベント
     * 　　・何もイベントが発生しないこと
     * ・業務ロジック
     * 　　　ー
     */
    @ExperimentalCoroutinesApi
    @Test
    fun changeAlarmModeBySetNormalInitNormal() = testScope.runBlockingTest {
        // 初期化
        setSettingUseCaseByAlertModeHard()
        initViewModel()

        // 期待結果
        val alarmMode = AlarmMode.NORMAL
        val expectationsState = state.copy(
            afterAlarmMode = alarmMode,
            isEnableConfirmButton = true
        )

        // 実施
        settingViewModel.setEvent(SettingContract.Event.OnChangeAlarmMode(alarmMode))

        // 比較
        result(expectationsState)
    }

    /**
     * アラームモード変更
     *
     * 条件：変更時のアラームモード：ノーマル、アラームモードの初期値：ノーマル
     * 期待結果；
     * ・画面の値
     * 　　・アラームモードがノーマルへ変更されること
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
        val alarmMode = AlarmMode.NORMAL
        val expectationsState = state.copy(
            afterAlarmMode = alarmMode,
            isEnableConfirmButton = false
        )

        // 実施
        settingViewModel.setEvent(SettingContract.Event.OnChangeAlarmMode(alarmMode))

        // 比較
        result(expectationsState)
    }

    /**
     * アラームモード変更
     *
     * 条件：変更時のアラームモード：ハード、アラームモードの初期値：ハード
     * 期待結果；
     * ・画面の値
     * 　　・アラームモードがハードへ変更されること
     * 　　・変更ボタンが非活性状態であること
     * ・画面イベント
     * 　　・何もイベントが発生しないこと
     * ・業務ロジック
     * 　　　ー
     */
    @ExperimentalCoroutinesApi
    @Test
    fun changeAlarmModeBySetHardInitHard() = testScope.runBlockingTest {
        // 初期化
        setSettingUseCaseByAlertModeHard()
        initViewModel()

        // 期待結果
        val alarmMode = AlarmMode.HARD
        val expectationsState = state.copy(
            afterAlarmMode = alarmMode,
            isEnableConfirmButton = false
        )

        // 実施
        settingViewModel.setEvent(SettingContract.Event.OnChangeAlarmMode(alarmMode))

        // 比較
        result(expectationsState)
    }

    /**
     * アラームモード変更
     *
     * 条件：変更時のアラームモード：ハード、アラームモードの初期値：ノーマル
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
        val alarmMode = AlarmMode.HARD
        val expectationsState = state.copy(
            afterAlarmMode = alarmMode,
            isEnableConfirmButton = true
        )

        // 実施
        settingViewModel.setEvent(SettingContract.Event.OnChangeAlarmMode(alarmMode))

        // 比較
        result(expectationsState)
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
        val expectationsEffect = SettingContract.Effect.NextNavigation(initUpdateLocalDataTime)
        val expectationsState = state.copy()
        val nextAlarmTimeInputDto = NextAlarmTimeInputDto(
            state.afterTime.hour,
            state.afterTime.minute,
            state.afterTime.second,
            state.afterAlarmMode
        )

        // 実施
        initViewModel()
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
        val expectationsEffect = SettingContract.Effect.ShowError(IllegalArgumentException("test"))
        val expectationsState = state.copy()
        val nextAlarmTimeInputDto = NextAlarmTimeInputDto(
            state.afterTime.hour,
            state.afterTime.minute,
            state.afterTime.second,
            state.afterAlarmMode
        )

        // 実施
        setSettingUseCaseByUpdateAlarmDateException()
        initViewModel()
        settingViewModel.setEvent(SettingContract.Event.CreatedView)
        settingViewModel.setEvent(SettingContract.Event.OnClickNextButton)

        // 比較
        result(expectationsState, expectationsEffect)
        coVerify(exactly = 1) { (settingUseCase).updateAlarmDate(nextAlarmTimeInputDto) }
    }


    // endregion
}