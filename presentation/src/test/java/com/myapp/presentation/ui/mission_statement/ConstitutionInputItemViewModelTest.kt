package com.myapp.presentation.ui.mission_statement

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.myapp.presentation.utils.base.BaseInputTextItemContract
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
    private val state = BaseInputTextItemContract.State()
    private var resultAction: MissionStatementDispatcherContract.Action? = null


    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        Dispatchers.setMain(coroutineDispatcher)
        MissionStatementDispatcher.action.onEach { resultAction = it }
            .launchIn(testScope)
    }

    @ExperimentalCoroutinesApi
    @After
    fun tearDown() {
        Dispatchers.resetMain()
        coroutineDispatcher.cleanupTestCoroutines()
    }

    /**
     * 実行結果比較
     *
     * @param state Stateの期待値
     * @param effect Effectの期待値
     * @param action actionの期待値
     */
    @ExperimentalCoroutinesApi
    private fun result(
        state: BaseInputTextItemContract.State,
        effect: BaseInputTextItemContract.Effect?,
        action: MissionStatementDispatcherContract.Action?
    ) = testScope.runBlockingTest {
        val resultState = viewModel.state.value
        val resultEffect: BaseInputTextItemContract.Effect? = null

        // 比較
        assertEquals(resultState, state)
        assertEquals(resultAction, action)
        assertEquals(resultEffect, effect)
    }

    // region 初期表示

    /**
     * 理想の葬式編集初期表示
     *
     * 条件：リストの先頭の項目
     * 期待結果；
     * ・画面の値
     * 　　・テキスト　　　　：渡された値
     * 　　・＋ボタン表示制御：表示
     * 　　・ーボタン表示制御：非表示
     * ・画面イベント
     * 　　ー
     * ・共通処理イベント
     * 　　ー
     */
    @ExperimentalCoroutinesApi
    @Test
    fun initByIndexFirst() {

        // 期待結果
        val textValue = "test"
        val expectationsState = state.copy(value = textValue, isPlusButtonVisibility = true, isMinusButtonVisibility = false)
        val expectationsEffect = null
        val expectationsAction = null

        // 実施
        viewModel = ConstitutionInputItemViewModel(0, 1, textValue)

        // 比較
        result(expectationsState, expectationsEffect, expectationsAction)
    }

    /**
     * 理想の葬式編集初期表示
     *
     * 条件：リストの先頭以外項目
     * 期待結果；
     * ・画面の値
     * 　　・テキスト　　　　：渡された値
     * 　　・＋ボタン表示制御：表示
     * 　　・ーボタン表示制御：表示
     * ・画面イベント
     * 　　ー
     * ・共通処理イベント
     * 　　ー
     */
    @ExperimentalCoroutinesApi
    @Test
    fun initByIndexSecond() {

        // 期待結果
        val textValue = "test"
        val expectationsState = state.copy(value = textValue, isPlusButtonVisibility = true, isMinusButtonVisibility = true)
        val expectationsEffect = null
        val expectationsAction = null

        // 実施
        viewModel = ConstitutionInputItemViewModel(1, 2, "test")

        // 比較
        result(expectationsState, expectationsEffect, expectationsAction)
    }

    // endregion

    // region 理想の葬式テキスト修正

    /**
     * 理想の葬式文字列修正
     *
     * 条件：テキストを入力
     * 期待結果；理想の葬式変更通知Dispatcherへ通知されること
     * 条件：リストの先頭以外項目
     * 期待結果；
     * ・画面の値
     * 　　・テキスト　　　　：変更した値
     * 　　・＋ボタン表示制御：表示
     * 　　・ーボタン表示制御：表示
     * ・画面イベント
     * 　　ー
     * ・共通処理イベント
     * 　　憲法テキスト変更アクションが流れること
     */
    @ExperimentalCoroutinesApi
    @Test
    fun changeConstitutionByAdd() = testScope.runBlockingTest {

        // 期待結果
        val afterValue = "test"
        val expectationsState = state.copy(value = afterValue, isPlusButtonVisibility = true, isMinusButtonVisibility = true)
        val expectationsEffect = null
        val expectationsAction = MissionStatementDispatcherContract.Action.ChangeConstitutionText(1, afterValue)

        // 実施
        viewModel = ConstitutionInputItemViewModel(1, 2, "changeText")
        viewModel.setEvent(BaseInputTextItemContract.Event.ChangeText(afterValue))

        // 比較
        result(expectationsState, expectationsEffect, expectationsAction)
    }

    /**
     * 理想の葬式文字列修正
     *
     * 条件：理想の葬式文字列削除
     * 期待結果；理想の葬式変更通知Dispatcherへ通知されること
     * 条件：リストの先頭以外項目
     * 期待結果；
     * ・画面の値
     * 　　・テキスト　　　　：変更した値
     * 　　・＋ボタン表示制御：表示
     * 　　・ーボタン表示制御：表示
     * ・画面イベント
     * 　　ー
     * ・共通処理イベント
     * 　憲法テキスト変更アクションが流れること
    */
    @ExperimentalCoroutinesApi
    @Test
    fun changeConstitutionByDelete() = testScope.runBlockingTest {

        // 期待結果
        val afterValue = ""
        val expectationsState = state.copy(value = afterValue, isPlusButtonVisibility = true, isMinusButtonVisibility = true)
        val expectationsEffect = null
        val expectationsAction = MissionStatementDispatcherContract.Action.ChangeConstitutionText(1, afterValue)

        // 実施
        viewModel = ConstitutionInputItemViewModel(1, 2, "changeText")
        viewModel.setEvent(BaseInputTextItemContract.Event.ChangeText(afterValue))

        // 比較
        result(expectationsState, expectationsEffect, expectationsAction)
    }

    // endregion

    // region 理想の葬式リスト追加

    /**
     * 理想の葬式リスト追加
     *
     * 条件：なし
     * 期待結果；
     * ・画面の値
     * 　　ー
     * ・画面イベント
     * 　　ー
     * ・共通処理イベント
     * 　　憲法項目追加アクションが流れること
     */
    @ExperimentalCoroutinesApi
    @Test
    fun addConstitution() = testScope.runBlockingTest {

        // 期待結果
        val textValue = "test"
        val indexValue = 1
        val expectationsState = state.copy(value = textValue, isPlusButtonVisibility = true, isMinusButtonVisibility = true)
        val expectationsEffect = null
        val expectationsAction = MissionStatementDispatcherContract.Action.AddConstitution(indexValue + 1)

        // 実施
        viewModel = ConstitutionInputItemViewModel(indexValue, 2, textValue)
        viewModel.setEvent(BaseInputTextItemContract.Event.OnClickPlusButton)

        // 比較
        result(expectationsState, expectationsEffect, expectationsAction)
    }

    // endregion

    // region 理想の葬式リスト削除

    /**
     * 理想の葬式リスト追加
     *
     * 条件：なし
     * 期待結果；
     * ・画面の値
     * 　　ー
     * ・画面イベント
     * 　　ー
     * ・共通処理イベント
     * 　　憲法項目追加アクションが流れること
     */
    @ExperimentalCoroutinesApi
    @Test
    fun deleteConstitution() = testScope.runBlockingTest {

        // 期待結果
        val textValue = "test"
        val indexValue = 1
        val expectationsState = state.copy(value = textValue, isPlusButtonVisibility = true, isMinusButtonVisibility = true)
        val expectationsEffect = null
        val expectationsAction = MissionStatementDispatcherContract.Action.DeleteConstitution(indexValue)

        // 実施
        viewModel = ConstitutionInputItemViewModel(indexValue, 2, textValue)
        viewModel.setEvent(BaseInputTextItemContract.Event.OnClickMinusButton)

        // 比較
        result(expectationsState, expectationsEffect, expectationsAction)
    }

    // endregion

}
