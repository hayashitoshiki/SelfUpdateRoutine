package com.myapp.presentation.ui.mission_statement

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.myapp.domain.dto.MissionStatementInputDto
import com.myapp.domain.model.entity.MissionStatement
import com.myapp.domain.usecase.MissionStatementUseCase
import com.myapp.presentation.R
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.*
import org.junit.Assert.assertEquals
import org.junit.rules.TestRule

/**
 * ミッションステートメント設定画面　ロジック仕様
 *
 */
class MissionStatementSettingViewModelTest {

    // region coroutine & LiveData用
    @ExperimentalCoroutinesApi
    private val coroutineDispatcher = TestCoroutineDispatcher()

    @ExperimentalCoroutinesApi
    private val testScope = TestCoroutineScope(coroutineDispatcher)

    @Rule
    @JvmField
    val rule: TestRule = InstantTaskExecutorRule()

    // endregion

    // region test date
    private val state = MissionStatementSettingContract.State()
    private lateinit var viewModel: MissionStatementSettingViewModel
    private lateinit var missionStatementUseCase: MissionStatementUseCase

    private val funeralList = listOf("弔辞：感謝されること", "人：優しい人間", "雰囲気：和気藹々")
    private val purposeLife = "世界一楽しく生きること"
    private val constitutionList = listOf("迷ったときは楽しい方向へ", "常に楽しいを見つける努力を", "判断基準は周りが笑顔になるかどどうか")
    private val missionStatement = MissionStatement(funeralList, purposeLife, constitutionList)

    // endregion

    // region 初期設定

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        Dispatchers.setMain(coroutineDispatcher)
        missionStatementUseCase = mockk<MissionStatementUseCase>().also {
            coEvery { it.getMissionStatement() } returns missionStatement
            coEvery { it.createMissionStatement(any()) } returns Unit
            coEvery { it.updateMissionStatement(any(), any()) } returns Unit
        }
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
     */
    @ExperimentalCoroutinesApi
    private fun result(
        state: MissionStatementSettingContract.State,
        effect: MissionStatementSettingContract.Effect?
    ) = testScope.runBlockingTest {
        val resultState = viewModel.state.value
        val resultEffect = viewModel.effect.value

        // 比較
        assertEquals(resultState, state)
        if (resultEffect is MissionStatementSettingContract.Effect.ShowError) {
            val resultMessage = resultEffect.value.message
            val message = (effect as MissionStatementSettingContract.Effect.ShowError).value.message
            assertEquals(resultMessage, message)
        } else {
            assertEquals(resultEffect, effect)
        }
        assertEquals(resultEffect, effect)
    }

    // endregion

    // region 理想の葬式リストロジック

    /**
     * 理想の葬式リストの内容変更
     *
     * 条件：指定したIDのリストが存在しない
     * 期待結果；
     * ・画面の値
     * 　　指定したIDのテキストが渡された値で変更されること
     * ・画面イベント
     * 　　ー
     * ・共通処理イベント
     * 　　ー
     * ・業務ロジック
     * 　ー
     */
    @ExperimentalCoroutinesApi
    @Test
    fun changeFuneralTextByFoundIndex() = testScope.runBlockingTest {

        // 期待結果
        val value = "test01"
        val id = 3L
        val expectationsEffect = null
        var funeralListCount = 1L
        var constitutionListCount = 1L
        val funeralList = missionStatement.funeralList
            .map { funeral -> Pair(funeralListCount++, funeral) }
            .toMutableList()
        val constitutionList = missionStatement.constitutionList
            .map { constitution -> Pair(constitutionListCount++, constitution) }
            .toMutableList()
        val index = funeralList.indexOfFirst { it.first == id }
        funeralList[index] = Pair(id, value)
        val expectationsState = state.copy(
            funeralListCount = funeralListCount,
            funeralList = funeralList,
            funeralListDiffColor = R.color.text_color_light_primary,
            purposeLife = missionStatement.purposeLife,
            purposeLifeDiffColor = R.color.text_color_light_secondary,
            constitutionListCount = constitutionListCount,
            constitutionList = constitutionList,
            constitutionListDiffColor= R.color.text_color_light_secondary,
            isEnableConfirmButton = true
        )

        //実施
        viewModel = MissionStatementSettingViewModel(missionStatement, missionStatementUseCase)
        MissionStatementDispatcher.setActions(MissionStatementDispatcherContract.Action.ChangeFuneralText(id, value))

        // 比較
        result(expectationsState, expectationsEffect)
    }

