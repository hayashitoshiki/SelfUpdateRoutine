package com.myapp.presentation.utils.expansion

/**
 * LocalDateTime拡張定義用ファイル
 */

import java.time.LocalDateTime

/**
 * 今日であるか判定
 *
 * @return 判定結果
 */
fun LocalDateTime.isToday(): Boolean {
    val today = LocalDateTime.now()
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
