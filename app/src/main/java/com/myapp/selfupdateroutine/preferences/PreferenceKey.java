package com.myapp.selfupdateroutine.preferences;

/**
 * Preferenceアクセスキー
 */
abstract class PreferenceKey {

    /**
     * Long型指定
     */
    protected enum LongKey {
        // アラーム時間
        ALARM_DATE,
        // アラーム時間
        LAST_SAVE_DATE
    }


    /**
     * Int型指定
     */
    protected enum IntKey {
        // アラームモード
        ALARM_MODE
    }

}