    /**
     * 理想の葬式リストの内容変更
     *
     * 条件：指定したIDのリストが存在しない
     * 期待結果；
     * ・画面の値
     * 　　何も値が変更されないこと
     * ・画面イベント
     * 　　ー
     * ・共通処理イベント
     * 　　ー
     * ・業務ロジック
     * 　ー
     */
    @ExperimentalCoroutinesApi
    @Test
    fun changeFuneralTextByNotFoundIndex() = testScope.runBlockingTest {

        // 期待結果
        val value = "test01"
        val id = 100L
        val expectationsEffect = null
        var funeralListCount = 1L
        var constitutionListCount = 1L
        val funeralList = missionStatement.funeralList
            .map { funeral -> Pair(funeralListCount++, funeral) }
            .toMutableList()
        val constitutionList = missionStatement.constitutionList
            .map { constitution -> Pair(constitutionListCount++, constitution) }
            .toMutableList()
        val expectationsState = state.copy(
            funeralListCount = funeralListCount,
            funeralList = funeralList,
            funeralListDiffColor = R.color.text_color_light_secondary,
            purposeLife = missionStatement.purposeLife,
            purposeLifeDiffColor = R.color.text_color_light_secondary,
            constitutionListCount = constitutionListCount,
            constitutionList = constitutionList,
            constitutionListDiffColor= R.color.text_color_light_secondary,
            isEnableConfirmButton = false
        )

        //実施
        viewModel = MissionStatementSettingViewModel(missionStatement, missionStatementUseCase)
        MissionStatementDispatcher.setActions(MissionStatementDispatcherContract.Action.ChangeFuneralText(id, value))

        // 比較
        result(expectationsState, expectationsEffect)
    }

    /**
     * 理想の葬式リストのリスト削除
     *
     * 条件：指定したインデックスが存在する
     * 期待結果；
     * ・画面の値
     * 　　理想の葬式リストの指定したインデックスが削除されること
     * ・画面イベント
     * 　　ー
     * ・共通処理イベント
     * 　　ー
     * ・業務ロジック
     * 　ー
     */
    @ExperimentalCoroutinesApi
    @Test
    fun deleteFuneralItemByFoundIndex() = testScope.runBlockingTest {

        // 期待結果
        val value = 2
        val expectationsEffect = null
        var funeralListCount = 1L
        var constitutionListCount = 1L
        val funeralList = missionStatement.funeralList
            .map { funeral -> Pair(funeralListCount++, funeral) }
            .toMutableList()
        val constitutionList = missionStatement.constitutionList
            .map { constitution -> Pair(constitutionListCount++, constitution) }
            .toMutableList()
        funeralList.removeAt(value)
        val expectationsState = state.copy(
            funeralListCount = funeralListCount,
            funeralList = funeralList,
            funeralListDiffColor = R.color.text_color_light_primary,
            purposeLife = missionStatement.purposeLife,
            purposeLifeDiffColor = R.color.text_color_light_secondary,
            constitutionListCount = constitutionListCount,
            constitutionList = constitutionList,
            constitutionListDiffColor= R.color.text_color_light_secondary,
            isEnableConfirmButton = true
        )

        //実施
        viewModel = MissionStatementSettingViewModel(missionStatement, missionStatementUseCase)
        MissionStatementDispatcher.setActions(MissionStatementDispatcherContract.Action.DeleteFuneral(value))

        // 比較
        result(expectationsState, expectationsEffect)
    }

