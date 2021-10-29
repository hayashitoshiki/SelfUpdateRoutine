package com.myapp.domain.usecase

import com.myapp.domain.dto.AllReportInputDto
import com.myapp.domain.model.entity.Account
import com.myapp.domain.model.entity.FfsReport
import com.myapp.domain.model.entity.Report
import com.myapp.domain.model.entity.WeatherReport
import com.myapp.domain.model.value.HeartScore
import com.myapp.domain.model.value.ReportDateTime
import com.myapp.domain.repository.LocalReportRepository
import com.myapp.domain.repository.LocalSettingRepository
import com.myapp.domain.repository.RemoteAccountRepository
import com.myapp.domain.repository.RemoteReportRepository
import com.myapp.domain.translator.ReportTranslator
import io.mockk.*
import java.time.LocalDateTime
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

/**
 * レポート関連機能 ロジック仕様
 *
 */
class ReportUseCaseImpTest {

    // mock
    private lateinit var reportUseCaseImp: ReportUseCaseImp
    private lateinit var localSettingRepository: LocalSettingRepository
    private lateinit var localReportRepository: LocalReportRepository
    private lateinit var remoteReportRepository: RemoteReportRepository
    private lateinit var remoteAccountRepository: RemoteAccountRepository

    // region TestData

    private val yesterdayDateTime = LocalDateTime.now().minusDays(1)
    private val lastYearDateTime = LocalDateTime.now().minusYears(1)
    private val nowDateTime = LocalDateTime.now()
    private val reportDto = AllReportInputDto("fact", "find", "learn", "statement", 40, "reason", "improve")
    private val report = ReportTranslator.allReportConvert(reportDto)
    private val lastYearFfsReport = FfsReport(ReportDateTime(lastYearDateTime), "fact", "find", "learn", "statement")
    private val lastYearWeatherReport = WeatherReport(ReportDateTime(lastYearDateTime), HeartScore(50),"reason", "improve")
    private val lastYearDateTimeReport = Report(lastYearFfsReport, lastYearWeatherReport)
    private val reportList = listOf(report)
    private val lastYearDateTimeReportList = listOf(lastYearDateTimeReport)


    private val yesterdayDateTimeFfsReport = FfsReport(ReportDateTime(yesterdayDateTime), "fact", "find", "learn", "statement")
    private val yesterdayDateTimeWeatherReport = WeatherReport(ReportDateTime(yesterdayDateTime), HeartScore(50),"reason", "improve")
    private val yesterdayDateTimeReport = Report(yesterdayDateTimeFfsReport, yesterdayDateTimeWeatherReport)
    private val remoteReportListByNotToday = listOf(yesterdayDateTimeReport)
    private val todayFfsReport = FfsReport(ReportDateTime(nowDateTime), "fact", "find", "learn", "statement")
    private val todayWeatherReport = WeatherReport(ReportDateTime(nowDateTime), HeartScore(50),"reason", "improve")
    private val todayReport = Report(todayFfsReport, todayWeatherReport)
    private val remoteReportListByToday = listOf(yesterdayDateTimeReport, todayReport)
    private val account = Account("test", "test123@com.je")

    // endregion

    @Before
    fun setUp() {
        mockkStatic(LocalDateTime::class)
        every { LocalDateTime.now() } returns nowDateTime
        localSettingRepository = mockk<LocalSettingRepository>().also {
            coEvery { it.setLastReportSaveDateTime(any()) } returns Unit
        }
        localReportRepository = mockk<LocalReportRepository>().also {
            coEvery { it.saveReport(any()) } returns Unit
            coEvery { it.getAllReport() } returns reportList
        }

    }

    // データが未登録
    private fun setNotData() {
        localSettingRepository = mockk<LocalSettingRepository>().also {
            coEvery { it.setLastReportSaveDateTime(any()) } returns Unit
        }
        localReportRepository = mockk<LocalReportRepository>().also {
            coEvery { it.saveReport(any()) } returns Unit
            coEvery { it.getAllReport() } returns listOf()
            coEvery { it.getLastSaveDate() } returns null
        }
    }

    // 昨日のデータが登録済み
    private fun setNotTodayData() {
        localSettingRepository = mockk<LocalSettingRepository>().also {
            coEvery { it.setLastReportSaveDateTime(any()) } returns Unit
        }
        localReportRepository = mockk<LocalReportRepository>().also {
            coEvery { it.saveReport(any()) } returns Unit
            coEvery { it.getAllReport() } returns lastYearDateTimeReportList
            coEvery { it.getLastSaveDate() } returns lastYearDateTimeReportList.last().ffsReport.dataTime.date
        }
    }


    // ログイン状態でかつ当日のデータが登録済み
    private fun setSignInAndToday() {
        remoteReportRepository = mockk<RemoteReportRepository>().also {
            coEvery { it.saveReport(any(), any()) } returns Unit
            coEvery { it.getReportByAfterDate(any(), any()) } returns remoteReportListByToday
        }
        remoteAccountRepository = mockk<RemoteAccountRepository>().also {
            coEvery { it.autoAuth() } returns true
            coEvery { it.getAccountDetail() } returns account
        }
    }

    // ログイン状態でかつ当日のデータが未登録
    private fun setSignInAndNotToday() {
        remoteReportRepository = mockk<RemoteReportRepository>().also {
            coEvery { it.saveReport(any(), any()) } returns Unit
            coEvery { it.getReportByAfterDate(any(), any()) } returns remoteReportListByNotToday
        }
        remoteAccountRepository = mockk<RemoteAccountRepository>().also {
            coEvery { it.autoAuth() } returns true
            coEvery { it.getAccountDetail() } returns account
        }
    }

