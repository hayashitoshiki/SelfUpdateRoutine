package com.myapp.selfupdateroutine.preferences;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Preference制御管理
 */
public class PreferenceManager {

    @SuppressLint("StaticFieldLeak")
    private static Context context;

    public PreferenceManager(Context context) {
        PreferenceManager.context = context;
    }

    /**
     * Long型格納
     *
     * @param key   キー
     * @param value 格納する値
     */
    protected void setLong(PreferenceKey.LongKey key, Long value) {
        SharedPreferences preferences = context.getSharedPreferences("selfUpdateRoutine", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong(key.name(), value);
        editor.apply();
    }

    /**
     * Long型取得
     *
     * @param key キー
     * @return キーに紐づくString型オブジェクト
     */
    protected long getLong(PreferenceKey.LongKey key) {
        long defaultValue = 0L;
        SharedPreferences preferences = context.getSharedPreferences("selfUpdateRoutine", Context.MODE_PRIVATE);
        return preferences.getLong(key.name(), defaultValue);
    }
}
