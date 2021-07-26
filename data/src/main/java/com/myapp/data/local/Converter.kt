package com.myapp.data.local

import com.myapp.data.database.entity.EmotionsReportEntity
import com.myapp.data.database.entity.FfsReportEntity
import com.myapp.domain.model.entity.EmotionsReport
import com.myapp.domain.model.entity.FfsReport
import com.myapp.domain.model.value.HeartScore
import com.myapp.domain.model.value.ReportDateTime

/**
 * DomainModel ←→ DataBase Entity　コンバーダー
 */
object Converter {

    /**
     * 感情日記オブジェクト -> 感情日記Entity
     */
    fun emotionsReportEntityFromEmotionsReport(emotionsReport: EmotionsReport): EmotionsReportEntity {
        return EmotionsReportEntity(
            null, emotionsReport.heartScore.data, emotionsReport.reasonComment, emotionsReport.improveComment,
            emotionsReport.dataTime.date
        )
    }

    /**
     * 感情日記Entity -> 感情日記オブジェクト
     */
    fun emotionsReportFromEmotionsReportEntity(emotionsReportEntity: EmotionsReportEntity): EmotionsReport {
        return EmotionsReport(
            ReportDateTime(emotionsReportEntity.createTime), HeartScore(emotionsReportEntity.heartScore),
            emotionsReportEntity.reasonComment, emotionsReportEntity.improveComment
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