    // ログアウト状態
    private fun setSignOut() {
        remoteReportRepository = mockk<RemoteReportRepository>().also {
            coEvery { it.saveReport(any(), any()) } returns Unit
        }
        remoteAccountRepository = mockk<RemoteAccountRepository>().also {
            coEvery { it.autoAuth() } returns false
            coEvery { it.getAccountDetail() } returns null
        }
    }

    private fun initViewMode() {
        reportUseCaseImp = ReportUseCaseImp(localReportRepository, localSettingRepository, remoteReportRepository, remoteAccountRepository)
    }

    @After
    fun tearDown() {
    }

    // region 登録

    /**
     * レポート登録
     *
     * 条件：ログイン状態
     * 期待結果
     * ・dtoが正しく変換され、登録されること
     * ・リモートデータベースへの登録処理が１回呼ばれること
     */
    @Test
    fun saveReportBySignIn() = runBlocking {
        setNotTodayData()
        setSignInAndNotToday()
        initViewMode()
        reportUseCaseImp.saveReport(reportDto)
        val act = ReportTranslator.allReportConvert(reportDto)
        coVerify(exactly = 1) { (localSettingRepository).setLastReportSaveDateTime(act.ffsReport.dataTime.date) }
        coVerify(exactly = 1) { (localReportRepository).saveReport(act) }
        coVerify(exactly = 1) { (remoteReportRepository).saveReport(listOf(act), account.email) }
    }

    /**
     * レポート登録
     *
     * 条件：ログイン状態でかつ既にリモートに当日のレポートが登録済み
     * 期待結果
     * ・リモートから取得したデータでローカルデータベースへの登録処理が取得した分呼ばれること
     * ・リモートデータベースへの登録処理が呼ばれないこと
     */
    @Test
    fun saveReportBySignInAndToday() = runBlocking {
        setNotTodayData()
        setSignInAndToday()
        initViewMode()
        reportUseCaseImp.saveReport(reportDto)
        val act = ReportTranslator.allReportConvert(reportDto)
        coVerify(exactly = 1) { (localSettingRepository).setLastReportSaveDateTime(act.ffsReport.dataTime.date) }
        coVerify(exactly = 0) { (localReportRepository).saveReport(act) }
        coVerify(exactly = 0) { (remoteReportRepository).saveReport(listOf(act), account.email) }
    }

    /**
     * レポート登録
     *
     * 条件：ログイン状態でかつリモートに当日は含まないレポートが登録済み
     * 期待結果
     * ・dtoが正しく変換され、登録されること
     * ・リモートから取得したデータでローカルデータベースへの登録処理が取得した分呼ばれること
     * ・リモートデータベースへの登録処理が呼ばれないこと
     */
    @Test
    fun saveReportBySignInAndNotToday() = runBlocking {
        setNotTodayData()
        setSignInAndNotToday()
        initViewMode()
        reportUseCaseImp.saveReport(reportDto)
        val act = ReportTranslator.allReportConvert(reportDto)
        coVerify(exactly = 1) { (localSettingRepository).setLastReportSaveDateTime(act.ffsReport.dataTime.date) }
        coVerify(exactly = 1) { (localReportRepository).saveReport(act) }
        for (report in remoteReportListByNotToday) {
            coVerify(exactly = 1) { (localReportRepository).saveReport(report) }
        }
        coVerify(exactly = 1) { (remoteReportRepository).saveReport(listOf(act), account.email) }
    }

    /**
     * レポート登録
     *
     * 条件：ログイン状態でかつリモートに昨日のレポートが登録済みでかつローカルがまだ未登録
     * 期待結果
     * ・リモートから取得したデータでローカルデータベースへの登録処理が取得した分呼ばれること
     * ・リモートデータベースへの登録処理が呼ばれないこと
     * ・dtoが正しく変換され、登録されること
     */
    @Test
    fun saveReportBySignInAndNotTodayAndLocalNotData() = runBlocking {
        setNotData()
        setSignInAndNotToday()
        initViewMode()
        reportUseCaseImp.saveReport(reportDto)
        val act = ReportTranslator.allReportConvert(reportDto)
        coVerify(exactly = 1) { (localSettingRepository).setLastReportSaveDateTime(act.ffsReport.dataTime.date) }
        coVerify(exactly = 1) { (localReportRepository).saveReport(act) }
        for (report in remoteReportListByNotToday) {
            coVerify(exactly = 1) { (localReportRepository).saveReport(report) }
        }
        coVerify(exactly = 1) { (remoteReportRepository).saveReport(listOf(act), account.email) }
    }

    /**
     * レポート登録
     *
     * 条件：ログアウト状態
     * 期待結果
     * ・dtoが正しく変換され、登録されること
     * ・リモートデータベースへの登録処理が１回呼ばれないこと
     */
    @Test
    fun saveReportBySignOut() = runBlocking {
        setSignOut()
        initViewMode()
        reportUseCaseImp.saveReport(reportDto)
        val act = ReportTranslator.allReportConvert(reportDto)
        coVerify(exactly = 1) { (localSettingRepository).setLastReportSaveDateTime(act.ffsReport.dataTime.date) }
        coVerify(exactly = 1) { (localReportRepository).saveReport(act) }
        coVerify(exactly = 0) { (remoteReportRepository).saveReport(any(), any()) }
    }

    // endregion

    /**
     * 全レポートデータ取得
     *
     * 条件：なし
     * 期待結果：
     * ・localReportRepositoryの全レポート取得メソッドが呼ばれること
     * ・上記で取得したレポートがそのまま返却されること
     */
    @Test
    fun getAllReport() = runBlocking {
        setSignInAndToday()
        initViewMode()
        val result = reportUseCaseImp.getAllReport()
        assertEquals(reportList, result)
    }
}
