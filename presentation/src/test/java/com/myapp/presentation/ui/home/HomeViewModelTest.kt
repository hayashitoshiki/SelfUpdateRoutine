package com.myapp.presentation.ui.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.myapp.domain.model.entity.FfsReport
import com.myapp.domain.model.entity.MissionStatement
import com.myapp.domain.model.entity.Report
import com.myapp.domain.model.entity.WeatherReport
import com.myapp.domain.model.value.HeartScore
import com.myapp.domain.model.value.ReportDateTime
import com.myapp.domain.usecase.MissionStatementUseCase
import com.myapp.domain.usecase.ReportUseCase
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import java.time.LocalDateTime
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.*
import org.junit.rules.TestRule

/**
 * ホーム画面 ロジック仕様
 *
 */
class HomeViewModelTest {

    // region coroutine & LiveData用
    @ExperimentalCoroutinesApi
    private val coroutineDispatcher = TestCoroutineDispatcher()

    @ExperimentalCoroutinesApi
    private val testScope = TestCoroutineScope(coroutineDispatcher)

    @Rule
    @JvmField
    val rule: TestRule = InstantTaskExecutorRule()

    // endregion

    // region test date

    private lateinit var viewModel: HomeViewModel
    private lateinit var reportUseCase: ReportUseCase
    private lateinit var missionStatementUseCase: MissionStatementUseCase

    private val funeralList = listOf("弔辞：感謝されること", "人：優しい人間")
    private val purposeLife = "世界一楽しく生きること"
    private val constitutionList = listOf("迷ったときは楽しい方向へ", "常に楽しいを見つける努力を")
    private val missionStatement = MissionStatement(funeralList, purposeLife, constitutionList)

    private val today = LocalDateTime.now()
    private val yesterday = today.minusDays(1)
    private val ffsReportByToday = FfsReport(ReportDateTime(today), "a", "b", "c", "d")
    private val ffsReportByYesterday = FfsReport(ReportDateTime(yesterday), "a", "b", "c", "d")
    private val weatherReportByToday = WeatherReport(ReportDateTime(today), HeartScore(50), "e", "f")
    private val weatherReportByYesterday = WeatherReport(ReportDateTime(yesterday), HeartScore(50), "e", "f")
    private val reportByToday = Report(ffsReportByToday, weatherReportByToday)
    private val reportByYesterday = Report(ffsReportByYesterday, weatherReportByYesterday)
    private val reportListByToday = listOf(reportByYesterday, reportByToday)
    private val reportListByNotToday = listOf(reportByYesterday)
    private val reportListByEmpty = listOf<Report>()

    // endregion

