package com.myapp.presentation.ui.mission_statement

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.myapp.domain.model.entity.MissionStatement
import com.myapp.domain.usecase.MissionStatementUseCase
import com.nhaarman.mockito_kotlin.mock
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

/**
 * ミッションステートメント一覧画面　ロジック仕様
 *
 */
class MissionStatementListViewModelTest {

    // LiveData用
    @ExperimentalCoroutinesApi
    private val coroutineDispatcher = TestCoroutineDispatcher()

    @ExperimentalCoroutinesApi
    private val testScope = TestCoroutineScope(coroutineDispatcher)

    @Rule
    @JvmField
    val rule: TestRule = InstantTaskExecutorRule()

    private lateinit var viewModel: MissionStatementListViewModel
    private lateinit var missionStatementUseCase: MissionStatementUseCase

    // region test date

    private val funeralList = listOf("弔辞：感謝されること", "人：優しい人間")
    private val purposeLife = "世界一楽しく生きること"
    private val constitutionList = listOf("迷ったときは楽しい方向へ", "常に楽しいを見つける努力を")
    private val missionStatement = MissionStatement(funeralList, purposeLife, constitutionList)
    private val missionStatementOnlyFuneralList = MissionStatement(funeralList, "", listOf(""))
    private val missionStatementOnlyPurposeLife = MissionStatement(listOf(""), purposeLife, listOf(""))
    private val missionStatementOnlyConstitutionList = MissionStatement(listOf(""), "", constitutionList)

