package com.myapp.domain.repository

import com.myapp.domain.model.value.AlarmMode
import java.time.LocalDateTime

/**
 * ローカルデータへの設定機能のCRUD処理
 */
interface LocalSettingRepository {

    /**
     * 設定しているアラーム日付取得
     *
     * @return 設定しているアラーム時間
     */
    fun getAlarmDate(): LocalDateTime

    /**
     * アラーム日付更新
     *
     * @param date アラーム日付
     */
    fun saveAlarmDate(date: LocalDateTime)

    /**
     * 設定しているアラームモード取得
     *
     * 何も設定していない場合はNORMALを返す
     * @return アラームモード
     */
    fun getAlarmMode(): AlarmMode

    /**
     * アラームモード更新
     *
     * @param mode アラームモード
     */
    fun saveAlarmMode(mode: AlarmMode)

    /**
     * 最終レポートを記録した時間取得
     *
     * 最後にレポートを記録した時間を取得
     * 何も設定していない場合は2000/1/1/0:00:00を返す
     * @return 最後にレポートを記録した日時を返す
     */
    fun getLastReportSaveDateTime(): LocalDateTime

    /**
     * 最終レポート時間更新
     *
     * @param date レポートを記録した日時
     */
    fun setLastReportSaveDateTime(date: LocalDateTime)
}