    /**
     * 理想の葬式リストのリスト削除
     *
     * 条件：指定したインデックスが存在しない
     * 期待結果；
     * ・画面の値
     * 　　何も変更されないこと
     * ・画面イベント
     * 　　ー
     * ・共通処理イベント
     * 　　ー
     * ・業務ロジック
     * 　ー
     */
    @ExperimentalCoroutinesApi
    @Test
    fun deleteFuneralItemByNotFoundIndex() = testScope.runBlockingTest {

        // 期待結果
        val value = 200
        val expectationsEffect = null
        var funeralListCount = 1L
        var constitutionListCount = 1L
        val funeralList = missionStatement.funeralList
            .map { funeral -> Pair(funeralListCount++, funeral) }
            .toMutableList()
        val constitutionList = missionStatement.constitutionList
            .map { constitution -> Pair(constitutionListCount++, constitution) }
            .toMutableList()
        val expectationsState = state.copy(
            funeralListCount = funeralListCount,
            funeralList = funeralList,
            funeralListDiffColor = R.color.text_color_light_secondary,
            purposeLife = missionStatement.purposeLife,
            purposeLifeDiffColor = R.color.text_color_light_secondary,
            constitutionListCount = constitutionListCount,
            constitutionList = constitutionList,
            constitutionListDiffColor= R.color.text_color_light_secondary,
            isEnableConfirmButton = false
        )

        //実施
        viewModel = MissionStatementSettingViewModel(missionStatement, missionStatementUseCase)
        MissionStatementDispatcher.setActions(MissionStatementDispatcherContract.Action.DeleteFuneral(value))

        // 比較
        result(expectationsState, expectationsEffect)
    }

    /**
     * 理想の葬式リストのリスト追加
     *
     * 条件：指定したインデックスが存在する
     * 期待結果；
     * ・画面の値
     * 　・理想の葬式リストの指定したインデックスに空のリストが追加されること
     * ・画面イベント
     * 　　ー
     * ・共通処理イベント
     * 　　ー
     * ・業務ロジック
     * 　ー
     */
    @ExperimentalCoroutinesApi
    @Test
    fun addFuneralItemByFoundIndex() = testScope.runBlockingTest {

        // 期待結果
        val value = 2
        val expectationsEffect = null
        var funeralListCount = 1L
        var constitutionListCount = 1L
        val funeralList = missionStatement.funeralList
            .map { funeral -> Pair(funeralListCount++, funeral) }
            .toMutableList()
        val constitutionList = missionStatement.constitutionList
            .map { constitution -> Pair(constitutionListCount++, constitution) }
            .toMutableList()
        funeralList.add(value, Pair(funeralListCount, ""))
        val expectationsState = state.copy(
            funeralListCount = funeralListCount + 1,
            funeralList = funeralList,
            funeralListDiffColor = R.color.text_color_light_secondary,
            purposeLife = missionStatement.purposeLife,
            purposeLifeDiffColor = R.color.text_color_light_secondary,
            constitutionListCount = constitutionListCount,
            constitutionList = constitutionList,
            constitutionListDiffColor= R.color.text_color_light_secondary,
            isEnableConfirmButton = false
        )

        //実施
        viewModel = MissionStatementSettingViewModel(missionStatement, missionStatementUseCase)
        MissionStatementDispatcher.setActions(MissionStatementDispatcherContract.Action.AddFuneral(value))

        // 比較
        result(expectationsState, expectationsEffect)
    }