    // endregion

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        Dispatchers.setMain(coroutineDispatcher)
        missionStatementUseCase = mockk<MissionStatementUseCase>().also {
            coEvery { it.getMissionStatement() } returns missionStatement
        }
    }

    private fun setMockObserver() {
        val observerString = mock<Observer<String>>()
        val observerStringList = mock<Observer<List<String>>>()
        val observerBoolean = mock<Observer<Boolean>>()
        viewModel.funeralList.observeForever(observerStringList)
        viewModel.purposeLife.observeForever(observerString)
        viewModel.isEnableFuneralList.observeForever(observerBoolean)
        viewModel.isEnablePurposeLife.observeForever(observerBoolean)
        viewModel.isEnableConstitutionList.observeForever(observerBoolean)

    }

    @ExperimentalCoroutinesApi
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    /**
     * 初期表示動作
     *
     * 条件：ミッションステートメントが登録されている
     *
     * 期待結果
     * 各値に下記のデータが格納されること
     * ・funeralList     ：理想の葬儀リスト
     * ・missionStatement：人生の目的
     * ・constitutionLis ：憲法リスト
     */
    @ExperimentalCoroutinesApi
    @Test
    fun initByMissionStatement() = testScope.runBlockingTest {
        viewModel = MissionStatementListViewModel(missionStatementUseCase)
        setMockObserver()
        assertEquals(missionStatement.funeralList, viewModel.funeralList.value)
        assertEquals(missionStatement.purposeLife, viewModel.purposeLife.value)
        assertEquals(missionStatement.constitutionList, viewModel.constitutionList.value)
    }


    /**
     * 初期表示動作
     *
     * 条件：ミッションステートメントがまだ登録されていない
     *
     * 期待結果
     * 各値に下記のデータが格納されること
     * ・funeralList     ：空のリスト
     * ・missionStatement：空
     * ・constitutionLis ：空のリスト
     */
    @ExperimentalCoroutinesApi
    @Test
    fun initByNotMissionStatement() = testScope.runBlockingTest {
        missionStatementUseCase = mockk<MissionStatementUseCase>().also {
            coEvery { it.getMissionStatement() } returns null
        }
        viewModel = MissionStatementListViewModel(missionStatementUseCase)
        setMockObserver()
        assertEquals(listOf(""), viewModel.funeralList.value)
        assertEquals("", viewModel.purposeLife.value)
        assertEquals(listOf(""), viewModel.constitutionList.value)
    }


    /**
     * ミッションステートメント更新
     *
     * 条件：ミッションステートメント編集画面でミッションステートメントが更新されること
     *
     * 期待結果
     * ミッションステートメント取得処理が走ること
     */
    @ExperimentalCoroutinesApi
    @Test
    fun updateMissionStatement() = testScope.runBlockingTest {
        missionStatementUseCase = mockk<MissionStatementUseCase>().also {
            coEvery { it.getMissionStatement() } returns null
        }
        viewModel = MissionStatementListViewModel(missionStatementUseCase)
        setMockObserver()
        coVerify(exactly = 1) { (missionStatementUseCase).getMissionStatement() }
        MissionStatementDispatcher.updateMissionStatement()
        coVerify(exactly = 2) { (missionStatementUseCase).getMissionStatement() }
    }

    // region　表示制御

    /**
     * ミッションステータス全項目登録済み
     *
     * 条件：人生の目的データが存在すること
     * 期待結果：人生の目的Flgがtrueであること
     */
    @Test
    fun idEnableAllTrue() {
        missionStatementUseCase = mockk<MissionStatementUseCase>().also {
            coEvery { it.getMissionStatement() } returns missionStatement
        }
        viewModel = MissionStatementListViewModel(missionStatementUseCase)
        setMockObserver()
        assertEquals(true, viewModel.isEnableFuneralList.value)
        assertEquals(true, viewModel.isEnablePurposeLife.value)
        assertEquals(true, viewModel.isEnableConstitutionList.value)
    }

    /**
     * ミッションステータス未登録
     *
     * 条件：人生の目的データが存在すること
     * 期待結果：人生の目的Flgがtrueであること
     */
    @Test
    fun idEnableAllFalse() {
        missionStatementUseCase = mockk<MissionStatementUseCase>().also {
            coEvery { it.getMissionStatement() } returns null
        }
        viewModel = MissionStatementListViewModel(missionStatementUseCase)
        setMockObserver()
        assertEquals(false, viewModel.isEnableFuneralList.value)
        assertEquals(false, viewModel.isEnablePurposeLife.value)
        assertEquals(false, viewModel.isEnableConstitutionList.value)
    }

    /**
     * ミッションステータス_理想の葬儀のみ登録済み
     *
     * 条件：人生の目的データが存在すること
     * 期待結果：人生の目的Flgがtrueであること
     */
    @Test
    fun idEnableFuneralListOnlyTrue() {
        missionStatementUseCase = mockk<MissionStatementUseCase>().also {
            coEvery { it.getMissionStatement() } returns missionStatementOnlyFuneralList
        }
        viewModel = MissionStatementListViewModel(missionStatementUseCase)
        setMockObserver()
        assertEquals(true, viewModel.isEnableFuneralList.value)
        assertEquals(false, viewModel.isEnablePurposeLife.value)
        assertEquals(false, viewModel.isEnableConstitutionList.value)
    }

    /**
     * ミッションステータス_人生の目的のみ登録済み
     *
     * 条件：人生の目的データが存在すること
     * 期待結果：人生の目的Flgがtrueであること
     */
    @Test
    fun idEnablePurposeLifeOnlyTrue() {
        missionStatementUseCase = mockk<MissionStatementUseCase>().also {
            coEvery { it.getMissionStatement() } returns missionStatementOnlyPurposeLife
        }
        viewModel = MissionStatementListViewModel(missionStatementUseCase)
        setMockObserver()
        assertEquals(false, viewModel.isEnableFuneralList.value)
        assertEquals(true, viewModel.isEnablePurposeLife.value)
        assertEquals(false, viewModel.isEnableConstitutionList.value)
    }

    /**
     * ミッションステータス_憲法のみ登録済み
     *
     * 条件：人生の目的データが存在すること
     * 期待結果：人生の目的Flgがtrueであること
     */
    @Test
    fun idEnableConstitutionListOnlyTrue() {
        missionStatementUseCase = mockk<MissionStatementUseCase>().also {
            coEvery { it.getMissionStatement() } returns missionStatementOnlyConstitutionList
        }
        viewModel = MissionStatementListViewModel(missionStatementUseCase)
        setMockObserver()
        assertEquals(false, viewModel.isEnableFuneralList.value)
        assertEquals(false, viewModel.isEnablePurposeLife.value)
        assertEquals(true, viewModel.isEnableConstitutionList.value)
    }

    // endregion
}