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
 * 振り返り_宣言画面 画面ロジック仕様
 */
class FfsStatementViewModelTest {

    private val state = DiaryBaseContract.State()
    private lateinit var ffsFactViewModel: FfsStatementViewModel
    private var resultAction: DiaryDispatcherContract.Action? = null

    @ExperimentalCoroutinesApi
    private val coroutineDispatcher = TestCoroutineDispatcher()

    @ExperimentalCoroutinesApi
    private val testScope = TestCoroutineScope(coroutineDispatcher)

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        Dispatchers.setMain(coroutineDispatcher)
        ffsFactViewModel = FfsStatementViewModel()

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
     * @param action  actionの期待値
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
     * 条件：「私は〜ている〜です」の条件にあった文字列入力
     * 期待結果；
     * ・画面の値
     * 　　・入力値が入力した文字に変更されること
     * 　　・次へボタンが活性状態になること
     * ・画面イベント
     * 　　・何もイベントが発生しないこと
     * ・共通処理イベント
     * 　　・入力した文字を引数に宣言入力アクションが流れること
     */
    @ExperimentalCoroutinesApi
    @Test
    fun changeStatementByInputOK() = testScope.runBlockingTest {

        // 期待結果
        val value = "私は成長に貪欲に生きている人間です"
        val hintText = ""
        val hintVisibility = false
        val isButtonEnable = true
        val expectationsState = state.copy(
            inputText = value,
            hintText = hintText,
            hintVisibility = hintVisibility,
            isButtonEnable = isButtonEnable)
        val expectationsEffect = null
        val expectationsAction = DiaryDispatcherContract.Action.ChangeStatement(value)

        // 実施
        ffsFactViewModel.setEvent(DiaryBaseContract.Event.OnChangeText(value))

        // 比較
        result(expectationsState, expectationsEffect, expectationsAction)
    }

    /**
     * テキスト変更
     *
     * 条件：文字列の先頭が「私は」の文字列を入力
     * 期待結果；
     * ・画面の値
     * 　　・入力値が入力した文字に変更されること
     * 　　・次へボタンが活性状態になること
     * ・画面イベント
     * 　　・何もイベントが発生しないこと
     * ・共通処理イベント
     * 　　・入力した文字を引数に宣言入力アクションが流れること
     */
    @ExperimentalCoroutinesApi
    @Test
    fun changeStatementByInputStartOk1() = testScope.runBlockingTest {

        // 期待結果
        val value = "私は"
        val hintText = "文章は現在進行形にしてください"
        val hintVisibility = true
        val isButtonEnable = false
        val expectationsState = state.copy(
            inputText = value,
            hintText = hintText,
            hintVisibility = hintVisibility,
            isButtonEnable = isButtonEnable)
        val expectationsEffect = null
        val expectationsAction = DiaryDispatcherContract.Action.ChangeStatement(value)

        // 実施
        ffsFactViewModel.setEvent(DiaryBaseContract.Event.OnChangeText(value))

        // 比較
        result(expectationsState, expectationsEffect, expectationsAction)
    }

    /**
     * テキスト変更
     *
     * 条件：文字列の先頭が「わたしは」の文字列を入力
     * 期待結果；
     * ・画面の値
     * 　　・入力値が入力した文字に変更されること
     * 　　・次へボタンが活性状態になること
     * ・画面イベント
     * 　　・何もイベントが発生しないこと
     * ・共通処理イベント
     * 　　・入力した文字を引数に宣言入力アクションが流れること
     */
    @ExperimentalCoroutinesApi
    @Test
    fun changeStatementByInputStartOk2() = testScope.runBlockingTest {

        // 期待結果
        val value = "わたしは"
        val hintText = "文章は現在進行形にしてください"
        val hintVisibility = true
        val isButtonEnable = false
        val expectationsState = state.copy(
            inputText = value,
            hintText = hintText,
            hintVisibility = hintVisibility,
            isButtonEnable = isButtonEnable)
        val expectationsEffect = null
        val expectationsAction = DiaryDispatcherContract.Action.ChangeStatement(value)

        // 実施
        ffsFactViewModel.setEvent(DiaryBaseContract.Event.OnChangeText(value))

        // 比較
        result(expectationsState, expectationsEffect, expectationsAction)
    }

