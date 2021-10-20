package com.myapp.domain.model.value

import android.nfc.FormatException
import com.myapp.common.ValidateUtil

/**
 * メールアドレス
 *
 * @property value メールアドレス値
 */
@JvmInline
value class Email(val value: String) {
    init {
        if (!ValidateUtil.isEmail(value)) throw FormatException("メールアドレスを入力してください")
    }

    companion object{
        /**
         * メールアドレス判定
         *
         * @param value 文字列
         * @return メールアドレス判定結果
         */
        fun check(value: String) : Boolean{
            return ValidateUtil.isEmail(value)
        }
    }
}