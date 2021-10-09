package com.myapp.presentation.utils.base

import android.content.Context
import androidx.annotation.StringRes

interface StringResource {
    fun apply(context: Context): String

    companion object {
        fun from(@StringRes resId: Int) = object : StringResource {
            override fun apply(context: Context) = context.getString(resId, null)
        }
    }
}