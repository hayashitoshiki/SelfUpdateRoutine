package com.myapp.presentation.utils

/**
 * LiveData拡張定義用ファイル
 */

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

/**
 * 初回のみ流す
 *
 * @param T　LiveData
 * @param owner ライフサイクル
 * @param observe 処理
 */
fun <T> LiveData<T>.observeAtOnce(
    owner: LifecycleOwner,
    observe: (value: T) -> Unit
) = apply {
    observe(
        owner,
        object : Observer<T> {
            override fun onChanged(value: T?) {
                removeObserver(this)
                observeNotNull(owner, observe)
            }
        }
    )
}

/**
 * Null以外の時流す
 *
 * @param T　LiveData
 * @param owner ライフサイクル
 * @param observe 処理
 */
fun <T> LiveData<T>.observeNotNull(
    owner: LifecycleOwner,
    observe: (value: T) -> Unit
) = apply {
    observe(
        owner,
        Observer { value ->
            value ?: return@Observer
            observe(value)
        }
    )
}
