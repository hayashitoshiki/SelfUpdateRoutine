package com.myapp.presentation.ui.diary

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

/**
 * 振り返り_理由画面 画面ロジック仕様
 */
class WeatherReasonViewModelTest {

    private val state = DiaryBaseContract.State()
    private lateinit var ffsFactViewModel: WeatherReasonViewModel
    private var resultAction: DiaryDispatcherContract.Action? = null

    @ExperimentalCoroutinesApi
    private val coroutineDispatcher = TestCoroutineDispatcher()

    @ExperimentalCoroutinesApi
    private val testScope = TestCoroutineScope(coroutineDispatcher)

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        Dispatchers.setMain(coroutineDispatcher)
        ffsFactViewModel = WeatherReasonViewModel()

        DiaryDispatcher.action.onEach { resultAction = it }
            .launchIn(testScope)
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
     * @param action actionの期待値
     */
    @ExperimentalCoroutinesApi
    private fun result(
        state: DiaryBaseContract.State,
        effect: DiaryBaseContract.Effect?,
        action: DiaryDispatcherContract.Action?
    ) = testScope.runBlockingTest {
        val resultState = ffsFactViewModel.state.value
        var resultEffect: DiaryBaseContract.Effect? = null
        ffsFactViewModel.effect.onEach { resultEffect = it }
            .launchIn(testScope)

        // 比較
        assertEquals(resultState, state)
        assertEquals(resultEffect, effect)
        assertEquals(resultAction, action)
    }

    // region テキスト入力

    /**
     * テキスト変更
     *
     * 条件：文字入力
     * 期待結果；
     * ・画面の値
     * 　　・入力値が入力した文字に変更されること
     * 　　・次へボタンが活性状態になること
     * ・画面イベント
     * 　　・何もイベントが発生しないこと
     * ・共通処理イベント
     * 　　・入力した文字を引数に理由入力アクションが流れること
     */
    @ExperimentalCoroutinesApi
    @Test
    fun changeReasonByInput() = testScope.runBlockingTest {

        // 期待結果
        val value = "reason"
        val expectationsState = state.copy(inputText = value, isButtonEnable = true)
        val expectationsEffect = null
        val expectationsAction = DiaryDispatcherContract.Action.ChangeReason(value)

        // 実施
        ffsFactViewModel.setEvent(DiaryBaseContract.Event.OnChangeText(value))

        // 比較
        result(expectationsState, expectationsEffect, expectationsAction)
    }

    /**
     * テキスト変更
     *
     * 条件：入力した文字を全て削除
     * 期待結果；
     * ・画面の値
     * 　　・入力値が空になること
     * 　　・次へボタンが非活性状態になること
     * ・画面イベント
     * 　　・何もイベントが発生しないこと
     * ・共通処理イベント
     * 　　・入力した文字を引数に理由案入力アクションが流れること
     */
    @ExperimentalCoroutinesApi
    @Test
    fun changeReasonByAllDelete() = testScope.runBlockingTest {

        // 期待結果
        val initValue = "reason"
        val value = ""
        val expectationsState = state.copy(inputText = value, isButtonEnable = false)
        val expectationsEffect = null
        val expectationsAction = DiaryDispatcherContract.Action.ChangeReason(value)

        // 実施
        ffsFactViewModel.setEvent(DiaryBaseContract.Event.OnChangeText(initValue))
        ffsFactViewModel.setEvent(DiaryBaseContract.Event.OnChangeText(value))

        // 比較
        result(expectationsState, expectationsEffect, expectationsAction)
    }

    // endregion

    // region ボタン押下

    /**
     * 次へボタン押下
     *
     * 条件：なし
     * 期待結果；
     * ・画面の値
     * 　　・何も値が変わらないこと
     * ・画面イベント
     * 　　・画面遷移イベントが発生すること
     * ・共通処理イベント
     * 　　ー
     */
    @ExperimentalCoroutinesApi
    @Test
    fun onClickNextButton() = testScope.runBlockingTest {

        // 期待結果
        val expectationsState = state.copy()
        val expectationsEffect = DiaryBaseContract.Effect.NextNavigation
        val expectationsAction = null

        // 実施
        ffsFactViewModel.setEvent(DiaryBaseContract.Event.OnClickNextButton)

        // 比較
        result(expectationsState, expectationsEffect, expectationsAction)
    }

    // endregion

    // region 画面共有アクション

    /**
     * 画面共有アクション処理
     *
     * 条件：画面共有処理で事実入力アクションが発行される
     * 期待結果；
     * ・画面の値
     * 　　・何も値が変更されないこと
     * ・画面イベント
     * 　　・何もイベントが発生しないこと
     * ・共通処理イベント
     * 　　ー
     */
    @ExperimentalCoroutinesApi
    @Test
    fun actionByChangeFact() = testScope.runBlockingTest {

        // 期待結果
        val action = DiaryDispatcherContract.Action.ChangeFact("fact")
        val expectationsState = state.copy()
        val expectationsEffect = null

        // 実施
        DiaryDispatcher.setActions(action)

        // 比較
        result(expectationsState, expectationsEffect, action)
    }

