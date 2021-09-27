package com.myapp.domain.usecase

import com.myapp.domain.model.value.AlarmMode
import com.myapp.domain.repository.LocalSettingRepository
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import java.time.LocalDateTime
import java.time.LocalTime
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

/**
 * アラーム関連機能 ロジック仕様
 *
 */
class AlarmNotificationUseCaseImpTest {

    // mock
    private lateinit var alarmNotificationUseCaseImp: AlarmNotificationUseCaseImp
    private lateinit var localSettingRepository: LocalSettingRepository

    // region TestData

    private val nowDateTime = LocalDateTime.now()
        .with(LocalTime.of(21, 0, 0, 0))
    private val nowDateTimeByBefore = nowDateTime.with(LocalTime.of(15, 0, 0, 0))
    private val tomorrowFirstDateTime = nowDateTime.plusDays(1)
        .with(LocalTime.of(0, 0, 0, 0))
    private val yesterdayDateTime = nowDateTime.minusDays(1)
    private val todayAlarmDate = nowDateTime.with(LocalTime.of(20, 0, 0, 0))
    private val tomorrowAlarmDate = todayAlarmDate.plusDays(1)

    // endregion

    @Before
    fun setUp() {
        localSettingRepository = mockk<LocalSettingRepository>().also {
            coEvery { it.getAlarmDate() } returns todayAlarmDate
        }

        alarmNotificationUseCaseImp = AlarmNotificationUseCaseImp(localSettingRepository)
    }

    // region Mockデータ設定

    // 今日の日時で最終レポート時間を返却
    private fun setLastReportTimeByToday() {
        coEvery { localSettingRepository.getLastReportSaveDateTime() } returns nowDateTime
    }

    // 昨日の日時で最終レポート時間を返却
    private fun setLastReportTimeByYesterday() {
        coEvery { localSettingRepository.getLastReportSaveDateTime() } returns yesterdayDateTime
    }

    // アラートモード＝高を返却
    private fun setHardMode() {
        coEvery { localSettingRepository.getAlarmMode() } returns AlarmMode.HARD
    }

    // アラートモード＝中を返却
    private fun setNormalMode() {
        coEvery { localSettingRepository.getAlarmMode() } returns AlarmMode.NORMAL
    }

    // アラーム時間内の時間で今日の日時を返却
    private fun setNowTimeByAlertTime() {
        mockkStatic(LocalDateTime::class)
        every {
            LocalDateTime.now()
        } returns nowDateTime
    }

    // アラーム時間外の時間で今日の日時を返却
    private fun setNowTimeByNotAlertTime() {
        mockkStatic(LocalDateTime::class)
        every {
            LocalDateTime.now()
        } returns tomorrowFirstDateTime
    }

    // アラーム時間外の時間で今日の日時を返却
    private fun setNowTimeByBeforeAlertTime() {
        mockkStatic(LocalDateTime::class)
        every {
            LocalDateTime.now()
        } returns nowDateTimeByBefore
    }

    // endregion

    @After
    fun tearDown() {
    }

    /**
     * まだ今日の記録をつけていない場合
     *
     * 条件：
     * ・まだ今日の記録をつけていない
     * ・アラート時間〜23:59:59の間
     * ・アラートモードが中
     *
     * 期待結果：翌日の日程でアラーム設定時間を返却
     */
    @Test
    fun getNextAlarmByNotReportAndNormalAndAlertTime() {
        setNowTimeByAlertTime()
        setNormalMode()
        setLastReportTimeByYesterday()
        val result = alarmNotificationUseCaseImp.getNextAlarmDateTime()
        assertEquals(tomorrowAlarmDate, result)
    }

    /**
     * まだ今日の記録をつけていない場合
     *
     * 条件：
     * ・まだ今日の記録をつけていない
     * ・アラーム時間より前
     * ・アラートモードが中
     *
     * 期待結果：当日の日程でアラーム設定時間を返却
     */
    @Test
    fun getNextAlarmByNotReportAndNormalAndBeforeTime() {
        setNowTimeByBeforeAlertTime()
        setNormalMode()
        setLastReportTimeByYesterday()
        val result = alarmNotificationUseCaseImp.getNextAlarmDateTime()
        assertEquals(todayAlarmDate, result)
    }

    /**
     * まだ今日の記録をつけていない場合
     *
     * 条件：
     * ・まだ今日の記録をつけていない
     * ・アラート時間〜23:59:59の間
     * ・アラートモードが高
     *
     * 期待結果：アラート時間の５秒後の時間を返却
     */
    @Test
    fun getNextAlarmByNotReportAndHardAndAlertTime() {
        setNowTimeByAlertTime()
        setHardMode()
        setLastReportTimeByYesterday()
        val result = alarmNotificationUseCaseImp.getNextAlarmDateTime()
        assertEquals(nowDateTime.plusSeconds(5), result)
    }

    /**
     * まだ今日の記録をつけていない場合
     *
     * 条件：
     * ・まだ今日の記録をつけていない
     * ・23:59:59の後（翌日）
     * ・アラートモードが高
     *
     * 期待結果：翌日の日程でアラーム設定時間を返却
     */
    @Test
    fun getNextAlarmByNotReportAndHardAndNotAlertTime() {
        setNowTimeByNotAlertTime()
        setHardMode()
        setLastReportTimeByYesterday()
        val result = alarmNotificationUseCaseImp.getNextAlarmDateTime()
        assertEquals(tomorrowAlarmDate, result)
    }

    /**
     * 今日の記録をつけてある場合
     *
     * 条件：
     * ・今日の記録をつけた
     * ・アラート時間〜23:59:59の間
     * ・アラートモードが中
     *
     * 期待結果：翌日の日程でアラーム設定時間を返却
     */
    @Test
    fun getNextAlarmByReportAndNormalAndAlertTime() {
        setNowTimeByAlertTime()
        setNormalMode()
        setLastReportTimeByToday()
        val result = alarmNotificationUseCaseImp.getNextAlarmDateTime()
        assertEquals(tomorrowAlarmDate, result)
    }

    /**
     * 今日の記録をつけた
     *
     * 条件：
     * ・今日の記録をつけた
     * ・アラート時間〜23:59:59の間
     * ・アラートモードが高
     *
     * 期待結果：翌日の日程でアラーム設定時間を返却
     */
    @Test
    fun getNextAlarmByReportAndHardAndAlertTime() {
        setNowTimeByAlertTime()
        setHardMode()
        setLastReportTimeByToday()
        val result = alarmNotificationUseCaseImp.getNextAlarmDateTime()
        assertEquals(tomorrowAlarmDate, result)
    }

    // region 通知バーの表示の有無チェック

    /**
     * すでに本日分のレポート登録済み
     *
     * 条件：
     * ・最終レポート記録時間が今日
     *
     * 期待結果：false(表示しない)を返す
     */
    @Test
    fun checkAlarmNotificationEnableByReport() {
        setLastReportTimeByToday()
        val result = alarmNotificationUseCaseImp.checkAlarmNotificationEnable()
        assertEquals(false, result)
    }

    /**
     * まだ本日分のレポート登録していない
     *
     * 条件：
     * ・最終レポート記録時間が昨日
     *
     * 期待結果：true(表示する)を返す
     */
    @Test
    fun checkAlarmNotificationEnableByNotReport() {
        setLastReportTimeByYesterday()
        val result = alarmNotificationUseCaseImp.checkAlarmNotificationEnable()
        assertEquals(true, result)
    }
}
