package com.myapp.presentation.utill

/**
 * UIのステータス管理
 *
 * @param T
 */
sealed class Status<out T> {
    object Loading : Status<Nothing>()
    data class Success<T>(val data: T) : Status<T>()
    data class Failure(val throwable: Throwable) : Status<Nothing>()
    object Non : Status<Nothing>()
}
