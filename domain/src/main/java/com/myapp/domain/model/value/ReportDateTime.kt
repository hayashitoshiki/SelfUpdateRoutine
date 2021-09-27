package com.myapp.domain.model.value

import android.annotation.SuppressLint
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.*

/**
 * 記録をつけた日時
 */
data class ReportDateTime(val date: LocalDateTime) {
    /**
     * セクション用の日時文字列を返す
     * @return yyyy/MM/dd
     */
    fun toSectionDate(): String {
        val systemDateTime = LocalDateTime.ofInstant(date.toInstant(ZoneOffset.UTC), ZoneId.systemDefault())
        val df = DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mm:ss")
        return df.format(systemDateTime)
            .substring(0, 10)
    }

    /**
     * カード表示用の日時文字列を返す
     * @return MM月dd日
     */
    fun toMdDate(): String {
        val systemDateTime = LocalDateTime.ofInstant(date.toInstant(ZoneOffset.UTC), ZoneId.systemDefault())
        val df = DateTimeFormatter.ofPattern("M月d日")
        return df.format(systemDateTime)
    }

    // 日付取得
    @SuppressLint("SimpleDateFormat")
    private fun getDataNow(): String {
        val df: DateFormat = SimpleDateFormat("yyyy/MM/dd")
        val date = Date(System.currentTimeMillis())
        return df.format(date)
    }
}
