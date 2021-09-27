package com.myapp.domain.usecase

import com.myapp.domain.dto.NextAlarmTimeInputDto
import com.myapp.domain.model.value.AlarmMode
import java.time.LocalDateTime

/**
 * 設定関連機能
 */
interface SettingUseCase {

    /**
     * 現在設定しているアラーム時間取得
     *
     * @return 現在設定しているアラーム時間
     */
    fun getAlarmDate(): LocalDateTime

    /**
     * アラーム設定更新
     *
     * Dtoを元に次回のアラーム時間とアラームモードを設定。
     * 設定後、設定したアラーム時間を返す
     *
     * @param dto アラーム設定オブジェクト生成用Dto
     * @return 設定したアラーム時間
     */
    fun updateAlarmDate(dto: NextAlarmTimeInputDto): LocalDateTime

    /**
     * 現在のアラームモード取得
     *
     * @return アラームモード
     */
    fun getAlarmMode(): AlarmMode

    /**
     * 次回のアラーム時刻取得
     *
     * @param dto アラーム設定オブジェクト生成用Dto
     * @return 次回のアラーム時刻
     */
    fun getNextAlarmDate(dto: NextAlarmTimeInputDto): LocalDateTime
}