    /**
     * テキスト変更
     *
     * 条件：文字列の先頭が「わたしは」・「私は」以外の文字列を入力
     * 期待結果；
     * ・画面の値
     * 　　・入力値が入力した文字に変更されること
     * 　　・次へボタンが活性状態になること
     * ・画面イベント
     * 　　・何もイベントが発生しないこと
     * ・共通処理イベント
     * 　　・入力した文字を引数に宣言入力アクションが流れること
     */
    @ExperimentalCoroutinesApi
    @Test
    fun changeStatementByInputStartNG() = testScope.runBlockingTest {

        // 期待結果
        val value = "僕は"
        val hintText = "文章の先頭は「わたしは」ではじめてください"
        val hintVisibility = true
        val isButtonEnable = false
        val expectationsState = state.copy(
            inputText = value,
            hintText = hintText,
            hintVisibility = hintVisibility,
            isButtonEnable = isButtonEnable)
        val expectationsEffect = null
        val expectationsAction = DiaryDispatcherContract.Action.ChangeStatement(value)

        // 実施
        ffsFactViewModel.setEvent(DiaryBaseContract.Event.OnChangeText(value))

        // 比較
        result(expectationsState, expectationsEffect, expectationsAction)
    }


    /**
     * テキスト変更
     *
     * 条件：「わたしは〜ている」の文字列を入力
     * 期待結果；
     * ・画面の値
     * 　　・入力値が入力した文字に変更されること
     * 　　・次へボタンが活性状態になること
     * ・画面イベント
     * 　　・何もイベントが発生しないこと
     * ・共通処理イベント
     * 　　・入力した文字を引数に宣言入力アクションが流れること
     */
    @ExperimentalCoroutinesApi
    @Test
    fun changeStatementByInputMiddleOK() = testScope.runBlockingTest {

        // 期待結果
        val value = "私は貪欲に成長ている"
        val hintText = "最後は「です」と宣言してください"
        val hintVisibility = true
        val isButtonEnable = false
        val expectationsState = state.copy(
            inputText = value,
            hintText = hintText,
            hintVisibility = hintVisibility,
            isButtonEnable = isButtonEnable)
        val expectationsEffect = null
        val expectationsAction = DiaryDispatcherContract.Action.ChangeStatement(value)

        // 実施
        ffsFactViewModel.setEvent(DiaryBaseContract.Event.OnChangeText(value))

        // 比較
        result(expectationsState, expectationsEffect, expectationsAction)
    }

    /**
     * テキスト変更
     *
     * 条件：文字列のの中間に「ている」が入っていない文字列を入力
     * 期待結果；
     * ・画面の値
     * 　　・入力値が入力した文字に変更されること
     * 　　・次へボタンが活性状態になること
     * ・画面イベント
     * 　　・何もイベントが発生しないこと
     * ・共通処理イベント
     * 　　・入力した文字を引数に宣言入力アクションが流れること
     */
    @ExperimentalCoroutinesApi
    @Test
    fun changeStatementByInputMiddleNG1() = testScope.runBlockingTest {

        // 期待結果
        val value = "私は貪欲に成長"
        val hintText = "文章は現在進行形にしてください"
        val hintVisibility = true
        val isButtonEnable = false
        val expectationsState = state.copy(
            inputText = value,
            hintText = hintText,
            hintVisibility = hintVisibility,
            isButtonEnable = isButtonEnable)
        val expectationsEffect = null
        val expectationsAction = DiaryDispatcherContract.Action.ChangeStatement(value)

        // 実施
        ffsFactViewModel.setEvent(DiaryBaseContract.Event.OnChangeText(value))

        // 比較
        result(expectationsState, expectationsEffect, expectationsAction)
    }

    /**
     * テキスト変更
     *
     * 条件：「私はている」と繋がっている文字列を入力
     * 期待結果；
     * ・画面の値
     * 　　・入力値が入力した文字に変更されること
     * 　　・次へボタンが活性状態になること
     * ・画面イベント
     * 　　・何もイベントが発生しないこと
     * ・共通処理イベント
     * 　　・入力した文字を引数に宣言入力アクションが流れること
     */
    @ExperimentalCoroutinesApi
    @Test
    fun changeStatementByInputMiddleNG2() = testScope.runBlockingTest {

        // 期待結果
        val value = "私はている"
        val hintText = "動詞を入れてください"
        val hintVisibility = true
        val isButtonEnable = false
        val expectationsState = state.copy(
            inputText = value,
            hintText = hintText,
            hintVisibility = hintVisibility,
            isButtonEnable = isButtonEnable)
        val expectationsEffect = null
        val expectationsAction = DiaryDispatcherContract.Action.ChangeStatement(value)

        // 実施
        ffsFactViewModel.setEvent(DiaryBaseContract.Event.OnChangeText(value))

        // 比較
        result(expectationsState, expectationsEffect, expectationsAction)
    }

