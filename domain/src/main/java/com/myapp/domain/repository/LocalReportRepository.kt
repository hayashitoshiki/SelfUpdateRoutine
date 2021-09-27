package com.myapp.domain.repository

import com.myapp.domain.model.entity.Report

/**
 * ローカルデータへのレポートのCRUD処理
 */
interface LocalReportRepository {

    /**
     * レポート保存
     *
     * @param report 保存するレポート
     */
    suspend fun saveReport(report: Report)

    /**
     * 全レポート取得
     *
     * @return 取得した全レポート
     */
    suspend fun getAllReport(): List<Report>
}
