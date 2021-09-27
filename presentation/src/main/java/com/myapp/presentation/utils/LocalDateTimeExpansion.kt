package com.myapp.presentation.utils

/**
 * LocalDateTime拡張定義用ファイル
 */

import com.myapp.common.getDateTimeNow
import java.time.LocalDateTime

/**
 * 今日であるか判定
 *
 * @return 判定結果
 */
fun LocalDateTime.isToday(): Boolean {
    val today = getDateTimeNow()
    if (today.year != this.year) {
        return false
    }
    if (today.month != this.month) {
        return false
    }
    if (today.dayOfMonth != this.dayOfMonth) {
        return false
    }
    return true
}
