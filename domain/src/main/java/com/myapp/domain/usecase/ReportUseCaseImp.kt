package com.myapp.domain.usecase

import com.myapp.domain.dto.AllReportInputDto
import com.myapp.domain.model.entity.Report
import com.myapp.domain.repository.LocalReportRepository
import com.myapp.domain.repository.LocalSettingRepository
import com.myapp.domain.repository.RemoteAccountRepository
import com.myapp.domain.repository.RemoteReportRepository
import com.myapp.domain.translator.ReportTranslator
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import javax.inject.Inject

// 今日の振り返り機能
class ReportUseCaseImp @Inject constructor(
    private val localReportRepository: LocalReportRepository,
    private val localSettingRepository: LocalSettingRepository,
    private val remoteReportRepository: RemoteReportRepository,
    private val remoteAccountRepository: RemoteAccountRepository
) : ReportUseCase {

    // 振り返り日記を登録する
    override suspend fun saveReport(allReportInputDto: AllReportInputDto) {
        val report = ReportTranslator.allReportConvert(allReportInputDto)

        // 最終登録時間設定
        localSettingRepository.setLastReportSaveDateTime(report.ffsReport.dataTime.date)

        // ログイン済みならリモードデータとの同期処理実施
        if (remoteAccountRepository.autoAuth()) {
            val email = remoteAccountRepository.getAccountDetail()?.email ?: return
            val date = localReportRepository.getLastSaveDate() ?: LocalDateTime.now().with(LocalDate.of(200, 1, 1))
            val reportList = remoteReportRepository.getReportByAfterDate(email, date)
            if (reportList.isNotEmpty()) {
                reportList.forEach{ localReportRepository.saveReport(it) }
                if (reportList.last().ffsReport.dataTime.date >= LocalDateTime.now().with(LocalTime.of(0, 0, 0))) {
                    return
                }
            }
            remoteReportRepository.saveReport(listOf(report), email)
        }
        localReportRepository.saveReport(report)
    }

    // 振り返り日記の詳細を返す
    override suspend fun getAllReport(): List<Report> {
        return localReportRepository.getAllReport()
    }
}