    /**
     * 理想の葬式リストのリスト追加
     *
     * 条件：指定したインデックスが存在しない
     * 期待結果；
     * ・画面の値
     * 　　値が変更されないこと
     * ・画面イベント
     * 　　ー
     * ・共通処理イベント
     * 　　ー
     * ・業務ロジック
     * 　ー
     */
    @ExperimentalCoroutinesApi
    @Test
    fun addFuneralItemByNotFoundIndex() = testScope.runBlockingTest {

        // 期待結果
        val value = 200
        val expectationsEffect = null
        var funeralListCount = 1L
        var constitutionListCount = 1L
        val funeralList = missionStatement.funeralList
            .map { funeral -> Pair(funeralListCount++, funeral) }
            .toMutableList()
        val constitutionList = missionStatement.constitutionList
            .map { constitution -> Pair(constitutionListCount++, constitution) }
            .toMutableList()
        val expectationsState = state.copy(
            funeralListCount = funeralListCount,
            funeralList = funeralList,
            funeralListDiffColor = R.color.text_color_light_secondary,
            purposeLife = missionStatement.purposeLife,
            purposeLifeDiffColor = R.color.text_color_light_secondary,
            constitutionListCount = constitutionListCount,
            constitutionList = constitutionList,
            constitutionListDiffColor= R.color.text_color_light_secondary,
            isEnableConfirmButton = false
        )

        //実施
        viewModel = MissionStatementSettingViewModel(missionStatement, missionStatementUseCase)
        MissionStatementDispatcher.setActions(MissionStatementDispatcherContract.Action.AddFuneral(value))

        // 比較
        result(expectationsState, expectationsEffect)
    }

    // endregion

    // region 憲法リストロジック

    /**
     * 憲法リストの内容変更
     *
     * 条件：指定したIDのリストが存在しない
     * 期待結果；
     * ・画面の値
     * 　　指定したIDのテキストが渡された値で変更されること
     * ・画面イベント
     * 　　ー
     * ・共通処理イベント
     * 　　ー
     * ・業務ロジック
     * 　ー
     */
    @ExperimentalCoroutinesApi
    @Test
    fun changeConstitutionTextByFoundIndex() = testScope.runBlockingTest {
        // 期待結果
        val value = "test01"
        val id = 3L
        val expectationsEffect = null
        var funeralListCount = 1L
        var constitutionListCount = 1L
        val funeralList = missionStatement.funeralList
            .map { funeral -> Pair(funeralListCount++, funeral) }
            .toMutableList()
        val constitutionList = missionStatement.constitutionList
            .map { constitution -> Pair(constitutionListCount++, constitution) }
            .toMutableList()
        val index = constitutionList.indexOfFirst { it.first == id }
        constitutionList[index] = Pair(id, value)
        val expectationsState = state.copy(
            funeralListCount = funeralListCount,
            funeralList = funeralList,
            funeralListDiffColor = R.color.text_color_light_secondary,
            purposeLife = missionStatement.purposeLife,
            purposeLifeDiffColor = R.color.text_color_light_secondary,
            constitutionListCount = constitutionListCount,
            constitutionList = constitutionList,
            constitutionListDiffColor= R.color.text_color_light_primary,
            isEnableConfirmButton = true
        )

        //実施
        viewModel = MissionStatementSettingViewModel(missionStatement, missionStatementUseCase)
        MissionStatementDispatcher.setActions(MissionStatementDispatcherContract.Action.ChangeConstitutionText(id, value))

        // 比較
        result(expectationsState, expectationsEffect)
    }

