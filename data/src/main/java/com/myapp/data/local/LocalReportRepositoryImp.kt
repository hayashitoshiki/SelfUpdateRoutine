package com.myapp.data.local

import com.myapp.data.database.dao.EmotionReportDao
import com.myapp.data.database.dao.FfsReportDao
import com.myapp.domain.model.entity.Report
import com.myapp.domain.repository.LocalReportRepository

class LocalReportRepositoryImp(
    private val emotionReportDao: EmotionReportDao,
    private val ffsReportDao: FfsReportDao
) : LocalReportRepository {

    // 振り返り日記保存
    override suspend fun saveReport(report: Report) {
        val emotionsReportEntity = Converter.emotionsReportEntityFromEmotionsReport(report.emotionsReport)
        val ffsReportEntity = Converter.ffsReportEntityFromFfsReport(report.ffsReport)
        emotionReportDao.insert(emotionsReportEntity)
        ffsReportDao.insert(ffsReportEntity)

    }

    // 全振り返り日記取得
    override suspend fun getFirst(): Report {
        val emotionsReportEntity = emotionReportDao.getReportById(1)
        val ffsReportEntity = ffsReportDao.getReportById(1)
        val emotionsReport = Converter.emotionsReportFromEmotionsReportEntity(emotionsReportEntity)
        val ffsReport = Converter.ffsReportFromFfsReportEntity(ffsReportEntity)
        return Report(ffsReport, emotionsReport)
    }
}