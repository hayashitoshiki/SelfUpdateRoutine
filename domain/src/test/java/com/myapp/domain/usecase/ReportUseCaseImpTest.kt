package com.myapp.domain.usecase

import com.myapp.domain.dto.AllReportInputDto
import com.myapp.domain.model.entity.Account
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

    private val nowDateTime = LocalDateTime.now()
    private val reportDto = AllReportInputDto("fact", "find", "learn", "statement", 40, "reason", "improve")
    private val report = ReportTranslator.allReportConvert(reportDto)
    private val reportList = listOf(report)
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

    // ログイン状態
    private fun setSignIn() {
        remoteReportRepository = mockk<RemoteReportRepository>().also {
            coEvery { it.saveReport(any(), any()) } returns Unit
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
        setSignIn()
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
        setSignIn()
        initViewMode()
        val result = reportUseCaseImp.getAllReport()
        assertEquals(reportList, result)
    }
}