    /**
     * 憲法リストの内容変更
     *
     * 条件：指定したIDのリストが存在しない
     * 期待結果；
     * ・画面の値
     * 　　何も値が変更されないこと
     * ・画面イベント
     * 　　ー
     * ・共通処理イベント
     * 　　ー
     * ・業務ロジック
     * 　ー
     */
    @ExperimentalCoroutinesApi
    @Test
    fun changeConstitutionTextByNotFoundIndex() = testScope.runBlockingTest {
        // 期待結果
        val value = "test01"
        val id = 100L
        val expectationsEffect = null
        var funeralListCount = 1L
        var constitutionListCount = 1L
        val funeralList = missionStatement.funeralList
            .map { funeral -> Pair(funeralListCount++, funeral) }
            .toMutableList()
        val constitutionList = missionStatement.constitutionList
            .map { constitution -> Pair(constitutionListCount++, constitution) }
            .toMutableList()
        val expectationsState = state.copy(
            funeralListCount = funeralListCount,
            funeralList = funeralList,
            funeralListDiffColor = R.color.text_color_light_secondary,
            purposeLife = missionStatement.purposeLife,
            purposeLifeDiffColor = R.color.text_color_light_secondary,
            constitutionListCount = constitutionListCount,
            constitutionList = constitutionList,
            constitutionListDiffColor= R.color.text_color_light_secondary,
            isEnableConfirmButton = false
        )

        //実施
        viewModel = MissionStatementSettingViewModel(missionStatement, missionStatementUseCase)
        MissionStatementDispatcher.setActions(MissionStatementDispatcherContract.Action.ChangeConstitutionText(id, value))

        // 比較
        result(expectationsState, expectationsEffect)
    }

    /**
     * 憲法リストのリスト削除
     *
     * 条件：指定したインデックスが存在する
     * 期待結果；
     * ・画面の値
     * 　　憲法リストの指定したインデックスが削除されること
     * ・画面イベント
     * 　　ー
     * ・共通処理イベント
     * 　　ー
     * ・業務ロジック
     * 　ー
     */
    @ExperimentalCoroutinesApi
    @Test
    fun deleteConstitutionItemByFoundIndex() = testScope.runBlockingTest {

        // 期待結果
        val value = 2
        val expectationsEffect = null
        var funeralListCount = 1L
        var constitutionListCount = 1L
        val funeralList = missionStatement.funeralList
            .map { funeral -> Pair(funeralListCount++, funeral) }
            .toMutableList()
        val constitutionList = missionStatement.constitutionList
            .map { constitution -> Pair(constitutionListCount++, constitution) }
            .toMutableList()
        constitutionList.removeAt(value)
        val expectationsState = state.copy(
            funeralListCount = funeralListCount,
            funeralList = funeralList,
            funeralListDiffColor = R.color.text_color_light_secondary,
            purposeLife = missionStatement.purposeLife,
            purposeLifeDiffColor = R.color.text_color_light_secondary,
            constitutionListCount = constitutionListCount,
            constitutionList = constitutionList,
            constitutionListDiffColor= R.color.text_color_light_primary,
            isEnableConfirmButton = true
        )

        //実施
        viewModel = MissionStatementSettingViewModel(missionStatement, missionStatementUseCase)
        MissionStatementDispatcher.setActions(MissionStatementDispatcherContract.Action.DeleteConstitution(value))

        // 比較
        result(expectationsState, expectationsEffect)
    }

    /**
     * 憲法リストのリスト削除
     *
     * 条件：指定したインデックスが存在しない
     * 期待結果；
     * ・画面の値
     * 　　何も変更されないこと
     * ・画面イベント
     * 　　ー
     * ・共通処理イベント
     * 　　ー
     * ・業務ロジック
     * 　ー
     */
    @ExperimentalCoroutinesApi
    @Test
    fun deleteConstitutionItemByNotFoundIndex() = testScope.runBlockingTest {

        // 期待結果
        val value = 200
        val expectationsEffect = null
        var funeralListCount = 1L
        var constitutionListCount = 1L
        val funeralList = missionStatement.funeralList
            .map { funeral -> Pair(funeralListCount++, funeral) }
            .toMutableList()
        val constitutionList = missionStatement.constitutionList
            .map { constitution -> Pair(constitutionListCount++, constitution) }
            .toMutableList()
        val expectationsState = state.copy(
            funeralListCount = funeralListCount,
            funeralList = funeralList,
            funeralListDiffColor = R.color.text_color_light_secondary,
            purposeLife = missionStatement.purposeLife,
            purposeLifeDiffColor = R.color.text_color_light_secondary,
            constitutionListCount = constitutionListCount,
            constitutionList = constitutionList,
            constitutionListDiffColor= R.color.text_color_light_secondary,
            isEnableConfirmButton = false
        )

        //実施
        viewModel = MissionStatementSettingViewModel(missionStatement, missionStatementUseCase)
        MissionStatementDispatcher.setActions(MissionStatementDispatcherContract.Action.DeleteConstitution(value))

        // 比較
        result(expectationsState, expectationsEffect)
    }

