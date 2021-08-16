package com.myapp.presentation.ui.constitution

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.myapp.domain.model.entity.MissionStatement
import com.myapp.domain.usecase.MissionStatementUseCase
import com.nhaarman.mockito_kotlin.mock
import io.mockk.coEvery
import io.mockk.mockk
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class ConstitutionListViewModelTest {

    // LiveData用
    @Rule
    @JvmField
    val rule: TestRule = InstantTaskExecutorRule()

    private lateinit var viewModel: ConstitutionListViewModel
    private lateinit var missionStatementUseCase: MissionStatementUseCase

    // region test date

    private val funeralList = listOf("弔辞：感謝されること", "人：優しい人間")
    private val purposeLife = "世界一楽しく生きること"
    private val constitutionList = listOf("迷ったときは楽しい方向へ", "常に楽しいを見つける努力を")
    private val missionStatement = MissionStatement(funeralList, purposeLife, constitutionList)

    // endregion

    @Before
    fun setUp() {
        missionStatementUseCase = mockk<MissionStatementUseCase>().also {
            coEvery { it.getMissionStatement() } returns missionStatement
        }

    }

    private fun setMockObserver() {
        val observerString = mock<Observer<String>>()
        val observerStringList = mock<Observer<List<String>>>()
        viewModel.funeralList.observeForever(observerStringList)
        viewModel.purposeLife.observeForever(observerString)
        viewModel.constitutionList.observeForever(observerStringList)
    }

    @After
    fun tearDown() {
    }

    /**
     * 初期表示動作
     *
     * 条件：なし
     *
     * 期待結果
     * 各値に下記のデータが格納されること
     * ・funeralList     ：理想の葬儀リスト
     * ・missionStatement：人生の目的
     * ・constitutionLis ：憲法リスト
     */
    @Test
    fun init() {
        viewModel = ConstitutionListViewModel(missionStatementUseCase)
        setMockObserver()
        assertEquals(missionStatement.funeralList, viewModel.funeralList.value)
        assertEquals(missionStatement.purposeLife, viewModel.purposeLife.value)
        assertEquals(missionStatement.constitutionList, viewModel.constitutionList.value)
    }
}