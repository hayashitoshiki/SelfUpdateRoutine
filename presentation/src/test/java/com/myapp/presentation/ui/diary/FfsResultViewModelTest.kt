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
 * 振り返り_FFS振り返り確認画面 画面ロジック仕様
 */
class FfsResultViewModelTest {

    private val state = FfsResultContract.State()
    private lateinit var ffsFactViewModel: FfsResultViewModel
    private var resultAction: DiaryDispatcherContract.Action? = null

    @ExperimentalCoroutinesApi
    private val coroutineDispatcher = TestCoroutineDispatcher()

    @ExperimentalCoroutinesApi
    private val testScope = TestCoroutineScope(coroutineDispatcher)

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        Dispatchers.setMain(coroutineDispatcher)
        ffsFactViewModel = FfsResultViewModel()

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
        state: FfsResultContract.State,
        effect: FfsResultContract.Effect?,
        action: DiaryDispatcherContract.Action?
    ) = testScope.runBlockingTest {
        val resultState = ffsFactViewModel.state.value
        var resultEffect: FfsResultContract.Effect? = null
        ffsFactViewModel.effect.onEach { resultEffect = it }
            .launchIn(testScope)

        // 比較
        assertEquals(resultState, state)
        assertEquals(resultAction, action)
        assertEquals(resultEffect, effect)
    }


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
        val expectationsEffect = FfsResultContract.Effect.NextNavigation
        val expectationsAction = null

        // 実施
        ffsFactViewModel.setEvent(FfsResultContract.Event.OnClickNextButton)

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
     * 　　・事実値が変更されること
     * ・画面イベント
     * 　　・何もイベントが発生しないこと
     * ・共通処理イベント
     * 　　ー
     */
    @ExperimentalCoroutinesApi
    @Test
    fun actionByChangeFact() = testScope.runBlockingTest {

        // 期待結果
        val value = "fact"
        val action = DiaryDispatcherContract.Action.ChangeFact(value)
        val expectationsState = state.copy(fact = value)
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
     * 　　・発見値が変更されること
     * ・画面イベント
     * 　　・何もイベントが発生しないこと
     * ・共通処理イベント
     * 　　ー
     */
    @ExperimentalCoroutinesApi
    @Test
    fun actionByChangeFind() = testScope.runBlockingTest {

        // 期待結果
        val value = "find"
        val action = DiaryDispatcherContract.Action.ChangeFind(value)
        val expectationsState = state.copy(find = value)
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
     * 　　・教訓値が変更されること
     * ・画面イベント
     * 　　・何もイベントが発生しないこと
     * ・共通処理イベント
     * 　　ー
     */
    @ExperimentalCoroutinesApi
    @Test
    fun actionByChangeLearn() = testScope.runBlockingTest {

        // 期待結果
        val value = "learn"
        val action = DiaryDispatcherContract.Action.ChangeLearn(value)
        val expectationsState = state.copy(learn = value)
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
     * 　　・宣言値が変更されること
     * ・画面イベント
     * 　　・何もイベントが発生しないこと
     * ・共通処理イベント
     * 　　ー
     */
    @ExperimentalCoroutinesApi
    @Test
    fun actionByChangeStatement() = testScope.runBlockingTest {

        // 期待結果
        val value = "statement"
        val action = DiaryDispatcherContract.Action.ChangeStatement(value)
        val expectationsState = state.copy(statement = value)
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
     * 　　・点数値が変更されること
     * ・画面イベント
     * 　　・何もイベントが発生しないこと
     * ・共通処理イベント
     * 　　ー
     */
    @ExperimentalCoroutinesApi
    @Test
    fun actionByChangeAssessment() = testScope.runBlockingTest {

        // 期待結果
        val value = 1f
        val action = DiaryDispatcherContract.Action.ChangeAssessment(value)
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
     * 　　・理由値が変更されないこと
     * ・画面イベント
     * 　　・何もイベントが発生しないこと
     * ・共通処理イベント
     * 　　ー
     */
    @ExperimentalCoroutinesApi
    @Test
    fun actionByChangeReason() = testScope.runBlockingTest {

        // 期待結果
        val value = "reason"
        val action = DiaryDispatcherContract.Action.ChangeReason(value)
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
        val value = "improve"
        val action = DiaryDispatcherContract.Action.ChangeImprove(value)
        val expectationsState = state.copy()
        val expectationsEffect = null

        // 実施
        DiaryDispatcher.setActions(action)

        // 比較
        result(expectationsState, expectationsEffect, action)
    }

    // endregion
}