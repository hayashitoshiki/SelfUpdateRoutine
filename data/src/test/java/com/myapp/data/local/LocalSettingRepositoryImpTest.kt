package com.myapp.data.local

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.myapp.common.getDateTimeNow
import com.myapp.data.local.preferences.PreferenceManager
import com.myapp.domain.model.value.AlarmMode
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.time.LocalDateTime
import java.time.LocalTime

/**
 * SharedPreferences　CRUDテスト
 *
 */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [28])
class LocalSettingRepositoryImpTest {

    private lateinit var localSettingRepositoryImp: LocalSettingRepositoryImp
    private lateinit var context: Context
    private lateinit var preferenceManager: PreferenceManager

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        preferenceManager = PreferenceManager(context)
        localSettingRepositoryImp = LocalSettingRepositoryImp(preferenceManager)
    }

    @After
    fun tearDown() {

    }

    // region AlarmDate(アラーム設定に日時)

    /**
     * アラーム時間設定
     *
     * 条件：設定あり
     * 期待結果：設定した値が返ること
     */
    @Test
    fun getAlarmDateByNotNon() {
        val data = LocalDateTime.now()
        localSettingRepositoryImp.saveAlarmDate(data)
        val result = localSettingRepositoryImp.getAlarmDate()
        assertEquals(data.year, result.year)
        assertEquals(data.month, result.month)
        assertEquals(data.dayOfMonth, result.dayOfMonth)
        assertEquals(data.hour, result.hour)
        assertEquals(data.minute, result.minute)
        assertEquals(data.second, result.second)
    }

    /**
     * アラーム時間取得
     *
     * 条件：設定なし
     * 期待結果：22:00（デフォルト値）が返ること
     */
    @Test
    fun getAlarmDateByNon() {
        val data = getDateTimeNow().with(LocalTime.of(22, 0))
        val result = localSettingRepositoryImp.getAlarmDate()
        assertEquals(data, result)
    }

    // endregion

    // region AlarmMode（アラームモード)

    /**
     * アラームモード取得
     *
     * 条件：設定あり
     * 期待結果：設定した値が返ること
     */
    @Test
    fun getAlarmModeByNotNon() {
        val data = AlarmMode.HARD
        localSettingRepositoryImp.saveAlarmMode(data)
        val result = localSettingRepositoryImp.getAlarmMode()
        assertEquals(data, result)
    }

    /**
     * アラームモード取得
     *
     * 条件：設定なし
     * 期待結果：0（デフォルト値）が返ること
     */
    @Test
    fun getAlarmModeByNon() {
        val data = AlarmMode.NORMAL
        val result = localSettingRepositoryImp.getAlarmMode()
        assertEquals(data, result)
    }

    // endregion

    // region LastReportSaveDateTime(最後に登録した日付)

    /**
     * レポート最終登録日取得
     *
     * 条件：設定あり
     * 期待結果：設定した値が返ること
     */
    @Test
    fun getLastReportSaveDateTimeByNotNon() {
        val data = getDateTimeNow().with(LocalTime.of(19, 0))
        localSettingRepositoryImp.setLastReportSaveDateTime(data)
        val result = localSettingRepositoryImp.getLastReportSaveDateTime()
        assertEquals(data, result)
    }

    /**
     * レポート最終登録日取得
     *
     * 条件：設定なし
     * 期待結果：2000/1/1 0:00:00（デフォルト値）が返ること
     */
    @Test
    fun getLastReportSaveDateTimeByNon() {
        val data = LocalDateTime.of(2000, 1, 1, 0, 0, 0)
        val result = localSettingRepositoryImp.getLastReportSaveDateTime()
        assertEquals(data, result)
    }

    // endregion
}
