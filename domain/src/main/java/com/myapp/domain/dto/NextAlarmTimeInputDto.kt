package com.myapp.domain.dto

import com.myapp.domain.model.value.AlarmMode

/**
 * アラーム設定オブジェクト生成用Dto
 *
 * @property hour 時間
 * @property minute 分
 * @property second 秒
 * @property mode アラームモード
 */
class NextAlarmTimeInputDto(
    val hour: Int,
    val minute: Int,
    val second: Int,
    val mode: AlarmMode
)