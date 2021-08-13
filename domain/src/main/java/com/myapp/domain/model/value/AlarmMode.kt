package com.myapp.domain.model.value

/**
 * アラートモード
 *
 * @property value DB保存用の引数
 */
enum class AlarmMode(val value: Int) {
    NORMAL(0), HARD(1);

    companion object {
        /**
         * valueからAlertMode取得
         *
         * @param value アラートモートの引数
         * @return　valueに紐づくAlertMode
         */
        @JvmStatic
        fun fromValue(value: Int): AlarmMode {
            return values().first { it.value == value }
        }
    }
}