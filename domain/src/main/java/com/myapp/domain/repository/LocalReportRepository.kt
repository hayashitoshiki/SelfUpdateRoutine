package com.myapp.domain.repository

import com.myapp.domain.model.entity.Report
import java.time.LocalDateTime

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

    /**
     * 最後に登録した日付を返す
     *
     * @return 最後に登録した日付(未登録であればnullを返す)
     */
    suspend fun getLastSaveDate(): LocalDateTime?

    /**
     * 全レポート削除
     *
     */
    suspend fun deleteAll()
}
