package com.myapp.presentation.ui.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.myapp.domain.model.entity.FfsReport
import com.myapp.domain.model.entity.MissionStatement
import com.myapp.domain.model.entity.Report
import com.myapp.domain.model.entity.WeatherReport
import com.myapp.domain.model.value.HeartScore
import com.myapp.domain.model.value.ReportDateTime
import com.myapp.domain.usecase.MissionStatementUseCase
import com.myapp.domain.usecase.ReportUseCase
import com.nhaarman.mockito_kotlin.mock
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.*
import org.junit.rules.TestRule
import java.time.LocalDateTime

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

    private fun setMockObserver() {
        val observerString = mock<Observer<String>>()
        val observerInt = mock<Observer<Int>>()
        val observerReport = mock<Observer<List<Report>>>()
        val observerHomeFragmentMainContainerType = mock<Observer<HomeFragmentMainContainerType<*>>>()
        viewModel.factComment.observeForever(observerString)
        viewModel.findComment.observeForever(observerString)
        viewModel.learnComment.observeForever(observerString)
        viewModel.statementComment.observeForever(observerString)
        viewModel.assessmentInputImg.observeForever(observerInt)
        viewModel.reasonComment.observeForever(observerString)
        viewModel.improveComment.observeForever(observerString)
        viewModel.report.observeForever(observerReport)
        viewModel.mainContainerType.observeForever(observerHomeFragmentMainContainerType)
        viewModel.missionStatement.observeForever(observerString)
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
        every {
            LocalDateTime.now()
        } returns today.withHour(17)
    }

    // レポート入力可能時間以降の時間を返却
    private fun setNowTimeByAfter18() {
        mockkStatic(LocalDateTime::class)
        every {
            LocalDateTime.now()
        } returns today.withHour(18)
    }

    @ExperimentalCoroutinesApi
    @After
    fun tearDown() {
        Dispatchers.resetMain()
        coroutineDispatcher.cleanupTestCoroutines()
    }


    // endregion

    // region 初期表示ロジック
    /**
     * 初期表示
     *
     * 条件：まだレポートが登録されていない
     * 期待結果：メインコンテンツの表示タイプがレポート未設定
     */
    @ExperimentalCoroutinesApi
    @Test
    fun initByNotReport() = testScope.runBlockingTest {
        setReportListByEmpty()
        setMissionStatementByNull()
        viewModel = HomeViewModel(reportUseCase, missionStatementUseCase)
        setMockObserver()
        val result = viewModel.mainContainerType.value is HomeFragmentMainContainerType.NotReport
        Assert.assertEquals(true, result)
    }

    /**
     * 初期表示
     *
     * 条件：レポートが登録されているが昨日の分でかつ、時間が18:00より前でかつ、人生の目標が設定済み
     * 期待結果：
     * ・メインコンテンツの表示タイプが目標一覧
     * ・人生の目標文言が設定されること
     */
    @ExperimentalCoroutinesApi
    @Test
    fun initByYesterdayReportAndMissionReport() = testScope.runBlockingTest {
        setReportListByNotToday()
        setNowTimeByBefore18()
        setMissionStatement()
        viewModel = HomeViewModel(reportUseCase, missionStatementUseCase)
        setMockObserver()
        val result = viewModel.mainContainerType.value is HomeFragmentMainContainerType.Vision
        Assert.assertEquals(true, result)
    }

    /**
     * 初期表示
     *
     * 条件：レポートが登録されているが昨日の分でかつ、時間が18:00より前でかつ、人生の目標が未設定
     * 期待結果：
     * ・メインコンテンツの表示タイプが目標一覧
     * ・人生の目標文言が設定されないこと
     */
    @Test
    fun initByYesterdayReportAndNotMissionStatement() {
        setNowTimeByBefore18()
        setReportListByNotToday()
        setMissionStatementByNull()
        viewModel = HomeViewModel(reportUseCase, missionStatementUseCase)
        setMockObserver()
        val result = viewModel.mainContainerType.value is HomeFragmentMainContainerType.Vision
        Assert.assertEquals(true, result)
    }

    /**
     * 初期表示
     *
     * 条件：レポートが登録されているが昨日の分でかつ、時間が18:00より後
     * 期待結果：メインコンテンツの表示タイプが目標一覧
     */
    @Test
    fun initByYesterdayReportAndTimeAfter18() {
        setNowTimeByAfter18()
        setReportListByNotToday()
        setMissionStatementByNull()
        viewModel = HomeViewModel(reportUseCase, missionStatementUseCase)
        setMockObserver()
        val result = viewModel.mainContainerType.value is HomeFragmentMainContainerType.NotReport
        Assert.assertEquals(true, result)
    }

    /**
     * 初期表示
     *
     * 条件：レポートが今日の分で登録されていてかつ、時間が18:00より前
     * 期待結果：メインコンテンツの表示タイプが目標一覧
     */
    @Test
    fun initByReportAndBefore18() {
        setNowTimeByBefore18()
        setReportListByToday()
        setMissionStatementByNull()
        viewModel = HomeViewModel(reportUseCase, missionStatementUseCase)
        setMockObserver()
        val result = viewModel.mainContainerType.value is HomeFragmentMainContainerType.Vision
        Assert.assertEquals(true, result)
    }

    /**
     * 初期表示
     *
     * 条件：レポートが今日の分で登録されていてかつ、時間が18:00より後
     * 期待結果：メインコンテンツの表示タイプが今日のレポート
     */
    @Test
    fun initByReportAndAfter18() {
        setNowTimeByAfter18()
        setReportListByToday()
        setMissionStatementByNull()
        viewModel = HomeViewModel(reportUseCase, missionStatementUseCase)
        setMockObserver()
        val result = viewModel.mainContainerType.value is HomeFragmentMainContainerType.Report
        Assert.assertEquals(true, result)
    }

    // endregion

}