package com.myapp.presentation.ui.mission_statement

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockito_kotlin.mock
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

/**
 * 憲法アイテム　ロジック仕様
 *
 */
class ConstitutionInputItemViewModelTest {

    @ExperimentalCoroutinesApi
    private val coroutineDispatcher = TestCoroutineDispatcher()

    @ExperimentalCoroutinesApi
    private val testScope = TestCoroutineScope(coroutineDispatcher)

    // LiveData用
    @Rule
    @JvmField
    val rule: TestRule = InstantTaskExecutorRule()

    private lateinit var viewModel: ConstitutionInputItemViewModel

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        Dispatchers.setMain(coroutineDispatcher)
    }

    @ExperimentalCoroutinesApi
    @After
    fun tearDown() {
        Dispatchers.resetMain()
        coroutineDispatcher.cleanupTestCoroutines()
    }

    private fun setMockObserver() {
        val observerString = mock<Observer<String>>()
        val observerBoolean = mock<Observer<Boolean>>()
        viewModel.isPlusButtonEnable.observeForever(observerBoolean)
        viewModel.isMinusButtonEnable.observeForever(observerBoolean)
        viewModel.value.observeForever(observerString)
    }

    /**
     * 理想の葬式編集初期表示
     *
     * 条件：一番先頭のリスト
     * 期待結果：マイナスボタンが表示されないこと
     */
    @Test
    fun initByIndexFirst() {
        viewModel = ConstitutionInputItemViewModel(0, 1, "test")
        setMockObserver()
        assertEquals(true, viewModel.isPlusButtonEnable.value)
        assertEquals(false, viewModel.isMinusButtonEnable.value)
        assertEquals("test", viewModel.value.value)
    }

    /**
     * 理想の葬式編集初期表示
     *
     * 条件：２つ目以降ののリスト
     * 期待結果：マイナスボタンが表示されること
     */
    @Test
    fun initByIndexSecond() {
        viewModel = ConstitutionInputItemViewModel(1, 2, "test")
        setMockObserver()
        assertEquals(true, viewModel.isPlusButtonEnable.value)
        assertEquals(true, viewModel.isMinusButtonEnable.value)
        assertEquals("test", viewModel.value.value)
    }

    /**
     * 理想の葬式文字列修正
     *
     * 条件：なし
     * 期待結果；理想の葬式変更通知Dispatcherへ通知されること
     */
    @ExperimentalCoroutinesApi
    @Test
    fun changeFuneral() = testScope.runBlockingTest {
        var result = Pair(0, "aaa")
        val act = Pair(0, "test1")
        MissionStatementDispatcher.constitutionText.onEach {
            result = it
        }
            .launchIn(testScope)
        viewModel = ConstitutionInputItemViewModel(0, 1, "aaa")
        setMockObserver()
        viewModel.value.value = "test1"
        assertEquals(act, result)
    }

    /**
     * 理想の葬式文字列削除
     *
     * 条件：なし
     * 期待結果：理想の葬式削除通知のDispatcherへ通知されること
     *
     */
    @ExperimentalCoroutinesApi
    @Test
    fun deleteFuneral() = testScope.runBlockingTest {
        val index = 3
        var result = 0
        MissionStatementDispatcher.constitutionMinusButton.onEach {
            result = it
        }
            .launchIn(testScope)
        viewModel = ConstitutionInputItemViewModel(index, 1, "test")
        setMockObserver()
        viewModel.onClickMinusButton()
        assertEquals(index, result)
    }

    /**
     * 理想の葬式リスト追加
     *
     * 条件：なし
     * 期待結果：理想の葬式リスト追加通知のDispatcherへ通知されること
     */
    @ExperimentalCoroutinesApi
    @Test
    fun addFuneral() = testScope.runBlockingTest {
        val index = 3
        var result = 0
        MissionStatementDispatcher.constitutionPlusButton.onEach {
            result = it
        }
            .launchIn(testScope)
        viewModel = ConstitutionInputItemViewModel(index, 2, "test")
        setMockObserver()
        viewModel.onClickPlusButton()
        assertEquals(index + 1, result)
    }
}
