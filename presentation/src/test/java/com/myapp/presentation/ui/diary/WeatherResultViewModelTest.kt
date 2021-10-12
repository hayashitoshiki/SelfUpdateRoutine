package com.myapp.presentation.ui.diary

import com.myapp.domain.dto.AllReportInputDto
import com.myapp.domain.usecase.ReportUseCase
import com.myapp.presentation.utils.base.Status
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
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
 * 振り返り_天気比喩振り返り確認画面　画面ロジック仕様
 */
class WeatherResultViewModelTest {

    private val state = WeatherResultContract.State()
    private lateinit var ffsFactViewModel: WeatherResultViewModel
    private lateinit var reportUseCase: ReportUseCase
    private var resultAction: DiaryDispatcherContract.Action? = null

    @ExperimentalCoroutinesApi
    private val coroutineDispatcher = TestCoroutineDispatcher()

    @ExperimentalCoroutinesApi
    private val testScope = TestCoroutineScope(coroutineDispatcher)

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        Dispatchers.setMain(coroutineDispatcher)
        reportUseCase = mockk<ReportUseCase>().also {
            coEvery { it.saveReport(any()) } returns Unit
        }
        ffsFactViewModel = WeatherResultViewModel(reportUseCase)

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
        state: WeatherResultContract.State,
        effect: WeatherResultContract.Effect?,
        action: DiaryDispatcherContract.Action?
    ) = testScope.runBlockingTest {
        val resultState = ffsFactViewModel.state.value
        var resultEffect: WeatherResultContract.Effect? = null
        ffsFactViewModel.effect.onEach { resultEffect = it }
            .launchIn(testScope)

        // 比較
        assertEquals(resultState, state)
        assertEquals(resultAction, action)

        if (resultEffect is WeatherResultContract.Effect.SaveResult) {
            val resultStatus = (resultEffect as WeatherResultContract.Effect.SaveResult).status
            if (resultStatus is Status.Failure) {
                val status = ((effect as WeatherResultContract.Effect.SaveResult).status as Status.Failure)
                assertEquals(resultStatus.throwable.message, status.throwable.message)
            } else {
                assertEquals(resultEffect, effect)
            }
        } else {
            assertEquals(resultEffect, effect)
        }
    }

    private suspend fun diaryDispatcherFactAction(expectationsState: WeatherResultContract.State): Pair<WeatherResultContract.State, DiaryDispatcherContract.Action> {
        val value = "fact"
        val action = DiaryDispatcherContract.Action.ChangeFact(value)
        DiaryDispatcher.setActions(action)
        return Pair(expectationsState.copy(fact = value), action)
    }

    private suspend fun diaryDispatcherFindAction(expectationsState: WeatherResultContract.State): Pair<WeatherResultContract.State, DiaryDispatcherContract.Action> {
        val value = "find"
        val action = DiaryDispatcherContract.Action.ChangeFind(value)
        DiaryDispatcher.setActions(action)
        return Pair(expectationsState.copy(find = value), action)
    }

    private suspend fun diaryDispatcherLearnAction(expectationsState: WeatherResultContract.State): Pair<WeatherResultContract.State, DiaryDispatcherContract.Action> {
        val value = "learn"
        val action = DiaryDispatcherContract.Action.ChangeLearn(value)
        DiaryDispatcher.setActions(action)
        return Pair(expectationsState.copy(learn = value), action)
    }

    private suspend fun diaryDispatcherStatementAction(expectationsState: WeatherResultContract.State): Pair<WeatherResultContract.State, DiaryDispatcherContract.Action> {
        val value = "statement"
        val action = DiaryDispatcherContract.Action.ChangeStatement(value)
        DiaryDispatcher.setActions(action)
        return Pair(expectationsState.copy(statement = value), action)
    }

    private suspend fun diaryDispatcherAssessmentAction(expectationsState: WeatherResultContract.State): Pair<WeatherResultContract.State, DiaryDispatcherContract.Action> {
        val value = 50f
        val action = DiaryDispatcherContract.Action.ChangeAssessment(value)
        DiaryDispatcher.setActions(action)
        return Pair(expectationsState.copy(assessment = (value * 100).toInt()), action)
    }

    private suspend fun diaryDispatcherReasonAction(expectationsState: WeatherResultContract.State): Pair<WeatherResultContract.State, DiaryDispatcherContract.Action> {
        val value = "reason"
        val action = DiaryDispatcherContract.Action.ChangeReason(value)
        DiaryDispatcher.setActions(action)
        return Pair(expectationsState.copy(reason = value), action)
    }

    private suspend fun diaryDispatcherImproveAction(expectationsState: WeatherResultContract.State): Pair<WeatherResultContract.State, DiaryDispatcherContract.Action> {
        val value = "improve"
        val action = DiaryDispatcherContract.Action.ChangeImprove(value)
        DiaryDispatcher.setActions(action)
        return Pair(expectationsState.copy(improve = value), action)
    }


    // region 保存ボタン押下

    /**
     * 保存ボタン押下
     *
     * 条件：全項目入力なし
     * 期待結果；
     * ・画面の値
     * 　　・何も変更されないこと
     * ・画面イベント
     * 　　・事実値入力エラーのイベントが発生すること
     * ・共通処理イベント
     * 　　・入力した文字を引数に事実入力アクションが流れること
     * ・業務ロジック
     * 　・保存処理が呼ばれないこと
     */
    @ExperimentalCoroutinesApi
    @Test
    fun onClickNextButtonByAllNull() = testScope.runBlockingTest {

        // 期待結果
        val expectationsState = state.copy()
        val status = Status.Failure(IllegalAccessError("事実データが入っていません"))
        val expectationsEffect = WeatherResultContract.Effect.SaveResult(status)
        val expectationsAction = null

        // 実施
        ffsFactViewModel.setEvent(WeatherResultContract.Event.OnClickSaveButton)

        // 比較
        result(expectationsState, expectationsEffect, expectationsAction)
        coVerify(exactly = 0) { (reportUseCase).saveReport(any()) }
    }

    /**
     * 保存ボタン押下
     *
     * 条件：事実値のみ未入力
     * 期待結果；
     * ・画面の値
     * 　　ー
     * ・画面イベント
     * 　　・入力イベントが異常系で発生すること
     * ・共通処理イベント
     * 　　ー
     * ・業務ロジック
     * 　・保存処理が呼ばれないこと
     */
    @ExperimentalCoroutinesApi
    @Test
    fun onClickNextButtonByFactNull() = testScope.runBlockingTest {

        // 期待結果
        val status = Status.Failure(IllegalAccessError("事実データが入っていません"))
        var stateCopy = diaryDispatcherFindAction(state).first
        stateCopy = diaryDispatcherLearnAction(stateCopy).first
        stateCopy = diaryDispatcherStatementAction(stateCopy).first
        stateCopy = diaryDispatcherAssessmentAction(stateCopy).first
        stateCopy = diaryDispatcherReasonAction(stateCopy).first
        val expectationsEffect = WeatherResultContract.Effect.SaveResult(status)
        val (expectationsState, expectationsAction) = diaryDispatcherImproveAction(stateCopy)

        // 実施
        ffsFactViewModel.setEvent(WeatherResultContract.Event.OnClickSaveButton)

        // 比較
        result(expectationsState, expectationsEffect, expectationsAction)
        coVerify(exactly = 0) { (reportUseCase).saveReport(any()) }
    }

    /**
     * 保存ボタン押下
     *
     * 条件：発見値のみ未入力
     * 期待結果；
     * ・画面の値
     * 　　ー
     * ・画面イベント
     * 　　・入力イベントが異常系で発生すること
     * ・共通処理イベント
     * 　　ー
     * ・業務ロジック
     * 　・保存処理が呼ばれないこと
     */
    @ExperimentalCoroutinesApi
    @Test
    fun onClickNextButtonByFindNull() = testScope.runBlockingTest {

        // 期待結果
        val status = Status.Failure(IllegalAccessError("発見データが入っていません"))
        var stateCopy = diaryDispatcherFactAction(state).first
        stateCopy = diaryDispatcherLearnAction(stateCopy).first
        stateCopy = diaryDispatcherStatementAction(stateCopy).first
        stateCopy = diaryDispatcherAssessmentAction(stateCopy).first
        stateCopy = diaryDispatcherReasonAction(stateCopy).first
        val expectationsEffect = WeatherResultContract.Effect.SaveResult(status)
        val (expectationsState, expectationsAction) = diaryDispatcherImproveAction(stateCopy)

        // 実施
        ffsFactViewModel.setEvent(WeatherResultContract.Event.OnClickSaveButton)

        // 比較
        result(expectationsState, expectationsEffect, expectationsAction)
        coVerify(exactly = 0) { (reportUseCase).saveReport(any()) }
    }

    /**
     * 保存ボタン押下
     *
     * 条件：教訓値のみ未入力
     * 期待結果；
     * ・画面の値
     * 　　ー
     * ・画面イベント
     * 　　・入力イベントが異常系で発生すること
     * ・共通処理イベント
     * 　　ー
     * ・業務ロジック
     * 　・保存処理が呼ばれないこと
     */
    @ExperimentalCoroutinesApi
    @Test
    fun onClickNextButtonByLearnNull() = testScope.runBlockingTest {

        // 期待結果
        val status = Status.Failure(IllegalAccessError("学びデータが入っていません"))
        var stateCopy = diaryDispatcherFactAction(state).first
        stateCopy = diaryDispatcherFindAction(stateCopy).first
        stateCopy = diaryDispatcherStatementAction(stateCopy).first
        stateCopy = diaryDispatcherAssessmentAction(stateCopy).first
        stateCopy = diaryDispatcherReasonAction(stateCopy).first
        val (expectationsState, expectationsAction) = diaryDispatcherImproveAction(stateCopy)
        val expectationsEffect = WeatherResultContract.Effect.SaveResult(status)

        // 実施
        ffsFactViewModel.setEvent(WeatherResultContract.Event.OnClickSaveButton)

        // 比較
        result(expectationsState, expectationsEffect, expectationsAction)
        coVerify(exactly = 0) { (reportUseCase).saveReport(any()) }
    }

    /**
     * 保存ボタン押下
     *
     * 条件：宣言のみ未入力
     * 期待結果；
     * ・画面の値
     * 　　ー
     * ・画面イベント
     * 　　・入力イベントが異常系で発生すること
     * ・共通処理イベント
     * 　　ー
     * ・業務ロジック
     * 　・保存処理が入力した値で呼ばれること
     */
    @ExperimentalCoroutinesApi
    @Test
    fun onClickNextButtonByStatementNull() = testScope.runBlockingTest {

        // 期待結果
        val status = Status.Failure(IllegalAccessError("宣言データが入っていません"))
        var stateCopy = diaryDispatcherFactAction(state).first
        stateCopy = diaryDispatcherFindAction(stateCopy).first
        stateCopy = diaryDispatcherLearnAction(stateCopy).first
        stateCopy = diaryDispatcherAssessmentAction(stateCopy).first
        stateCopy = diaryDispatcherReasonAction(stateCopy).first
        val expectationsEffect = WeatherResultContract.Effect.SaveResult(status)
        val (expectationsState, expectationsAction) = diaryDispatcherImproveAction(stateCopy)

        // 実行
        ffsFactViewModel.setEvent(WeatherResultContract.Event.OnClickSaveButton)

        // 比較
        result(expectationsState, expectationsEffect, expectationsAction)
        coVerify(exactly = 0) { (reportUseCase).saveReport(any()) }
    }

    /**
     * 保存ボタン押下
     *
     * 条件：理由のみ未入力
     * 期待結果；
     * ・画面の値
     * 　　ー
     * ・画面イベント
     * 　　・入力イベントが理由系で発生すること
     * ・共通処理イベント
     * 　　ー
     * ・業務ロジック
     * 　・保存処理が呼ばれないこと
     */
    @ExperimentalCoroutinesApi
    @Test
    fun onClickNextButtonByReasonNull() = testScope.runBlockingTest {

        // 期待結果
        val status = Status.Failure(IllegalAccessError("理由データが入っていません"))
        var stateCopy = diaryDispatcherFactAction(state).first
        stateCopy = diaryDispatcherFindAction(stateCopy).first
        stateCopy = diaryDispatcherLearnAction(stateCopy).first
        stateCopy = diaryDispatcherStatementAction(stateCopy).first
        stateCopy = diaryDispatcherAssessmentAction(stateCopy).first
        val expectationsEffect = WeatherResultContract.Effect.SaveResult(status)
        val (expectationsState, expectationsAction) = diaryDispatcherImproveAction(stateCopy)

        // 実行
        ffsFactViewModel.setEvent(WeatherResultContract.Event.OnClickSaveButton)

        // 比較
        result(expectationsState, expectationsEffect, expectationsAction)
        coVerify(exactly = 0) { (reportUseCase).saveReport(any()) }
    }

    /**
     * 保存ボタン押下
     *
     * 条件：改善案のみ未入力
     * 期待結果；
     * ・画面の値
     * 　　ー
     * ・画面イベント
     * 　　・入力イベントが異常系で発生すること
     * ・共通処理イベント
     * 　　ー
     * ・業務ロジック
     * 　・保存処理が呼ばれないこと
     */
    @ExperimentalCoroutinesApi
    @Test
    fun onClickNextButtonByImproveNull() = testScope.runBlockingTest {

        // 期待結果
        val status = Status.Failure(IllegalAccessError("改善案データが入っていません"))
        var stateCopy = diaryDispatcherFactAction(state).first
        stateCopy = diaryDispatcherFindAction(stateCopy).first
        stateCopy = diaryDispatcherLearnAction(stateCopy).first
        stateCopy = diaryDispatcherStatementAction(stateCopy).first
        stateCopy = diaryDispatcherAssessmentAction(stateCopy).first
        val expectationsEffect = WeatherResultContract.Effect.SaveResult(status)
        val (expectationsState, expectationsAction) = diaryDispatcherReasonAction(stateCopy)

        // 実行
        ffsFactViewModel.setEvent(WeatherResultContract.Event.OnClickSaveButton)

        // 比較
        result(expectationsState, expectationsEffect, expectationsAction)
        coVerify(exactly = 0) { (reportUseCase).saveReport(any()) }
    }

    /**
     * 保存ボタン押下
     *
     * 条件：全項目入力済
     * 期待結果；
     * ・画面の値
     * 　　ー
     * ・画面イベント
     * 　　・入力イベントが正常系で発生すること
     * ・共通処理イベント
     * 　　ー
     * ・業務ロジック
     * 　・保存処理が入力した値で呼ばれること
     */
    @ExperimentalCoroutinesApi
    @Test
    fun onClickNextButtonByAllInput() = testScope.runBlockingTest {

        // 期待結果
        val status = Status.Success(null)
        var stateCopy = diaryDispatcherFactAction(state).first
        stateCopy = diaryDispatcherFindAction(stateCopy).first
        stateCopy = diaryDispatcherLearnAction(stateCopy).first
        stateCopy = diaryDispatcherStatementAction(stateCopy).first
        stateCopy = diaryDispatcherAssessmentAction(stateCopy).first
        stateCopy = diaryDispatcherReasonAction(stateCopy).first
        stateCopy = diaryDispatcherImproveAction(stateCopy).first
        val expectationsEffect = WeatherResultContract.Effect.SaveResult(status)
        val (expectationsState, expectationsAction) = diaryDispatcherImproveAction(stateCopy)
        val allReportInputDto = AllReportInputDto(
            stateCopy.fact, stateCopy.find, stateCopy.learn, stateCopy.statement, stateCopy.assessment, stateCopy.reason,
            stateCopy.improve
        )

        // 実行
        ffsFactViewModel.setEvent(WeatherResultContract.Event.OnClickSaveButton)

        // 比較
        result(expectationsState, expectationsEffect, expectationsAction)
        coVerify(exactly = 1) { (reportUseCase).saveReport(allReportInputDto) }
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
        val value2 = (value * 100).toInt()
        val action = DiaryDispatcherContract.Action.ChangeAssessment(value)
        val expectationsState = state.copy(assessment = value2)
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
        val expectationsState = state.copy(reason = value)
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
        val expectationsState = state.copy(improve = value)
        val expectationsEffect = null

        // 実施
        DiaryDispatcher.setActions(action)

        // 比較
        result(expectationsState, expectationsEffect, action)
    }

    // endregion
}