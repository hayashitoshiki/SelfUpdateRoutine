package com.myapp.domain.repository

import com.myapp.domain.model.entity.Report

/**
 * ローカルデータへの振り返り日記のCRUD処理
 */
interface LocalReportRepository {

    /**
     * 振り返り日記保存
     */
    suspend fun saveReport(report: Report)

    /**
     * TODO : 仮　１つ目の振り返り日記取得
     */
    suspend fun getFirst(): Report
}