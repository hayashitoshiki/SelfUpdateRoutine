package com.myapp.data.local.preferences

import android.content.Context
import javax.inject.Inject

/**
 * Preference制御管理
 */
class PreferenceManager @Inject constructor(val context: Context) {

    companion object{
        private const val PREFERENCE_NAME = "selfUpdateRoutine"
    }

    /**
     * キー
     *
     */
    class Key {
        /**
         * Long型指定
         */
        enum class LongKey {
            // アラーム時間
            ALARM_DATE,
            // アラーム時間
            LAST_SAVE_DATE
        }

        /**
         * Int型指定
         */
        enum class IntKey {
            // アラームモード
            ALARM_MODE
        }
    }

    /**
     * Int型格納
     *
     * @param key   キー
     * @param value 格納する値
     */
    fun setInt(
        key: Key.IntKey,
        value: Int
    ) {
        val preferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
        val editor = preferences.edit()
        editor.putInt(key.name, value)
        editor.apply()
    }

    /**
     * Int型取得
     *
     * @param key キー
     * @return キーに紐づくInt型オブジェクト
     */
    fun getInt(key: Key.IntKey): Int {
        val defaultValue = 0
        val preferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
        return preferences.getInt(key.name, defaultValue)
    }

    /**
     * Long型格納
     *
     * @param key   キー
     * @param value 格納する値
     */
    fun setLong(
        key: Key.LongKey,
        value: Long?
    ) {
        val preferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
        val editor = preferences.edit()
        editor.putLong(key.name, value!!)
        editor.apply()
    }

    /**
     * Long型取得
     *
     * @param key キー
     * @return キーに紐づくLong型オブジェクト
     */
    fun getLong(key: Key.LongKey): Long {
        val defaultValue = 0L
        val preferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
        return preferences.getLong(key.name, defaultValue)
    }
}
