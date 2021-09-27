package com.myapp.domain.usecase

import com.myapp.domain.dto.MissionStatementInputDto
import com.myapp.domain.model.entity.MissionStatement
import com.myapp.domain.repository.LocalMissionStatementRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

/**
 * ミッションステートメント機能　ロジック仕様
 *
 */
class MissionStatementUseCaseImpTest {

    // region coroutine & LiveData用
    @ExperimentalCoroutinesApi
    private val coroutineDispatcher = TestCoroutineDispatcher()

    @ExperimentalCoroutinesApi
    private val testScope = TestCoroutineScope(coroutineDispatcher)

    // endregion

    // region test date

    private lateinit var useCase: MissionStatementUseCaseImp
    private lateinit var localMissionStatementRepository: LocalMissionStatementRepository

    private val funeralList1 = listOf("弔辞：笑われること", "人：芸人オンリー", "雰囲気：和気藹々")
    private val funeralList2 = listOf("弔辞：感謝されること", "人：優しい人間", "雰囲気：穏やか")
    private val purposeLife1 = "世界一楽しく生きること"
    private val purposeLife2 = "世界一優しい世界を作ること"
    private val constitutionList1 = listOf("迷ったときは楽しい方向へ", "常に楽しいを見つける努力を")
    private val constitutionList2 = listOf("弱い人の見方であれ！", "常に人助けすること")
    private val createDto = MissionStatementInputDto(funeralList1, purposeLife1, constitutionList1)
    private val missionStatement = MissionStatement(funeralList1, purposeLife1, constitutionList1)
    private val dto = MissionStatementInputDto(funeralList2, purposeLife2, constitutionList2)

    // endregion

    // region 初期設定

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        Dispatchers.setMain(coroutineDispatcher)
        localMissionStatementRepository = mockk<LocalMissionStatementRepository>().also {
            coEvery { it.getMissionStatement() } returns missionStatement
            coEvery { it.saveMissionStatement(any()) } returns Unit
        }
    }

    @ExperimentalCoroutinesApi
    @After
    fun tearDown() {
        Dispatchers.resetMain()
        coroutineDispatcher.cleanupTestCoroutines()
    }

    // endregion

    // region ミッションステートメント取得

    /**
     * ミッションステートメント取得
     *
     * 条件：ミッションステートメント登録済み
     * 期待結果：
     * ・ミッションステートメントを取得するメソッドが呼ばれること
     * ・上記で取得した値(null以外)が返却されること
     */
    @ExperimentalCoroutinesApi
    @Test
    fun getMissionStatementByNotNull() = testScope.runBlockingTest {
        useCase = MissionStatementUseCaseImp(localMissionStatementRepository)
        val result = useCase.getMissionStatement()
        coVerify(exactly = 1) { (localMissionStatementRepository).getMissionStatement() }
        assertEquals(missionStatement, result)
    }

    /**
     * ミッションステートメント取得
     *
     * 条件：ミッションステートメント未登録
     * 期待結果：
     * ・ミッションステートメントを取得するメソッドが呼ばれること
     * ・上記で取得した値(null)が返却されること
     */
    @ExperimentalCoroutinesApi
    @Test
    fun getMissionStatementByNull() = testScope.runBlockingTest {
        localMissionStatementRepository = mockk<LocalMissionStatementRepository>().also {
            coEvery { it.getMissionStatement() } returns null
        }
        useCase = MissionStatementUseCaseImp(localMissionStatementRepository)
        val result = useCase.getMissionStatement()
        coVerify(exactly = 1) { (localMissionStatementRepository).getMissionStatement() }
        assertEquals(null, result)
    }

    // endregion

    // region ミッションステートメント登録

    /**
     * ミッションステートメント登録
     *
     * 条件：なし
     * 期待結果：
     * ・ミッションステートメントを登録するメソッドが呼ばれること
     */
    @ExperimentalCoroutinesApi
    @Test
    fun createMissionStatement() = testScope.runBlockingTest {
        useCase = MissionStatementUseCaseImp(localMissionStatementRepository)
        useCase.createMissionStatement(createDto)
        coVerify(exactly = 1) { (localMissionStatementRepository).saveMissionStatement(missionStatement) }
    }

    // endregion

    // region ミッションステートメント更新

    /**
     * ミッションステートメント更新
     *
     * 条件：なし
     * 期待結果：
     * ・ミッションステートメントを登録するメソッドが呼ばれること
     */
    @ExperimentalCoroutinesApi
    @Test
    fun updateMissionStatement() = testScope.runBlockingTest {
        useCase = MissionStatementUseCaseImp(localMissionStatementRepository)
        useCase.updateMissionStatement(missionStatement, dto)
        val result = missionStatement.also {
            it._funeralList = dto.funeralList
            it._purposeLife = dto.purposeLife
            it._constitutionList = dto.constitutionList
        }
        coVerify(exactly = 1) { (localMissionStatementRepository).saveMissionStatement(result) }
    }

    // endregion
}
