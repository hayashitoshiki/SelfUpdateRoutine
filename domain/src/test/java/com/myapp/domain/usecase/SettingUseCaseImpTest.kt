package com.myapp.domain.usecase

import com.myapp.domain.dto.NextAlarmTimeInputDto
import com.myapp.domain.model.value.AlarmMode
import com.myapp.domain.repository.LocalSettingRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.time.LocalDateTime
import java.time.LocalTime

class SettingUseCaseImpTest {

    // mock
    private lateinit var settingUseCaseImp: SettingUseCaseImp
    private lateinit var localSettingRepository: LocalSettingRepository

    // region TestData

    private val nowDateTime = LocalDateTime.now()
    private val yesterdayDateTime = nowDateTime.minusDays(1)
    private val todayAlarmDate = nowDateTime.with(LocalTime.of(20, 0, 0, 0))
    private val alarmModeNormal = AlarmMode.NORMAL
    private val dto = NextAlarmTimeInputDto(20, 0, 0, alarmModeNormal)

    // endregion

    @Before
    fun setUp() {
        localSettingRepository = mockk<LocalSettingRepository>().also {
            coEvery { it.getAlarmDate() } returns todayAlarmDate
            coEvery { it.getAlarmMode() } returns alarmModeNormal
            coEvery { it.saveAlarmDate(any()) } returns Unit
            coEvery { it.saveAlarmMode(any()) } returns Unit
        }
        settingUseCaseImp = SettingUseCaseImp(localSettingRepository)
    }

    // region Mockデータ設定

    // 最終レポート記録時間が今日
    private fun setLastReportByToday() {
        coEvery { localSettingRepository.getLastReportSaveDateTime() } returns todayAlarmDate

    }

    // 最終レポート記録時間が昨日
    private fun setLastReportByYesterday() {
        coEvery { localSettingRepository.getLastReportSaveDateTime() } returns yesterdayDateTime

    }

    // endregion

    @After
    fun tearDown() {
    }

    /**
     * アラーム時間取得
     *
     * 条件：なし
     * 期待結果：
     * ・localSettingRepositoryのアラーム時間取得メソッドが呼ばれること
     * ・上記で取得した値がそのまま返却されること
     */
    @Test
    fun getAlarmDate() {
        val result = settingUseCaseImp.getAlarmDate()
        coVerify(exactly = 1) { (localSettingRepository).getAlarmDate() }
        assertEquals(todayAlarmDate, result)
    }

    /**
     * アラームモード取得
     *
     * 条件：なし
     * 期待結果：
     * ・localSettingRepositoryのアラームモード取得メソッドが呼ばれること
     * ・上記で取得した値がそのまま返却されること
     */
    @Test
    fun getAlarmMode() {
        val result = settingUseCaseImp.getAlarmMode()
        coVerify(exactly = 1) { (localSettingRepository).getAlarmMode() }
        assertEquals(alarmModeNormal, result)
    }

    /**
     * アラームモード更新
     *
     * 条件：最後にレポートを登録した日付が今日
     * 期待結果：
     * ・dtoを元にア明日の日付でラーム時間が正しく変換されること
     * ・変換されたデータが登録されること
     * ・変換されたデータが返却されること
     */
    @Test
    fun updateAlarmDateByReportToday() {
        setLastReportByToday()
        val result = settingUseCaseImp.updateAlarmDate(dto)
        val act = nowDateTime.with(LocalTime.of(dto.hour, dto.minute, dto.second))
            .plusDays(1)
        coVerify(exactly = 1) { (localSettingRepository).saveAlarmDate(act) }
        coVerify(exactly = 1) { (localSettingRepository).saveAlarmMode(dto.mode) }
        assertEquals(act, result)
    }

    /**
     * アラームモード更新
     *
     * 条件：
     * ・最後にレポートを登録した日付が昨日
     * ・設定した時間より現在の時間が前
     * 期待結果：
     * ・dtoを元にア今日の日付でラーム時間が正しく変換されること
     * ・変換されたデータが登録されること
     * ・変換されたデータが返却されること
     */
    @Test
    fun updateAlarmDateByReportYesterday() {
        setLastReportByYesterday()
        val result = settingUseCaseImp.updateAlarmDate(dto)
        val act = nowDateTime.with(LocalTime.of(dto.hour, dto.minute, dto.second))
        coVerify(exactly = 1) { (localSettingRepository).saveAlarmDate(act) }
        coVerify(exactly = 1) { (localSettingRepository).saveAlarmMode(dto.mode) }
        assertEquals(act, result)
    }

    /**
     * アラームモード更新
     *
     * 条件：
     * ・最後にレポートを登録した日付が昨日
     * ・設定した時間より現在の時間が後
     * 期待結果：
     * ・dtoを元にア明日の日付でラーム時間が正しく変換されること
     * ・変換されたデータが登録されること
     * ・変換されたデータが返却されること
     */
    @Test
    fun updateAlarmDateByReportYesterdayAndNotAlarmTime() {
        setLastReportByYesterday()
        val result = settingUseCaseImp.updateAlarmDate(dto)
        val act = nowDateTime.with(LocalTime.of(dto.hour, dto.minute, dto.second))
        coVerify(exactly = 1) { (localSettingRepository).saveAlarmDate(act) }
        coVerify(exactly = 1) { (localSettingRepository).saveAlarmMode(dto.mode) }
        assertEquals(act, result)
    }
}