    /**
     * 憲法リストのリスト追加
     *
     * 条件：指定したインデックスが存在する
     * 期待結果；
     * ・画面の値
     * 　　憲法リストの指定したインデックスに空のリストが追加されること
     * ・画面イベント
     * 　　ー
     * ・共通処理イベント
     * 　　ー
     * ・業務ロジック
     * 　ー
     */
    @ExperimentalCoroutinesApi
    @Test
    fun addConstitutionItemByFoundIndex() = testScope.runBlockingTest {

        // 期待結果
        val value = 2
        val expectationsEffect = null
        var funeralListCount = 1L
        var constitutionListCount = 1L
        val funeralList = missionStatement.funeralList
            .map { funeral -> Pair(funeralListCount++, funeral) }
            .toMutableList()
        val constitutionList = missionStatement.constitutionList
            .map { constitution -> Pair(constitutionListCount++, constitution) }
            .toMutableList()
        constitutionList.add(value, Pair(constitutionListCount, ""))
        val expectationsState = state.copy(
            funeralListCount = funeralListCount,
            funeralList = funeralList,
            funeralListDiffColor = R.color.text_color_light_secondary,
            purposeLife = missionStatement.purposeLife,
            purposeLifeDiffColor = R.color.text_color_light_secondary,
            constitutionListCount = constitutionListCount + 1,
            constitutionList = constitutionList,
            constitutionListDiffColor= R.color.text_color_light_secondary,
            isEnableConfirmButton = false
        )

        //実施
        viewModel = MissionStatementSettingViewModel(missionStatement, missionStatementUseCase)
        MissionStatementDispatcher.setActions(MissionStatementDispatcherContract.Action.AddConstitution(value))

        // 比較
        result(expectationsState, expectationsEffect)
    }

    /**
     * 憲法リストのリスト追加
     *
     * 条件：指定したインデックスが存在しない
     * 期待結果；
     * ・画面の値
     * 　　値が変更されないこと
     * ・画面イベント
     * 　　ー
     * ・共通処理イベント
     * 　　ー
     * ・業務ロジック
     * 　ー
     */
    @ExperimentalCoroutinesApi
    @Test
    fun addConstitutionItemByNotFoundIndex() = testScope.runBlockingTest {

        // 期待結果
        val value = 200
        val expectationsEffect = null
        var funeralListCount = 1L
        var constitutionListCount = 1L
        val funeralList = missionStatement.funeralList
            .map { funeral -> Pair(funeralListCount++, funeral) }
            .toMutableList()
        val constitutionList = missionStatement.constitutionList
            .map { constitution -> Pair(constitutionListCount++, constitution) }
            .toMutableList()
        val expectationsState = state.copy(
            funeralListCount = funeralListCount,
            funeralList = funeralList,
            funeralListDiffColor = R.color.text_color_light_secondary,
            purposeLife = missionStatement.purposeLife,
            purposeLifeDiffColor = R.color.text_color_light_secondary,
            constitutionListCount = constitutionListCount,
            constitutionList = constitutionList,
            constitutionListDiffColor= R.color.text_color_light_secondary,
            isEnableConfirmButton = false
        )

        //実施
        viewModel = MissionStatementSettingViewModel(missionStatement, missionStatementUseCase)
        MissionStatementDispatcher.setActions(MissionStatementDispatcherContract.Action.AddConstitution(value))

        // 比較
        result(expectationsState, expectationsEffect)
    }

    // endregion

    // region 確定ボタンロジック

