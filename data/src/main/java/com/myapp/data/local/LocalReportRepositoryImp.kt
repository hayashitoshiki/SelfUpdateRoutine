package com.myapp.data.local

import com.myapp.data.Converter
import com.myapp.data.local.database.dao.report.FfsReportDao
import com.myapp.data.local.database.dao.report.WeatherReportDao
import com.myapp.domain.model.entity.Report
import com.myapp.domain.repository.LocalReportRepository
import java.time.LocalDateTime
import javax.inject.Inject

class LocalReportRepositoryImp @Inject constructor(
    private val weatherReportDao: WeatherReportDao,
    private val ffsReportDao: FfsReportDao
) : LocalReportRepository {

    // 振り返り日記保存
    override suspend fun saveReport(report: Report) {
        val emotionsReportEntity = Converter.weatherReportEntityFromWeatherReport(report.weatherReport)
        val ffsReportEntity = Converter.ffsReportEntityFromFfsReport(report.ffsReport)
        weatherReportDao.insert(emotionsReportEntity)
        ffsReportDao.insert(ffsReportEntity)
    }

    // 全振り返り日記取得
    override suspend fun getAllReport(): List<Report> {
        val emotionsReportList = weatherReportDao.getAll()
            .map { Converter.weatherReportFromWeatherReportEntity(it) }
        val ffsReportList = ffsReportDao.getAll()
            .map { Converter.ffsReportFromFfsReportEntity(it) }
        return ffsReportList.mapNotNull { ffsReport ->
            emotionsReportList
                .firstOrNull { it.dataTime.date.toLocalDate() == ffsReport.dataTime.date.toLocalDate() }
                ?.let { Report(ffsReport, it) }
        }
    }

    // 登録してあるレポートの最後に登録した日付を返す
    override suspend fun getLastSaveDate(): LocalDateTime? {
        return getAllReport().let {
            if (it.isEmpty()) null
            else it.last().ffsReport.dataTime.date
        }
    }

    override suspend fun deleteAll() {
        weatherReportDao.deleteAll()
        ffsReportDao.deleteAll()
    }

}