    /**
     * テキスト変更
     *
     * 条件：「私はでいる」と繋がっている文字列を入力
     * 期待結果；
     * ・画面の値
     * 　　・入力値が入力した文字に変更されること
     * 　　・次へボタンが活性状態になること
     * ・画面イベント
     * 　　・何もイベントが発生しないこと
     * ・共通処理イベント
     * 　　・入力した文字を引数に宣言入力アクションが流れること
     */
    @ExperimentalCoroutinesApi
    @Test
    fun changeStatementByInputMiddleNG3() = testScope.runBlockingTest {

        // 期待結果
        val value = "私はている"
        val hintText = "動詞を入れてください"
        val hintVisibility = true
        val isButtonEnable = false
        val expectationsState = state.copy(
            inputText = value,
            hintText = hintText,
            hintVisibility = hintVisibility,
            isButtonEnable = isButtonEnable)
        val expectationsEffect = null
        val expectationsAction = DiaryDispatcherContract.Action.ChangeStatement(value)

        // 実施
        ffsFactViewModel.setEvent(DiaryBaseContract.Event.OnChangeText(value))

        // 比較
        result(expectationsState, expectationsEffect, expectationsAction)
    }

    /**
     * テキスト変更
     *
     * 条件：文末が「です」ではない文字列を入力
     * 期待結果；
     * ・画面の値
     * 　　・入力値が入力した文字に変更されること
     * 　　・次へボタンが活性状態になること
     * ・画面イベント
     * 　　・何もイベントが発生しないこと
     * ・共通処理イベント
     * 　　・入力した文字を引数に宣言入力アクションが流れること
     */
    @ExperimentalCoroutinesApi
    @Test
    fun changeStatementByInputEndNG1() = testScope.runBlockingTest {

        // 期待結果
        val value = "私は貪欲に成長しているんだ"
        val hintText = "最後は「です」と宣言してください"
        val hintVisibility = true
        val isButtonEnable = false
        val expectationsState = state.copy(
            inputText = value,
            hintText = hintText,
            hintVisibility = hintVisibility,
            isButtonEnable = isButtonEnable)
        val expectationsEffect = null
        val expectationsAction = DiaryDispatcherContract.Action.ChangeStatement(value)

        // 実施
        ffsFactViewModel.setEvent(DiaryBaseContract.Event.OnChangeText(value))

        // 比較
        result(expectationsState, expectationsEffect, expectationsAction)
    }

    /**
     * テキスト変更
     *
     * 条件：文末が「私は〜ているです」となっている文字列を入力
     * 期待結果；
     * ・画面の値
     * 　　・入力値が入力した文字に変更されること
     * 　　・次へボタンが活性状態になること
     * ・画面イベント
     * 　　・何もイベントが発生しないこと
     * ・共通処理イベント
     * 　　・入力した文字を引数に宣言入力アクションが流れること
     */
    @ExperimentalCoroutinesApi
    @Test
    fun changeStatementByInputEndNG2() = testScope.runBlockingTest {

        // 期待結果
        val value = "私は貪欲に成長しているです"
        val hintText = "あなたは何者ですか？"
        val hintVisibility = true
        val isButtonEnable = false
        val expectationsState = state.copy(
            inputText = value,
            hintText = hintText,
            hintVisibility = hintVisibility,
            isButtonEnable = isButtonEnable)
        val expectationsEffect = null
        val expectationsAction = DiaryDispatcherContract.Action.ChangeStatement(value)

        // 実施
        ffsFactViewModel.setEvent(DiaryBaseContract.Event.OnChangeText(value))

        // 比較
        result(expectationsState, expectationsEffect, expectationsAction)
    }

    /**
     * テキスト変更
     *
     * 条件：文末が「私は〜ている○(一文字)です」の○が男or 女or 人以外になっている文字列を入力
     * 期待結果；
     * ・画面の値
     * 　　・入力値が入力した文字に変更されること
     * 　　・次へボタンが活性状態になること
     * ・画面イベント
     * 　　・何もイベントが発生しないこと
     * ・共通処理イベント
     * 　　・入力した文字を引数に宣言入力アクションが流れること
     */
    @ExperimentalCoroutinesApi
    @Test
    fun changeStatementByInputEndNG3() = testScope.runBlockingTest {

        // 期待結果
        val value = "私は貪欲に成長している猫です"
        val hintText = "あなたは何者ですか？"
        val hintVisibility = true
        val isButtonEnable = false
        val expectationsState = state.copy(
            inputText = value,
            hintText = hintText,
            hintVisibility = hintVisibility,
            isButtonEnable = isButtonEnable)
        val expectationsEffect = null
        val expectationsAction = DiaryDispatcherContract.Action.ChangeStatement(value)

        // 実施
        ffsFactViewModel.setEvent(DiaryBaseContract.Event.OnChangeText(value))

        // 比較
        result(expectationsState, expectationsEffect, expectationsAction)
    }