    /**
     * 変更ボタンタップ
     *
     * 条件：更新
     * 期待結果；
     * ・画面の値
     * 　　ー
     * ・画面イベント
     * 　・画面遷移イベントが走ること
     * ・共通処理イベント
     * 　　ー
     * ・業務ロジック
     * 　・更新処理が走ること
     */
    @ExperimentalCoroutinesApi
    @Test
    fun onClickConfirmButtonByNotNullUpdate() = testScope.runBlockingTest {

        // 期待結果
        val dto = MissionStatementInputDto(funeralList, purposeLife, constitutionList)
        val expectationsEffect = MissionStatementSettingContract.Effect.NavigateMissionStatementSetting
        var funeralListCount = 1L
        var constitutionListCount = 1L
        val funeralList = missionStatement.funeralList
            .map { funeral -> Pair(funeralListCount++, funeral) }
            .toMutableList()
        val constitutionList = missionStatement.constitutionList
            .map { constitution -> Pair(constitutionListCount++, constitution) }
            .toMutableList()
        val expectationsState = state.copy(
            funeralListCount = funeralListCount,
            funeralList = funeralList,
            funeralListDiffColor = R.color.text_color_light_secondary,
            purposeLife = missionStatement.purposeLife,
            purposeLifeDiffColor = R.color.text_color_light_secondary,
            constitutionListCount = constitutionListCount,
            constitutionList = constitutionList,
            constitutionListDiffColor= R.color.text_color_light_secondary,
            isEnableConfirmButton = false
        )

        //実施
        viewModel = MissionStatementSettingViewModel(missionStatement, missionStatementUseCase)
        viewModel.setEvent(MissionStatementSettingContract.Event.OnClickChangeButton)

        // 比較
        result(expectationsState, expectationsEffect)
        coVerify(exactly = 1) { (missionStatementUseCase).updateMissionStatement(missionStatement, dto) }
    }

    /**
     * 変更ボタンタップ
     *
     * 条件：更新 & ビジネスロジックでExceptionエラー
     * 期待結果；
     * ・画面の値
     * 　　ー
     * ・画面イベント
     * 　・エラーイベントが走ること
     * ・共通処理イベント
     * 　　ー
     * ・業務ロジック
     * 　・更新処理が走ること
     */
    @ExperimentalCoroutinesApi
    @Test
    fun onClickConfirmButtonByNotNullUpdateException() = testScope.runBlockingTest {

        // 期待結果
        val error = IllegalArgumentException("test")
        val expectationsEffect = MissionStatementSettingContract.Effect.ShowError(error)
        val dto = MissionStatementInputDto(funeralList, purposeLife, constitutionList)
        var funeralListCount = 1L
        var constitutionListCount = 1L
        val funeralList = missionStatement.funeralList
            .map { funeral -> Pair(funeralListCount++, funeral) }
            .toMutableList()
        val constitutionList = missionStatement.constitutionList
            .map { constitution -> Pair(constitutionListCount++, constitution) }
            .toMutableList()
        val expectationsState = state.copy(
            funeralListCount = funeralListCount,
            funeralList = funeralList,
            funeralListDiffColor = R.color.text_color_light_secondary,
            purposeLife = missionStatement.purposeLife,
            purposeLifeDiffColor = R.color.text_color_light_secondary,
            constitutionListCount = constitutionListCount,
            constitutionList = constitutionList,
            constitutionListDiffColor= R.color.text_color_light_secondary,
            isEnableConfirmButton = false
        )

        //実施
        missionStatementUseCase = mockk<MissionStatementUseCase>().also {
            coEvery { it.getMissionStatement() } returns missionStatement
            coEvery { it.createMissionStatement(any()) } throws error
            coEvery { it.updateMissionStatement(any(), any()) } throws error
        }
        viewModel = MissionStatementSettingViewModel(missionStatement, missionStatementUseCase)
        viewModel.setEvent(MissionStatementSettingContract.Event.OnClickChangeButton)

        // 比較
        result(expectationsState, expectationsEffect)
        coVerify(exactly = 1) { (missionStatementUseCase).updateMissionStatement(missionStatement, dto) }
    }

