package com.myapp.domain.translator

import com.myapp.domain.dto.AllReportInputDto
import com.myapp.domain.model.entity.FfsReport
import com.myapp.domain.model.entity.Report
import com.myapp.domain.model.entity.WeatherReport
import com.myapp.domain.model.value.HeartScore
import com.myapp.domain.model.value.ReportDateTime
import java.time.LocalDateTime

/**
 * レポート用 Dto -> DomainModel コンバーター
 **/
internal object ReportTranslator {

    /**
     * レポートDto -> レポートオブジェクト
     *
     * @param allReportInputDto レポートDto
     * @return レポートオブジェクト
     */
    fun allReportConvert(allReportInputDto: AllReportInputDto): Report {
        val datetime = ReportDateTime(LocalDateTime.now())
        val ffsReport = FfsReport(
            datetime, allReportInputDto.factComment, allReportInputDto.findComment, allReportInputDto.learnComment,
            allReportInputDto.statementComment
        )
        val emotionsReport = WeatherReport(
            datetime, HeartScore(allReportInputDto.heartScore), allReportInputDto.reasonComment,
            allReportInputDto.improveComment
        )

        return Report(ffsReport, emotionsReport)
    }
}
