package com.myapp.data.local

import com.myapp.common.getDateTimeNow
import com.myapp.data.local.database.entity.mission_statement.ConstitutionEntity
import com.myapp.data.local.database.entity.mission_statement.FuneralEntity
import com.myapp.data.local.database.entity.mission_statement.PurposeLifeEntity
import com.myapp.data.local.database.entity.report.FfsReportEntity
import com.myapp.data.local.database.entity.report.WeatherReportEntity
import com.myapp.domain.model.entity.FfsReport
import com.myapp.domain.model.entity.MissionStatement
import com.myapp.domain.model.entity.WeatherReport
import com.myapp.domain.model.value.HeartScore
import com.myapp.domain.model.value.ReportDateTime

/**
 * DomainModel ←→ DataBase Entity　コンバーダー
 */
object Converter {

    // region レポート

    /**
     * 感情日記オブジェクト -> 感情日記Entity
     *
     * @param weatherReport 感情日記オブジェクト
     * @return 感情日記Entity
     */
    fun weatherReportEntityFromWeatherReport(weatherReport: WeatherReport): WeatherReportEntity {
        return WeatherReportEntity(
            null, weatherReport.heartScore.data, weatherReport.reasonComment, weatherReport.improveComment,
            weatherReport.dataTime.date
        )
    }

    /**
     * 感情日記Entity -> 感情日記オブジェクト
     *
     * @param weatherReportEntity 感情日記Entity
     * @return 感情日記オブジェクト
     */
    fun weatherReportFromWeatherReportEntity(weatherReportEntity: WeatherReportEntity): WeatherReport {
        return WeatherReport(
            ReportDateTime(weatherReportEntity.createTime), HeartScore(weatherReportEntity.heartScore),
            weatherReportEntity.reasonComment, weatherReportEntity.improveComment
        )
    }

    /**
     * FFS式日記オブジェクト -> FFS式日記Entity
     *
     * @param ffsReport FFS式日記オブジェクト
     * @return FFS式日記Entity
     */
    fun ffsReportEntityFromFfsReport(ffsReport: FfsReport): FfsReportEntity {
        return FfsReportEntity(
            null, ffsReport.factComment, ffsReport.findComment, ffsReport.learnComment, ffsReport.statementComment,
            ffsReport.dataTime.date
        )
    }

    /**
     * FFS式日記Entity -> FFS式日記オブジェクト
     *
     * @param ffsReportEntity FFS式日記Entity
     * @return FFS式日記オブジェクト
     */
    fun ffsReportFromFfsReportEntity(ffsReportEntity: FfsReportEntity): FfsReport {
        return FfsReport(
            ReportDateTime(ffsReportEntity.createTime), ffsReportEntity.factComment, ffsReportEntity.findComment,
            ffsReportEntity.learnComment, ffsReportEntity.statementComment
        )
    }

    // endregion

    // region ミッションステートメント

    /**
     * ミッションステートメントオブジェクト -> 理想の葬儀Entityリスト
     *
     * @param missionStatement ミッションステートメント
     * @return 理想の葬儀Entityリスト
     */
    fun funeralEntityListFromMissionStatement(missionStatement: MissionStatement): List<FuneralEntity> {
        return missionStatement.funeralList.filter { it.isNotEmpty() }
            .map { FuneralEntity(0, it, getDateTimeNow()) }
    }

    /**
     * ミッションステートメントオブジェクト -> 憲法Entityリスト
     *
     * @param missionStatement ミッションステートメント
     * @return 憲法Entityリスト
     */
    fun constitutionEntityListFromMissionStatement(missionStatement: MissionStatement): List<ConstitutionEntity> {
        return missionStatement.constitutionList.filter { it.isNotEmpty() }
            .map { ConstitutionEntity(0, it, getDateTimeNow()) }
    }

    /**
     * ミッションステートメントオブジェクト -> 人生の目的Entity
     *
     * @param missionStatement ミッションステートメント
     * @return 人生の目的Entity
     */
    fun purposeLifeEntityFromMissionStatement(missionStatement: MissionStatement): PurposeLifeEntity {
        return PurposeLifeEntity(1, missionStatement.purposeLife, getDateTimeNow(), getDateTimeNow())
    }

    /**
     * 各種Entityからミッションステートメントオブジェクト生成
     *
     * もし理想の葬式リストが空でかつ、
     * 人生の目的がnullまたは空でかつ、
     * 憲法リストが空なら、
     * 引数の全ての値が空ならnullを返す
     *
     * @param funeralEntityList 理想の葬儀リスト
     * @param purposeLifeEntity 人生の目的
     * @param constitutionEntityList 憲法リスト
     * @return ミッションステートメント
     */
    fun missionStatementFromEntity(
        funeralEntityList: List<FuneralEntity>,
        purposeLifeEntity: PurposeLifeEntity?,
        constitutionEntityList: List<ConstitutionEntity>
    ): MissionStatement? {
        val funeralList = funeralEntityList.map { it.text }
        val purposeLife = purposeLifeEntity?.text ?: ""
        val constitutionList = constitutionEntityList.map { it.text }
        return if (funeralList.isEmpty() && purposeLife == "" && constitutionList.isEmpty()) {
            null
        } else {
            MissionStatement(funeralList, purposeLife, constitutionList)
        }
    }

    // endregion
}