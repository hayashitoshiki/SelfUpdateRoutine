package com.myapp.common

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
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