    /**
     * 画面共有アクション処理
     *
     * 条件：画面共有処理で発見入力アクションが発行される
     * 期待結果；
     * ・画面の値
     * 　　・何も値が変更されないこと
     * ・画面イベント
     * 　　・何もイベントが発生しないこと
     * ・共通処理イベント
     * 　　ー
     */
    @ExperimentalCoroutinesApi
    @Test
    fun actionByChangeFind() = testScope.runBlockingTest {

        // 期待結果
        val action = DiaryDispatcherContract.Action.ChangeFind("find")
        val expectationsState = state.copy()
        val expectationsEffect = null

        // 実施
        DiaryDispatcher.setActions(action)

        // 比較
        result(expectationsState, expectationsEffect, action)
    }

    /**
     * 画面共有アクション処理
     *
     * 条件：画面共有処理で教訓入力アクションが発行される
     * 期待結果；
     * ・画面の値
     * 　　・何も値が変更されないこと
     * ・画面イベント
     * 　　・何もイベントが発生しないこと
     * ・共通処理イベント
     * 　　ー
     */
    @ExperimentalCoroutinesApi
    @Test
    fun actionByChangeLearn() = testScope.runBlockingTest {

        // 期待結果
        val action = DiaryDispatcherContract.Action.ChangeLearn("learn")
        val expectationsState = state.copy()
        val expectationsEffect = null

        // 実施
        DiaryDispatcher.setActions(action)

        // 比較
        result(expectationsState, expectationsEffect, action)
    }

    /**
     * 画面共有アクション処理
     *
     * 条件：画面共有処理で宣言入力アクションが発行される
     * 期待結果；
     * ・画面の値
     * 　　・何も値が変更されないこと
     * ・画面イベント
     * 　　・何もイベントが発生しないこと
     * ・共通処理イベント
     * 　　ー
     */
    @ExperimentalCoroutinesApi
    @Test
    fun actionByChangeStatement() = testScope.runBlockingTest {

        // 期待結果
        val action = DiaryDispatcherContract.Action.ChangeStatement("statement")
        val expectationsState = state.copy()
        val expectationsEffect = null

        // 実施
        DiaryDispatcher.setActions(action)

        // 比較
        result(expectationsState, expectationsEffect, action)
    }


    /**
     * 画面共有アクション処理
     *
     * 条件：画面共有処理で点数入力アクションが発行される
     * 期待結果；
     * ・画面の値
     * 　　・何も値が変更されないこと
     * ・画面イベント
     * 　　・何もイベントが発生しないこと
     * ・共通処理イベント
     * 　　ー
     */
    @ExperimentalCoroutinesApi
    @Test
    fun actionByChangeAssessment() = testScope.runBlockingTest {

        // 期待結果
        val action = DiaryDispatcherContract.Action.ChangeAssessment(1f)
        val expectationsState = state.copy()
        val expectationsEffect = null

        // 実施
        DiaryDispatcher.setActions(action)

        // 比較
        result(expectationsState, expectationsEffect, action)
    }

    /**
     * 画面共有アクション処理
     *
     * 条件：画面共有処理で理由入力アクションが発行される
     * 期待結果；
     * ・画面の値
     * 　　・何も値が変更されないこと
     * ・画面イベント
     * 　　・何もイベントが発生しないこと
     * ・共通処理イベント
     * 　　ー
     */
    @ExperimentalCoroutinesApi
    @Test
    fun actionByChangeReason() = testScope.runBlockingTest {

        // 期待結果
        val action = DiaryDispatcherContract.Action.ChangeReason("reason")
        val expectationsState = state.copy()
        val expectationsEffect = null

        // 実施
        DiaryDispatcher.setActions(action)

        // 比較
        result(expectationsState, expectationsEffect, action)
    }

    /**
     * 画面共有アクション処理
     *
     * 条件：画面共有処理で改善案入力アクションが発行される
     * 期待結果；
     * ・画面の値
     * 　　・何も値が変更されないこと
     * ・画面イベント
     * 　　・何もイベントが発生しないこと
     * ・共通処理イベント
     * 　　ー
     */
    @ExperimentalCoroutinesApi
    @Test
    fun actionByChangeImprove() = testScope.runBlockingTest {

        // 期待結果
        val action = DiaryDispatcherContract.Action.ChangeImprove("improve")
        val expectationsState = state.copy()
        val expectationsEffect = null

        // 実施
        DiaryDispatcher.setActions(action)

        // 比較
        result(expectationsState, expectationsEffect, action)
    }

    // endregion
}