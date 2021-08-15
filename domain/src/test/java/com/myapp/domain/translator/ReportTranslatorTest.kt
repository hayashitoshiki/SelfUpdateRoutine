package com.myapp.domain.translator

import com.myapp.domain.dto.AllReportInputDto
import io.mockk.every
import io.mockk.mockkStatic
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.time.LocalDateTime
import java.time.LocalTime

class ReportTranslatorTest {

    // test date
    private val dto = AllReportInputDto("fact", "find", "lean", "statement", 50, "reason", "improve")
    private val nowDateTime = LocalDateTime.now()
        .with(LocalTime.of(21, 0, 0, 0))

    @Before
    fun setUp() {
        mockkStatic(LocalDateTime::class)
        every {
            LocalDateTime.now()
        } returns nowDateTime
    }

    @After
    fun tearDown() {
    }

    /**
     * レポートInputDto　→ レポートモデル変換
     *
     * 条件：なし
     * 期待結果：
     * 想定通りに値が格納されていること
     */
    @Test
    fun allReportConvert() {
        val result = ReportTranslator.allReportConvert(dto)
        assertEquals(nowDateTime, result.ffsReport.dataTime.date)
        assertEquals(dto.factComment, result.ffsReport.factComment)
        assertEquals(dto.findComment, result.ffsReport.findComment)
        assertEquals(dto.learnComment, result.ffsReport.learnComment)
        assertEquals(dto.statementComment, result.ffsReport.statementComment)
        assertEquals(nowDateTime, result.weatherReport.dataTime.date)
        assertEquals(dto.heartScore, result.weatherReport.heartScore.data)
        assertEquals(dto.reasonComment, result.weatherReport.reasonComment)
        assertEquals(dto.improveComment, result.weatherReport.improveComment)
    }
}