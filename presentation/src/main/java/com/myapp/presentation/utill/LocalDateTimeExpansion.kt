package com.myapp.presentation.utill

import com.myapp.common.getDateTimeNow
import java.time.LocalDateTime

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