    /**
     * テキスト変更
     *
     * 条件：文末が「私は〜ている男です」になっている文字列を入力
     * 期待結果；
     * ・画面の値
     * 　　・入力値が入力した文字に変更されること
     * 　　・次へボタンが活性状態になること
     * ・画面イベント
     * 　　・何もイベントが発生しないこと
     * ・共通処理イベント
     * 　　・入力した文字を引数に宣言入力アクションが流れること
     */
    @ExperimentalCoroutinesApi
    @Test
    fun changeStatementByInputEndOK1() = testScope.runBlockingTest {

        // 期待結果
        val value = "私は貪欲に成長している男です"
        val hintText = ""
        val hintVisibility = false
        val isButtonEnable = true
        val expectationsState = state.copy(
            inputText = value,
            hintText = hintText,
            hintVisibility = hintVisibility,
            isButtonEnable = isButtonEnable)
        val expectationsEffect = null
        val expectationsAction = DiaryDispatcherContract.Action.ChangeStatement(value)

        // 実施
        ffsFactViewModel.setEvent(DiaryBaseContract.Event.OnChangeText(value))

        // 比較
        result(expectationsState, expectationsEffect, expectationsAction)
    }

    /**
     * テキスト変更
     *
     * 条件：文末が「私は〜ている女です」になっている文字列を入力
     * 期待結果；
     * ・画面の値
     * 　　・入力値が入力した文字に変更されること
     * 　　・次へボタンが活性状態になること
     * ・画面イベント
     * 　　・何もイベントが発生しないこと
     * ・共通処理イベント
     * 　　・入力した文字を引数に宣言入力アクションが流れること
     */
    @ExperimentalCoroutinesApi
    @Test
    fun changeStatementByInputEndOK2() = testScope.runBlockingTest {

        // 期待結果
        val value = "私は貪欲に成長している女です"
        val hintText = ""
        val hintVisibility = false
        val isButtonEnable = true
        val expectationsState = state.copy(
            inputText = value,
            hintText = hintText,
            hintVisibility = hintVisibility,
            isButtonEnable = isButtonEnable)
        val expectationsEffect = null
        val expectationsAction = DiaryDispatcherContract.Action.ChangeStatement(value)

        // 実施
        ffsFactViewModel.setEvent(DiaryBaseContract.Event.OnChangeText(value))

        // 比較
        result(expectationsState, expectationsEffect, expectationsAction)
    }

    /**
     * テキスト変更
     *
     * 条件：文末が「私は〜ている人です」になっている文字列を入力
     * 期待結果；
     * ・画面の値
     * 　　・入力値が入力した文字に変更されること
     * 　　・次へボタンが活性状態になること
     * ・画面イベント
     * 　　・何もイベントが発生しないこと
     * ・共通処理イベント
     * 　　・入力した文字を引数に宣言入力アクションが流れること
     */
    @ExperimentalCoroutinesApi
    @Test
    fun changeStatementByInputEndOK3() = testScope.runBlockingTest {

        // 期待結果
        val value = "私は貪欲に成長している人です"
        val hintText = ""
        val hintVisibility = false
        val isButtonEnable = true
        val expectationsState = state.copy(
            inputText = value,
            hintText = hintText,
            hintVisibility = hintVisibility,
            isButtonEnable = isButtonEnable)
        val expectationsEffect = null
        val expectationsAction = DiaryDispatcherContract.Action.ChangeStatement(value)

        // 実施
        ffsFactViewModel.setEvent(DiaryBaseContract.Event.OnChangeText(value))

        // 比較
        result(expectationsState, expectationsEffect, expectationsAction)
    }

