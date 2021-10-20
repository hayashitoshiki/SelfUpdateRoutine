package com.myapp.domain.model.value

import android.nfc.FormatException
import com.myapp.common.ValidateUtil

/**
 * パスワード
 *
 * @property value パスワード値
 */
@JvmInline
value class Password(val value: String) {
    init {
        if (value.length < 6) throw FormatException("パスワードは６文字以上で入力してください")
        if (!ValidateUtil.isContainsNumber(value)) throw FormatException("数値が含まれてません")
        if (!ValidateUtil.isContainsSmallAlphabet(value)) throw FormatException("小文字が含まれてません")
        if (!ValidateUtil.isContainsBigAlphabet(value)) throw FormatException("大文字が含まれてません")
    }

    companion object{
        /**
         * パスワード判定
         *
         * @param value 文字列
         * @return パスワード判定結果
         */
        fun check(value: String) : Boolean{
            return ValidateUtil.isContainsBigAlphabet(value) && ValidateUtil.isContainsSmallAlphabet(value)
                    && !ValidateUtil.isContainsBigAlphabet(value)
        }
    }
}