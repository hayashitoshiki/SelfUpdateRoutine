package com.myapp.domain.translator

import com.myapp.common.getDateTimeNow
import com.myapp.domain.dto.AllReportInputDto
import com.myapp.domain.model.entity.EmotionsReport
import com.myapp.domain.model.entity.FfsReport
import com.myapp.domain.model.entity.Report
import com.myapp.domain.model.value.HeartScore
import com.myapp.domain.model.value.ReportDateTime

/**
 * Dto -> DomainModel コンバーター
 **/
object ReportTranslator {

    /**
     * 振り返り日記Dto -> 振り返り日記オブジェクト
     */
    fun allReportConvert(allReportInputDto: AllReportInputDto): Report {
        val datetime = ReportDateTime(getDateTimeNow())
        val ffsReport = FfsReport(
            datetime, allReportInputDto.factComment, allReportInputDto.findComment, allReportInputDto.learnComment,
            allReportInputDto.statementComment
        )
        val emotionsReport = EmotionsReport(
            datetime, HeartScore(allReportInputDto.heartScore), allReportInputDto.reasonComment,
            allReportInputDto.improveComment
        )

        return Report(ffsReport, emotionsReport)
    }
}