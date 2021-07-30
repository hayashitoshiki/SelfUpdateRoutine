package com.myapp.data.local

import com.myapp.data.database.entity.FfsReportEntity
import com.myapp.data.database.entity.WeatherReportEntity
import com.myapp.domain.model.entity.FfsReport
import com.myapp.domain.model.entity.WeatherReport
import com.myapp.domain.model.value.HeartScore
import com.myapp.domain.model.value.ReportDateTime

/**
 * DomainModel ←→ DataBase Entity　コンバーダー
 */
object Converter {

    /**
     * 感情日記オブジェクト -> 感情日記Entity
     */
    fun weatherReportEntityFromWeatherReport(weatherReport: WeatherReport): WeatherReportEntity {
        return WeatherReportEntity(
            null, weatherReport.heartScore.data, weatherReport.reasonComment, weatherReport.improveComment,
            weatherReport.dataTime.date
        )
    }

    /**
     * 感情日記Entity -> 感情日記オブジェクト
     */
    fun weatherReportFromWeatherReportEntity(weatherReportEntity: WeatherReportEntity): WeatherReport {
        return WeatherReport(
            ReportDateTime(weatherReportEntity.createTime), HeartScore(weatherReportEntity.heartScore),
            weatherReportEntity.reasonComment, weatherReportEntity.improveComment
        )
    }

    /**
     * FFS式日記オブジェクト -> FFS式日記Entity
     */
    fun ffsReportEntityFromFfsReport(ffsReport: FfsReport): FfsReportEntity {
        return FfsReportEntity(
            null, ffsReport.factComment, ffsReport.findComment, ffsReport.learnComment, ffsReport.statementComment,
            ffsReport.dataTime.date
        )
    }

    /**
     * FFS式日記Entity -> FFS式日記オブジェクト
     */
    fun ffsReportFromFfsReportEntity(ffsReportEntity: FfsReportEntity): FfsReport {
        return FfsReport(
            ReportDateTime(ffsReportEntity.createTime), ffsReportEntity.factComment, ffsReportEntity.findComment,
            ffsReportEntity.learnComment, ffsReportEntity.statementComment
        )
    }
}