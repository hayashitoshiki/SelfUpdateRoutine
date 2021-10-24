package com.myapp.common

/**
 * バリデーション系Utilクラス
 */
object ValidateUtil {

    private val number = Regex("[0-9]+")
    private val smallAlphabet = Regex("[a-z]+")
    private val bigAlphabet = Regex("[A-Z]+")

    /**
     * 数値が含まれるか
     *
     * @param value 文字列
     * @return 数値が含まれているかの判定結果
     */
    fun isContainsNumber(value: String): Boolean {
        return number.containsMatchIn(value)
    }

    /**
     * 小文字が含まれているか
     *
     * @param value 文字列
     * @return 小文字が含まれているかの判定結果
     */
    fun isContainsSmallAlphabet(value: String): Boolean {
        return smallAlphabet.containsMatchIn(value)
    }

    /**
     * 大文字が含まれているか
     *
     * @param value 文字列
     * @return 大文字が含まれているかの半手結果
     */
    fun isContainsBigAlphabet(value: String): Boolean {
        return bigAlphabet.containsMatchIn(value)
    }

    /**
     * メールアドレスか
     *
     * @param value 文字列
     * @return メールアドレスかの判定結果
     */
    fun isEmail(value: String): Boolean {
        val validate = Regex("[a-zA-Z0-9._-]+@[a-z]{2,}+\\.+[a-z]{2,}")
        return validate.containsMatchIn(value)
    }
}