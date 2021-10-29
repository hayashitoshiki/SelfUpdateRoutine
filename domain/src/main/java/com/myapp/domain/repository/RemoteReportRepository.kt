package com.myapp.domain.repository

import com.myapp.domain.model.entity.Report
import java.time.LocalDateTime

/**
 * ローカルデータへのレポートのCRUD処理
 */
interface RemoteReportRepository {

    /**
     * レポート保存
     *
     * @param reportList 保存するレポート
     */
    suspend fun saveReport(reportList: List<Report>, email: String)

    /**
     * 全レポート取得
     *
     * @return 取得した全レポート
     */
    suspend fun getAllReport(email: String): List<Report>

    /**
     *  指定日以降のレポート取得
     *
     * @param email アカウントのメールアドレス
     * @param date 指定日
     * @return
     */
    suspend fun getReportByAfterDate(email: String, date: LocalDateTime): List<Report>
}
