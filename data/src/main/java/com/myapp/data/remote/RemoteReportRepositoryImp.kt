package com.myapp.data.remote

import com.myapp.data.Converter
import com.myapp.data.remote.api.FireBaseService
import com.myapp.domain.model.entity.Report
import com.myapp.domain.repository.RemoteReportRepository
import java.time.LocalDateTime
import javax.inject.Inject

class RemoteReportRepositoryImp @Inject constructor(
    private val fireBaseService: FireBaseService
) : RemoteReportRepository {

    // 振り返り日記保存
    override suspend fun saveReport(reportList: List<Report>, email: String) {
        reportList.forEach {
            val emotionsReportEntity = Converter.weatherReportEntityFromWeatherReport(it.weatherReport)
            val ffsReportEntity = Converter.ffsReportEntityFromFfsReport(it.ffsReport)
            fireBaseService.addWeather(emotionsReportEntity, email)
            fireBaseService.addFfs(ffsReportEntity, email)
        }
    }

    // 全振り返り日記取得
    override suspend fun getAllReport(email: String): List<Report> {
        val emotionsReportList = fireBaseService.getWeatherAll(email)
            .map { Converter.weatherReportFromWeatherReportHash(it) }
            .sortedBy { it.dataTime.date }
        val ffsReportList = fireBaseService.getFfsAll(email)
            .map { Converter.ffsReportFromFfsReportHash(it) }
            .sortedBy { it.dataTime.date }
        return ffsReportList.mapNotNull { ffsReport ->
            emotionsReportList
                .firstOrNull { it.dataTime.date.toLocalDate() == ffsReport.dataTime.date.toLocalDate() }
                ?.let { Report(ffsReport, it) }
        }
    }

    // 指定日以降の振り返り日記取得
    override suspend fun getReportByAfterDate(email: String, date: LocalDateTime): List<Report> {
        val ffsReportList = fireBaseService.getFfsByAfterDate(email, date)
            .map { Converter.ffsReportFromFfsReportHash(it) }
            .sortedBy { it.dataTime.date }
        val emotionsReportList = fireBaseService.getWeatherByAfterDate(email, date)
            .map { Converter.weatherReportFromWeatherReportHash(it) }
            .sortedBy { it.dataTime.date }
        return ffsReportList.mapNotNull { ffsReport ->
            emotionsReportList
                .firstOrNull { it.dataTime.date.toLocalDate() == ffsReport.dataTime.date.toLocalDate() }
                ?.let { Report(ffsReport, it) }
        }
    }
}
