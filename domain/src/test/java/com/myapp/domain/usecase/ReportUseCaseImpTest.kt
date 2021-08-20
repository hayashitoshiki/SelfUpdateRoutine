package com.myapp.domain.usecase

import com.myapp.domain.dto.AllReportInputDto
import com.myapp.domain.repository.LocalReportRepository
import com.myapp.domain.repository.LocalSettingRepository
import com.myapp.domain.translator.ReportTranslator
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.time.LocalDateTime

/**
 * レポート関連機能 ロジック仕様
 *
 */
class ReportUseCaseImpTest {

    // mock
    private lateinit var reportUseCaseImp: ReportUseCaseImp
    private lateinit var localSettingRepository: LocalSettingRepository
    private lateinit var localReportRepository: LocalReportRepository

    // region TestData

    private val nowDateTime = LocalDateTime.now()
    private val reportDto = AllReportInputDto("fact", "find", "learn", "statement", 40, "reason", "improve")
    private val report = ReportTranslator.allReportConvert(reportDto)
    private val reportList = listOf(report)

    // endregion

    @Before
    fun setUp() {
        mockkStatic(LocalDateTime::class)
        every {
            LocalDateTime.now()
        } returns nowDateTime
        localSettingRepository = mockk<LocalSettingRepository>().also {
            coEvery { it.setLastReportSaveDateTime(any()) } returns Unit
        }
        localReportRepository = mockk<LocalReportRepository>().also {
            coEvery { it.saveReport(any()) } returns Unit
            coEvery { it.getAllReport() } returns reportList
        }

        reportUseCaseImp = ReportUseCaseImp(localReportRepository, localSettingRepository)
    }

    @After
    fun tearDown() {
    }

    /**
     * レポート登録
     *
     * 条件：なし
     * 期待結果
     * ・dtoが正しく変換され、登録されること
     */
    @Test
    fun saveReport() = runBlocking {
        reportUseCaseImp.saveReport(reportDto)
        val act = ReportTranslator.allReportConvert(reportDto)
        coVerify(exactly = 1) { (localSettingRepository).setLastReportSaveDateTime(act.ffsReport.dataTime.date) }
        coVerify(exactly = 1) { (localReportRepository).saveReport(act) }
    }

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
        val result = reportUseCaseImp.getAllReport()
        assertEquals(reportList, result)
    }

}