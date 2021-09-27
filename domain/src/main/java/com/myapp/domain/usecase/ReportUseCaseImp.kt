package com.myapp.domain.usecase

import com.myapp.domain.dto.AllReportInputDto
import com.myapp.domain.model.entity.Report
import com.myapp.domain.repository.LocalReportRepository
import com.myapp.domain.repository.LocalSettingRepository
import com.myapp.domain.translator.ReportTranslator
import javax.inject.Inject

// 今日の振り返り機能
class ReportUseCaseImp @Inject constructor(
    private val localReportRepository: LocalReportRepository,
    private val localSettingRepository: LocalSettingRepository
) : ReportUseCase {

    // 振り返り日記を登録する
    override suspend fun saveReport(allReportInputDto: AllReportInputDto) {
        val report = ReportTranslator.allReportConvert(allReportInputDto)
        localSettingRepository.setLastReportSaveDateTime(report.ffsReport.dataTime.date)
        localReportRepository.saveReport(report)
    }

    // 振り返り日記の詳細を返す
    override suspend fun getAllReport(): List<Report> {
        return localReportRepository.getAllReport()
    }
}