    /**
     * テキスト変更
     *
     * 条件：文末が「私は〜ている人間です」になっている文字列を入力
     * 期待結果；
     * ・画面の値
     * 　　・入力値が入力した文字に変更されること
     * 　　・次へボタンが活性状態になること
     * ・画面イベント
     * 　　・何もイベントが発生しないこと
     * ・共通処理イベント
     * 　　・入力した文字を引数に宣言入力アクションが流れること
     */
    @ExperimentalCoroutinesApi
    @Test
    fun changeStatementByInputEndOK4() = testScope.runBlockingTest {

        // 期待結果
        val value = "私は貪欲に成長している人間です"
        val hintText = ""
        val hintVisibility = false
        val isButtonEnable = true
        val expectationsState = state.copy(
            inputText = value,
            hintText = hintText,
            hintVisibility = hintVisibility,
            isButtonEnable = isButtonEnable)
        val expectationsEffect = null
        val expectationsAction = DiaryDispatcherContract.Action.ChangeStatement(value)

        // 実施
        ffsFactViewModel.setEvent(DiaryBaseContract.Event.OnChangeText(value))

        // 比較
        result(expectationsState, expectationsEffect, expectationsAction)
    }

    /**
     * テキスト変更
     *
     * 条件：文末が「私は〜ている〜です!」となっている文字列を入力
     * 期待結果；
     * ・画面の値
     * 　　・入力値が入力した文字に変更されること
     * 　　・次へボタンが活性状態になること
     * ・画面イベント
     * 　　・何もイベントが発生しないこと
     * ・共通処理イベント
     * 　　・入力した文字を引数に宣言入力アクションが流れること
     */
    @ExperimentalCoroutinesApi
    @Test
    fun changeStatementByInputEndDecorate1() = testScope.runBlockingTest {

        // 期待結果
        val value = "私は貪欲に成長している人間です!"
        val hintText = ""
        val hintVisibility = false
        val isButtonEnable = true
        val expectationsState = state.copy(
            inputText = value,
            hintText = hintText,
            hintVisibility = hintVisibility,
            isButtonEnable = isButtonEnable)
        val expectationsEffect = null
        val expectationsAction = DiaryDispatcherContract.Action.ChangeStatement(value)

        // 実施
        ffsFactViewModel.setEvent(DiaryBaseContract.Event.OnChangeText(value))

        // 比較
        result(expectationsState, expectationsEffect, expectationsAction)
    }

    /**
     * テキスト変更
     *
     * 条件：文末が「私は〜ている〜です！」となっている文字列を入力
     * 期待結果；
     * ・画面の値
     * 　　・入力値が入力した文字に変更されること
     * 　　・次へボタンが活性状態になること
     * ・画面イベント
     * 　　・何もイベントが発生しないこと
     * ・共通処理イベント
     * 　　・入力した文字を引数に宣言入力アクションが流れること
     */
    @ExperimentalCoroutinesApi
    @Test
    fun changeStatementByInputEndDecorate2() = testScope.runBlockingTest {

        // 期待結果
        val value = "私は貪欲に成長している人間です！"
        val hintText = ""
        val hintVisibility = false
        val isButtonEnable = true
        val expectationsState = state.copy(
            inputText = value,
            hintText = hintText,
            hintVisibility = hintVisibility,
            isButtonEnable = isButtonEnable)
        val expectationsEffect = null
        val expectationsAction = DiaryDispatcherContract.Action.ChangeStatement(value)

        // 実施
        ffsFactViewModel.setEvent(DiaryBaseContract.Event.OnChangeText(value))

        // 比較
        result(expectationsState, expectationsEffect, expectationsAction)
    }

    /**
     * テキスト変更
     *
     * 条件：文末が「私は〜ている〜です.」となっている文字列を入力
     * 期待結果；
     * ・画面の値
     * 　　・入力値が入力した文字に変更されること
     * 　　・次へボタンが活性状態になること
     * ・画面イベント
     * 　　・何もイベントが発生しないこと
     * ・共通処理イベント
     * 　　・入力した文字を引数に宣言入力アクションが流れること
     */
    @ExperimentalCoroutinesApi
    @Test
    fun changeStatementByInputEndDecorate3() = testScope.runBlockingTest {

        // 期待結果
        val value = "私は貪欲に成長しているです."
        val hintText = "あなたは何者ですか？"
        val hintVisibility = true
        val isButtonEnable = false
        val expectationsState = state.copy(
            inputText = value,
            hintText = hintText,
            hintVisibility = hintVisibility,
            isButtonEnable = isButtonEnable)
        val expectationsEffect = null
        val expectationsAction = DiaryDispatcherContract.Action.ChangeStatement(value)

        // 実施
        ffsFactViewModel.setEvent(DiaryBaseContract.Event.OnChangeText(value))

        // 比較
        result(expectationsState, expectationsEffect, expectationsAction)
    }