    /**
     * 変更ボタンタップ
     *
     * 条件：新規登録
     * 期待結果；
     * ・画面の値
     * 　　ー
     * ・画面イベント
     * 　・画面遷移イベントが走ること
     * ・共通処理イベント
     * 　　ー
     * ・業務ロジック
     * 　・新規登録処理が走ること
     */
    @ExperimentalCoroutinesApi
    @Test
    fun onClickConfirmButtonByNotNullCreate() = testScope.runBlockingTest {

        // 期待結果
        val value = "test1"
        val emptyList = mutableListOf(Pair(0L,""))
        val expectationsEffect = MissionStatementSettingContract.Effect.NavigateMissionStatementSetting
        val dto = MissionStatementInputDto(mutableListOf(), value, mutableListOf())
        val expectationsState = state.copy(
            funeralListCount = state.funeralListCount + 1,
            funeralList = emptyList,
            funeralListDiffColor = R.color.text_color_light_secondary,
            purposeLife = value,
            purposeLifeDiffColor = R.color.text_color_light_primary,
            constitutionListCount = state.constitutionListCount + 1,
            constitutionList = emptyList,
            constitutionListDiffColor= R.color.text_color_light_secondary,
            isEnableConfirmButton = true
        )

        //実施
        viewModel = MissionStatementSettingViewModel(null, missionStatementUseCase)
        viewModel.setEvent(MissionStatementSettingContract.Event.OnChangePurposeText(value))
        viewModel.setEvent(MissionStatementSettingContract.Event.OnClickChangeButton)

        // 比較
        result(expectationsState, expectationsEffect)
        coVerify(exactly = 1) { (missionStatementUseCase).createMissionStatement(dto) }
    }

    /**
     * 変更ボタンタップ
     *
     * 条件：新規登録 & ビジネスロジックでExceptionエラー
     * 期待結果；
     * ・画面の値
     * 　　ー
     * ・画面イベント
     * 　・エラーイベントが走ること
     * ・共通処理イベント
     * 　　ー
     * ・業務ロジック
     * 　・新規登録処理が走ること
     */
    @ExperimentalCoroutinesApi
    @Test
    fun onClickConfirmButtonByNotNullCreateException() = testScope.runBlockingTest {

        // 期待結果
        val error = IllegalArgumentException("test")
        val value = "test01"
        val emptyList = mutableListOf(Pair(0L,""))
        val expectationsEffect = MissionStatementSettingContract.Effect.ShowError(error)
        val dto = MissionStatementInputDto(mutableListOf(), value, mutableListOf())
        val expectationsState = state.copy(
            funeralListCount = state.funeralListCount + 1,
            funeralList = emptyList,
            funeralListDiffColor = R.color.text_color_light_secondary,
            purposeLife = value,
            purposeLifeDiffColor = R.color.text_color_light_primary,
            constitutionListCount = state.constitutionListCount + 1,
            constitutionList = emptyList,
            constitutionListDiffColor= R.color.text_color_light_secondary,
            isEnableConfirmButton = true
        )

        //実施
        missionStatementUseCase = mockk<MissionStatementUseCase>().also {
            coEvery { it.getMissionStatement() } returns missionStatement
            coEvery { it.createMissionStatement(any()) } throws error
            coEvery { it.updateMissionStatement(any(), any()) } throws error
        }
        viewModel = MissionStatementSettingViewModel(null, missionStatementUseCase)
        viewModel.setEvent(MissionStatementSettingContract.Event.OnChangePurposeText(value))
        viewModel.setEvent(MissionStatementSettingContract.Event.OnClickChangeButton)

        // 比較
        result(expectationsState, expectationsEffect)
        coVerify(exactly = 1) { (missionStatementUseCase).createMissionStatement(dto) }
    }

    // endregion
}
