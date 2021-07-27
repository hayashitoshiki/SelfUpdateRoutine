package com.myapp.domain.usecase

import com.myapp.domain.dto.AllReportInputDto
import com.myapp.domain.model.entity.Report

/**
 * 今日の振り返り機能
 */
interface ReportUseCase {

    /**
     * 日記を登録
     */
    suspend fun saveReport(allReportInputDto: AllReportInputDto)


    /**
     * 全ての振り返り日記取得
     */
    suspend fun getAllReport(): List<Report>
}