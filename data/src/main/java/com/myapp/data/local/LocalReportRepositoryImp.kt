package com.myapp.data.local

import android.util.Log
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
    override suspend fun getAllReport(): List<Report> {
        Log.d("LocalReportRepositoryImp", "getAllReport")
        val emotionsReportList = emotionReportDao.getAll()
            .map {
                Converter.emotionsReportFromEmotionsReportEntity(it)
            }
        val ffsReportList = ffsReportDao.getAll()
            .map {
                Converter.ffsReportFromFfsReportEntity(it)
            }
        val reportList = mutableListOf<Report>()
        for (index in emotionsReportList.indices) {
            if (ffsReportList.size > index) {
                reportList.add(Report(ffsReportList[index], emotionsReportList[index]))
            }
        }
        Log.d("LocalReportRepositoryImp", "return size = " + reportList.size)
        return reportList

    }
}