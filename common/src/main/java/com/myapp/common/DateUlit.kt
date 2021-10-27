package com.myapp.common

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

/**
 * 現在時刻取得(他国化対応のためUTCで取得)
 */
fun getDateTimeNow(): LocalDateTime {
    val now = Date()
    val instant: Instant = now.toInstant()
    val utc = ZoneId.of("UTC")
    return LocalDateTime.ofInstant(instant, utc)
}

/**
 * 「HH:mm」文字列へ変換
 *
 * @return HH:mm
 */
fun LocalDateTime.getStrHHmm() : String {
    val formatter = DateTimeFormatter.ofPattern("HH:mm")
    return this.format(formatter)
}

/**
 * 「M月d日(E) HH:mm」文字列へ変換
 *
 * @return M月d日(E) HH::mm
 */
fun LocalDateTime.getStrHMMddEHHmm() : String {
    val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("M月d日(E) HH:mm", Locale.JAPANESE)
    return this.format(formatter)
}

