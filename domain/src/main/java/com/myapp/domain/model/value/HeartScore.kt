package com.myapp.domain.model.value

/**
 * 感情スコア
 */
data class HeartScore(val data: Int) {
    init {
        if (data < 0) {
            throw NumberFormatException("0以上の値を入力してください。value = $data.")
        }
        if (100 < data) {
            throw NumberFormatException("100以下の値を入力してください。value = $data.")
        }
    }
}