    // region 初期化

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        Dispatchers.setMain(coroutineDispatcher)
    }

    private fun setMissionStatement() {
        missionStatementUseCase = mockk<MissionStatementUseCase>().also {
            coEvery { it.getMissionStatement() } returns missionStatement
        }
    }

    private fun setMissionStatementByNull() {
        missionStatementUseCase = mockk<MissionStatementUseCase>().also {
            coEvery { it.getMissionStatement() } returns null
        }
    }

    private fun setReportListByEmpty() {
        reportUseCase = mockk<ReportUseCase>().also {
            coEvery { it.getAllReport() } returns reportListByEmpty
        }
    }

    private fun setReportListByNotToday() {
        reportUseCase = mockk<ReportUseCase>().also {
            coEvery { it.getAllReport() } returns reportListByNotToday
        }
    }

    private fun setReportListByToday() {
        reportUseCase = mockk<ReportUseCase>().also {
            coEvery { it.getAllReport() } returns reportListByToday
        }
    }

    // レポート入力可能時間以前の時間を返却
    private fun setNowTimeByBefore18() {
        mockkStatic(LocalDateTime::class)
        every { LocalDateTime.now() } returns today.withHour(17)
    }

    // レポート入力可能時間以降の時間を返却
    private fun setNowTimeByAfter18() {
        mockkStatic(LocalDateTime::class)
        every { LocalDateTime.now() } returns today.withHour(18)
    }

    @ExperimentalCoroutinesApi
    @After
    fun tearDown() {
        Dispatchers.resetMain()
        coroutineDispatcher.cleanupTestCoroutines()
    }

    /**
     * 実行結果比較
     *
     * @param state Stateの期待値
     * @param effect Effectの期待値
     */
    @ExperimentalCoroutinesApi
    private fun result(state: HomeContract.State, effect: HomeContract.Effect?) = testScope.runBlockingTest {
        val resultState = viewModel.state.value
        val resultEffect = viewModel.effect.value

        // 比較
        Assert.assertEquals(resultState, state)
        if (resultEffect is HomeContract.Effect.ShowError) {
            val resultMessage = resultEffect.throwable.message
            val message = (effect as HomeContract.Effect.ShowError).throwable.message
            Assert.assertEquals(resultMessage, message)
        } else {
            Assert.assertEquals(resultEffect, effect)
        }
    }

    // endregion

    // region 初期表示ロジック

    /**
     * 初期表示
     *
     * 条件：まだレポートが登録されていない
     * 期待結果；
     * ・画面の値
     * 　　・メインコンテンツの表示タイプがレポート未設定
     * 　　・レポートリストの表示制御値がfalse
     * 　　・Fabの表示制御値がfalse
     * 　　・レポートリスト未登録文言の表示制御値がtrue
     * ・画面イベント
     * 　　　ー
     * ・業務ロジック
     * 　　　ー
     */
    @ExperimentalCoroutinesApi
    @Test
    fun initByNotReport() = testScope.runBlockingTest {

        // 期待結果
        setReportListByEmpty()
        setMissionStatementByNull()
        viewModel = HomeViewModel(reportUseCase, missionStatementUseCase)
        val expectationsEffect = null
        val expectationsState = viewModel.state.value!!.copy(
            isFabVisibility = false,
            isReportListVisibility = false,
            isNotReportListVisibility = true,
            mainContainerType = HomeFragmentMainContainerType.NotReport,
        )

        // 比較
        result(expectationsState, expectationsEffect)
    }

    /**
     * 初期表示
     *
     * 条件：レポートが登録されているが昨日の分でかつ、現在の時間が18:00より前でかつ、人生の目標が設定済み
     * 期待結果；
     * ・画面の値
     * 　　・メインコンテンツの表示タイプが目標一覧
     * 　　・レポートリストの表示制御値がtrue
     * 　　・Fabの表示制御値がtrue
     * 　　・レポートリスト未登録文言の表示制御値がfalse
     * ・画面イベント
     * 　　　ー
     * ・業務ロジック
     * 　　　ー
     */
    @ExperimentalCoroutinesApi
    @Test
    fun initByYesterdayReportAndMissionReport() = testScope.runBlockingTest {

        // 期待結果
        setReportListByNotToday()
        setNowTimeByBefore18()
        setMissionStatement()
        viewModel = HomeViewModel(reportUseCase, missionStatementUseCase)

        val expectationsEffect = null
        val expectationsState = viewModel.state.value!!.copy(
            isFabVisibility = true,
            isReportListVisibility = true,
            isNotReportListVisibility = false,
            mainContainerType = HomeFragmentMainContainerType.Vision
        )

        // 比較
        result(expectationsState, expectationsEffect)
    }

    /**
     * 初期表示
     *
     * 条件：レポートが登録されているが昨日の分でかつ、現在の時間が18:00より後
     * 期待結果；
     * ・画面の値
     * 　　・メインコンテンツの表示タイプがレポート入力
     * 　　・レポートリストの表示制御値がtrue
     * 　　・Fabの表示制御値がtrue
     * 　　・レポートリスト未登録文言の表示制御値がfalse
     * ・画面イベント
     * 　　　ー
     * ・業務ロジック
     * 　　　ー
     */
    @ExperimentalCoroutinesApi
    @Test
    fun initByYesterdayReportAndTimeAfter18() = testScope.runBlockingTest {

        // 期待結果
        setNowTimeByAfter18()
        setReportListByNotToday()
        setMissionStatementByNull()
        viewModel = HomeViewModel(reportUseCase, missionStatementUseCase)
        val expectationsEffect = null
        val expectationsState = viewModel.state.value!!.copy(
            isFabVisibility = true,
            isReportListVisibility = true,
            isNotReportListVisibility = false,
            mainContainerType = HomeFragmentMainContainerType.NotReport
        )

        // 比較
        result(expectationsState, expectationsEffect)
    }

    /**
     * 初期表示
     *
     * 条件：レポートが今日の分で登録されていてかつ、現在の時間が18:00より前
     * 期待結果；
     * ・画面の値
     * 　　・メインコンテンツの表示タイプが目標一覧
     * 　　・レポートリストの表示制御値がtrue
     * 　　・Fabの表示制御値がtrue
     * 　　・レポートリスト未登録文言の表示制御値がfalse
     * ・画面イベント
     * 　　　ー
     * ・業務ロジック
     * 　　　ー
     */
    @ExperimentalCoroutinesApi
    @Test
    fun initByReportAndBefore18() = testScope.runBlockingTest {

        // 期待結果
        setNowTimeByBefore18()
        setReportListByToday()
        setMissionStatementByNull()
        viewModel = HomeViewModel(reportUseCase, missionStatementUseCase)
        val expectationsEffect = null
        val expectationsState = viewModel.state.value!!.copy(
            isFabVisibility = true,
            isReportListVisibility = true,
            isNotReportListVisibility = false,
            mainContainerType = HomeFragmentMainContainerType.Vision
        )

        // 比較
        result(expectationsState, expectationsEffect)
    }

    /**
     * 初期表示
     *
     * 条件：レポートが今日の分で登録されていてかつ、現在の時間が18:00より後
     * 期待結果；
     * ・画面の値
     * 　　・メインコンテンツの表示タイプがレポート入力
     * 　　・レポートリストの表示制御値がtrue
     * 　　・Fabの表示制御値がtrue
     * 　　・レポートリスト未登録文言の表示制御値がfalse
     * ・画面イベント
     * 　　　ー
     * ・業務ロジック
     * 　　　ー
     */
    @ExperimentalCoroutinesApi
    @Test
    fun initByReportAndAfter18() = testScope.runBlockingTest {

        // 期待結果
        setNowTimeByAfter18()
        setReportListByToday()
        setMissionStatementByNull()
        viewModel = HomeViewModel(reportUseCase, missionStatementUseCase)
        val expectationsEffect = null
        val expectationsState = viewModel.state.value!!.copy(
            isFabVisibility = true,
            isReportListVisibility = true,
            isNotReportListVisibility = false,
            mainContainerType = HomeFragmentMainContainerType.Report
        )

        // 比較
        result(expectationsState, expectationsEffect)
    }

    // endregion

    // region 振り返りボタンタップ

    /**
     * 振り返りボタンタップ
     *
     * 条件：なし
     * 期待結果；
     * ・画面の値
     * 　　ー
     * ・画面イベント
     * 　　・振り返り画面遷移イベントが走ること
     * ・業務ロジック
     * 　　　ー
     */
    @ExperimentalCoroutinesApi
    @Test
    fun onClickReportButton() = testScope.runBlockingTest {

        // 期待結果
        setReportListByNotToday()
        setNowTimeByBefore18()
        setMissionStatement()
        viewModel = HomeViewModel(reportUseCase, missionStatementUseCase)
        val expectationsEffect = HomeContract.Effect.DiaryReportNavigation
        val expectationsState = viewModel.state.value!!.copy()

        // 実施
        viewModel.setEvent(HomeContract.Event.OnClickReportButton)

        // 比較
        result(expectationsState, expectationsEffect)
    }

    // endregion

    // region Fabボタン押下

    /**
     * Fabボタン押下
     *
     * 条件：Fabを閉じる
     * 期待結果：
     * ・画面の値
     * 　　・Fab制御値がfalseになること
     * ・画面イベント
     * 　　・Fab制御イベントが引数:falseで走ること
     * ・業務ロジック
     * 　　　ー
     */
    @ExperimentalCoroutinesApi
    @Test
    fun onClickFabByFalse() = testScope.runBlockingTest {

        // 期待結果
        setReportListByNotToday()
        setNowTimeByBefore18()
        setMissionStatement()
        viewModel = HomeViewModel(reportUseCase, missionStatementUseCase)
        val value = false
        val expectationsEffect = HomeContract.Effect.ChangeFabEnable(value)
        val expectationsState = viewModel.state.value!!.copy(isFabCheck = value)

        // 実施
        viewModel.setEvent(HomeContract.Event.OnClickFabButton)
        viewModel.setEvent(HomeContract.Event.OnClickFabButton)

        // 比較
        result(expectationsState, expectationsEffect)
    }

    /**
     * Fabボタン押下
     *
     * 条件：Fabを開く
     * 期待結果：
     * ・画面の値
     * 　　・Fab制御値がfalseになること
     * ・画面イベント
     * 　　・Fab制御イベントが引数:falseで走ること
     * ・業務ロジック
     * 　　　ー
     */
    @ExperimentalCoroutinesApi
    @Test
    fun onClickFabByTrue() = testScope.runBlockingTest {

        // 期待結果
        setReportListByNotToday()
        setNowTimeByBefore18()
        setMissionStatement()
        viewModel = HomeViewModel(reportUseCase, missionStatementUseCase)
        val value = true
        val expectationsEffect = HomeContract.Effect.ChangeFabEnable(value)
        val expectationsState = viewModel.state.value!!.copy(isFabCheck = value)

        // 実施
        viewModel.setEvent(HomeContract.Event.OnClickFabButton)

        // 比較
        result(expectationsState, expectationsEffect)
    }

    // endregion

    // region Fab_宣言ボタンタップ

    /**
     * Fab_格言ボタンタップ
     *
     * 条件：振り返りレポートあり
     * 期待結果；
     * ・画面の値
     * 　　ー
     * ・画面イベント
     * 　　・宣言一覧画面遷移イベントが走ること
     * ・業務ロジック
     * 　　　ー
     */
    @ExperimentalCoroutinesApi
    @Test
    fun onClickFabLearnButton() = testScope.runBlockingTest {

        // 期待結果
        setReportListByNotToday()
        setNowTimeByBefore18()
        setMissionStatement()
        viewModel = HomeViewModel(reportUseCase, missionStatementUseCase)
        val value = viewModel.state.value!!.reportList
        val expectationsEffect = HomeContract.Effect.LearnListNavigation(value)
        val expectationsState = viewModel.state.value!!.copy()

        // 実施
        viewModel.setEvent(HomeContract.Event.OnClickFabLearnButton)

        // 比較
        result(expectationsState, expectationsEffect)
    }

    /**
     * Fab_格言ボタンタップ
     *
     * 条件：振り返りレポートなし
     * 期待結果；
     * ・画面の値
     * 　　ー
     * ・画面イベント
     * 　　・エラー処理イベントが走ること
     * ・業務ロジック
     * 　　　ー
     */
    @ExperimentalCoroutinesApi
    @Test
    fun onClickFabLearnButtonByEmptyReport() = testScope.runBlockingTest {

        // 期待結果
        setReportListByEmpty()
        setNowTimeByBefore18()
        setMissionStatement()
        viewModel = HomeViewModel(reportUseCase, missionStatementUseCase)
        val value = NullPointerException("レポートリストがありません")
        val expectationsEffect = HomeContract.Effect.ShowError(value)
        val expectationsState = viewModel.state.value!!.copy()

        // 実施
        viewModel.setEvent(HomeContract.Event.OnClickFabLearnButton)

        // 比較
        result(expectationsState, expectationsEffect)
    }

    // endregion

    // region Fab_宣言ボタンタップ

    /**
     * Fab_宣言ボタンタップ
     *
     * 条件：振り返りレポートあり
     * 期待結果；
     * ・画面の値
     * 　　ー
     * ・画面イベント
     * 　　・宣言一覧画面遷移イベントが走ること
     * ・業務ロジック
     * 　　　ー
     */
    @ExperimentalCoroutinesApi
    @Test
    fun onClickFabStatementButton() = testScope.runBlockingTest {

        // 期待結果
        setReportListByNotToday()
        setNowTimeByBefore18()
        setMissionStatement()
        viewModel = HomeViewModel(reportUseCase, missionStatementUseCase)
        val value = viewModel.state.value!!.reportList
        val expectationsEffect = HomeContract.Effect.StatementListNavigation(value)
        val expectationsState = viewModel.state.value!!.copy()

        // 実施
        viewModel.setEvent(HomeContract.Event.OnClickFabStatementButton)

        // 比較
        result(expectationsState, expectationsEffect)
    }

    /**
     * Fab_宣言ボタンタップ
     *
     * 条件：振り返りレポートなし
     * 期待結果；
     * ・画面の値
     * 　　ー
     * ・画面イベント
     * 　　・エラー処理イベントが走ること
     * ・業務ロジック
     * 　　　ー
     */
    @ExperimentalCoroutinesApi
    @Test
    fun onClickFabStatementButtonByEmptyReport() = testScope.runBlockingTest {

        // 期待結果
        setReportListByEmpty()
        setNowTimeByBefore18()
        setMissionStatement()
        viewModel = HomeViewModel(reportUseCase, missionStatementUseCase)
        val value = NullPointerException("レポートリストがありません")
        val expectationsEffect = HomeContract.Effect.ShowError(value)
        val expectationsState = viewModel.state.value!!.copy()

        // 実施
        viewModel.setEvent(HomeContract.Event.OnClickFabStatementButton)

        // 比較
        result(expectationsState, expectationsEffect)
    }

    // endregion


    // region 振り返りカードタップ

    /**
     * 振り返りカードタップ
     *
     * 条件：なし
     * 期待結果；
     * ・画面の値
     * 　　ー
     * ・画面イベント
     * 　　・Eventの引数で渡した値で振り返り詳細画面遷移イベントが走ること
     * ・業務ロジック
     * 　　　ー
     */
    @ExperimentalCoroutinesApi
    @Test
    fun onClickReportCard() = testScope.runBlockingTest {

        // 期待結果
        setReportListByNotToday()
        setNowTimeByBefore18()
        setMissionStatement()
        viewModel = HomeViewModel(reportUseCase, missionStatementUseCase)
        val value = reportByToday
        val expectationsEffect = HomeContract.Effect.ReportDetailListNavigation(value)
        val expectationsState = viewModel.state.value!!.copy()

        // 実施
        viewModel.setEvent(HomeContract.Event.OnClickReportCard(value))

        // 比較
        result(expectationsState, expectationsEffect)
    }

    // endregion

    // region 画面破棄

    /**
     * 画面破棄
     *
     * 条件：なし
     * 期待結果；
     * ・画面の値
     * 　　・Fab制御値がfalseになること
     * ・画面イベント
     * 　　・画面破棄イベントが走ること
     * ・業務ロジック
     * 　　　ー
     */
    @ExperimentalCoroutinesApi
    @Test
    fun onDestroyView() = testScope.runBlockingTest {

        // 期待結果
        setReportListByNotToday()
        setNowTimeByBefore18()
        setMissionStatement()
        viewModel = HomeViewModel(reportUseCase, missionStatementUseCase)
        val value = false
        val expectationsEffect = HomeContract.Effect.OnDestroyView
        val expectationsState = viewModel.state.value!!.copy(isFabCheck = value)

        // 実施
        viewModel.setEvent(HomeContract.Event.OnDestroyView)

        // 比較
        result(expectationsState, expectationsEffect)
    }

    // endregion
}
