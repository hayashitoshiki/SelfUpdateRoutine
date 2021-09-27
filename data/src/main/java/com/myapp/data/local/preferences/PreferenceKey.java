package com.myapp.data.local.preferences;

/**
 * Preferenceアクセスキー
 */
public class PreferenceKey {

    /**
     * Long型指定
     */
    public enum LongKey {
        // アラーム時間
        ALARM_DATE,
        // アラーム時間
        LAST_SAVE_DATE
    }


    /**
     * Int型指定
     */
    public enum IntKey {
        // アラームモード
        ALARM_MODE
    }

}

