package com.myapp.domain.translator

import com.myapp.domain.dto.MissionStatementInputDto
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

/**
 * ミッションステートメント変換Translator 変換仕様
 *
 */
class MissionStatementTranslatorTest {

    private val funeralList = listOf("弔辞：笑われること", "人：芸人オンリー", "雰囲気：和気藹々")
    private val purposeLife = "世界一楽しく生きること"
    private val constitutionList = listOf("迷ったときは楽しい方向へ", "常に楽しいを見つける努力を")

    private val dto = MissionStatementInputDto(funeralList, purposeLife, constitutionList)

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    /**
     * ミッションステートメントDtoからミッションステートメントオブジェクト生成
     *
     * 条件：なし
     * 期待結果：
     * 下記の内容でミッションステートメントオブジェクトが生成されること
     *　・理想の葬儀：ミッションステートメントdtoの理想の葬儀
     *  ・人生の目的：ミッションステートメントdtoの人生の目的
     *  ・憲法　　　：ミッションステートメントdtoの憲法
     */
    @Test
    fun missionStatementConvert() {
        val result = MissionStatementTranslator.missionStatementFromMissionStatementDto(dto)
        assertEquals(dto.funeralList, result.funeralList)
        assertEquals(dto.purposeLife, result.purposeLife)
        assertEquals(dto.constitutionList, result.constitutionList)
    }
}