    /**
     * テキスト変更
     *
     * 条件：文末が「私は〜ている〜です。」となっている文字列を入力
     * 期待結果；
     * ・画面の値
     * 　　・入力値が入力した文字に変更されること
     * 　　・次へボタンが活性状態になること
     * ・画面イベント
     * 　　・何もイベントが発生しないこと
     * ・共通処理イベント
     * 　　・入力した文字を引数に宣言入力アクションが流れること
     */
    @ExperimentalCoroutinesApi
    @Test
    fun changeStatementByInputEndDecorate4() = testScope.runBlockingTest {

        // 期待結果
        val value = "私は貪欲に成長している人間です。"
        val hintText = ""
        val hintVisibility = false
        val isButtonEnable = true
        val expectationsState = state.copy(
            inputText = value,
            hintText = hintText,
            hintVisibility = hintVisibility,
            isButtonEnable = isButtonEnable)
        val expectationsEffect = null
        val expectationsAction = DiaryDispatcherContract.Action.ChangeStatement(value)

        // 実施
        ffsFactViewModel.setEvent(DiaryBaseContract.Event.OnChangeText(value))

        // 比較
        result(expectationsState, expectationsEffect, expectationsAction)
    }

    /**
     * テキスト変更
     *
     * 条件：文末が「私は〜ている〜です★」となっている文字列を入力
     * 期待結果；
     * ・画面の値
     * 　　・入力値が入力した文字に変更されること
     * 　　・次へボタンが活性状態になること
     * ・画面イベント
     * 　　・何もイベントが発生しないこと
     * ・共通処理イベント
     * 　　・入力した文字を引数に宣言入力アクションが流れること
     */
    @ExperimentalCoroutinesApi
    @Test
    fun changeStatementByInputEndDecorate5() = testScope.runBlockingTest {

        // 期待結果
        val value = "私は貪欲に成長している人間です★"
        val hintText = ""
        val hintVisibility = false
        val isButtonEnable = true
        val expectationsState = state.copy(
            inputText = value,
            hintText = hintText,
            hintVisibility = hintVisibility,
            isButtonEnable = isButtonEnable)
        val expectationsEffect = null
        val expectationsAction = DiaryDispatcherContract.Action.ChangeStatement(value)

        // 実施
        ffsFactViewModel.setEvent(DiaryBaseContract.Event.OnChangeText(value))

        // 比較
        result(expectationsState, expectationsEffect, expectationsAction)
    }

    /**
     * テキスト変更
     *
     * 条件：文末が「私は〜ている〜です☆」となっている文字列を入力
     * 期待結果；
     * ・画面の値
     * 　　・入力値が入力した文字に変更されること
     * 　　・次へボタンが活性状態になること
     * ・画面イベント
     * 　　・何もイベントが発生しないこと
     * ・共通処理イベント
     * 　　・入力した文字を引数に宣言入力アクションが流れること
     */
    @ExperimentalCoroutinesApi
    @Test
    fun changeStatementByInputEndDecorate6() = testScope.runBlockingTest {

        // 期待結果
        val value = "私は貪欲に成長している人間です☆"
        val hintText = ""
        val hintVisibility = false
        val isButtonEnable = true
        val expectationsState = state.copy(
            inputText = value,
            hintText = hintText,
            hintVisibility = hintVisibility,
            isButtonEnable = isButtonEnable)
        val expectationsEffect = null
        val expectationsAction = DiaryDispatcherContract.Action.ChangeStatement(value)

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
     * 　　・入力した文字を引数に宣言入力アクションが流れること
     */
    @ExperimentalCoroutinesApi
    @Test
    fun changeStatementByAllDelete() = testScope.runBlockingTest {

        // 期待結果
        val initValue = "learn"
        val value = ""
        val hintText = ""
        val hintVisibility = false
        val isButtonEnable = false
        val expectationsState = state.copy(
            inputText = value,
            hintText = hintText,
            hintVisibility = hintVisibility,
            isButtonEnable = isButtonEnable)
        val expectationsEffect = null
        val expectationsAction = DiaryDispatcherContract.Action.ChangeStatement(value)

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
     * 条件：入力した文字を全て削除
     * 期待結果；
     * ・画面の値
     * 　　・何も値が変更されないこと
     * ・画面イベント
     * 　　・画面遷移イベントが発生すること
     * ・共通処理イベント
     * 　　・何もアクションが流れないこと
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