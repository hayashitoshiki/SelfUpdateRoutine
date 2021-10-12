package com.myapp.presentation.ui.mission_statement

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.myapp.domain.model.entity.MissionStatement
import com.myapp.domain.usecase.MissionStatementUseCase
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
    private val state = MissionStatementListContract.State()

    // region test date

    private val funeralList = listOf("弔辞：感謝されること", "人：優しい人間")
    private val purposeLife = "世界一楽しく生きること"
    private val constitutionList = listOf("迷ったときは楽しい方向へ", "常に楽しいを見つける努力を")
    private val missionStatement = MissionStatement(funeralList, purposeLife, constitutionList)
    private val missionStatementOnlyFuneralList = MissionStatement(funeralList, "", listOf())
    private val missionStatementOnlyPurposeLife = MissionStatement(listOf(), purposeLife, listOf())
    private val missionStatementOnlyConstitutionList = MissionStatement(listOf(), "", constitutionList)

    // endregion

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        Dispatchers.setMain(coroutineDispatcher)
        missionStatementUseCase = mockk<MissionStatementUseCase>().also {
            coEvery { it.getMissionStatement() } returns missionStatement
        }
    }

    @ExperimentalCoroutinesApi
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    /**
     * 実行結果比較
     *
     * @param state Stateの期待値
     * @param effect Effectの期待値
     */
    @ExperimentalCoroutinesApi
    private fun result(
        state: MissionStatementListContract.State,
        effect: MissionStatementListContract.Effect?
    ) = testScope.runBlockingTest {
        val resultState = viewModel.state.value
        val resultEffect = viewModel.effect.value

        // 比較
        assertEquals(resultState, state)
        assertEquals(resultEffect, effect)
    }

    // region 表示制御

    /**
     * 初期表示
     *
     * 条件：ミッションステートメントの全項目が登録されている
     * 期待結果；
     * ・画面の値
     * 　・理想の葬式リスト：取得した理想の葬儀リスト
     * 　・理想の葬式カード：活性状態
     * 　・人生の目的　　　：取得した人生の目的
     * 　・人生の目的カード：活性状態
     * 　・憲法リスト　　　：取得した憲法リスト
     * 　・憲法カード　　　：活性状態
     * ・画面イベント
     * 　　ー
     * ・共通処理イベント
     * 　　ー
     * ・業務ロジック
     * 　ー
     */
    @ExperimentalCoroutinesApi
    @Test
    fun initByMissionStatement() = testScope.runBlockingTest {

        // 期待結果
        val expectationsEffect = null
        val expectationsState = state.copy(
            missionStatement = missionStatement,
            funeralList = missionStatement.funeralList,
            isEnableFuneralList = true,
            purposeLife = missionStatement.purposeLife,
            isEnablePurposeLife = true,
            constitutionList = missionStatement.constitutionList,
            isEnableConstitutionList = true
        )

        //実施
        viewModel = MissionStatementListViewModel(missionStatementUseCase)

        // 比較
        result(expectationsState, expectationsEffect)
    }

    /**
     * 初期表示
     *
     * 条件：ミッションステートメントがまだ登録されていない
     * 期待結果；
     * ・画面の値
     * 　・理想の葬式リスト：空
     * 　・理想の葬式カード：非活性状態
     * 　・人生の目的　　　：空
     * 　・人生の目的カード：非活性状態
     * 　・憲法リスト　　　：空
     * 　・憲法カード　　　：非活性状態
     * ・画面イベント
     * 　　ー
     * ・共通処理イベント
     * 　　ー
     * ・業務ロジック
     * 　ー
     */
    @ExperimentalCoroutinesApi
    @Test
    fun initByNotMissionStatement() = testScope.runBlockingTest {

        // 期待結果
        val expectationsEffect = null
        val expectationsState = state.copy(
            missionStatement = null,
            funeralList = listOf(),
            isEnableFuneralList = false,
            purposeLife = "",
            isEnablePurposeLife = false,
            constitutionList = listOf(),
            isEnableConstitutionList = false
        )

        //実施
        missionStatementUseCase = mockk<MissionStatementUseCase>().also {
            coEvery { it.getMissionStatement() } returns null
        }
        viewModel = MissionStatementListViewModel(missionStatementUseCase)

        // 比較
        result(expectationsState, expectationsEffect)
    }

    /**
     * 初期表示
     *
     * 条件：理想の葬式データのみ登録済み
     * 期待結果；
     * ・画面の値
     * 　・理想の葬式リスト：取得した理想の葬式リスト
     * 　・理想の葬式カード：活性状態
     * 　・人生の目的　　　：空
     * 　・人生の目的カード：非活性状態
     * 　・憲法リスト　　　：空
     * 　・憲法カード　　　：非活性状態
     * ・画面イベント
     * 　　ー
     * ・共通処理イベント
     * 　　ー
     * ・業務ロジック
     * 　ー
     */
    @ExperimentalCoroutinesApi
    @Test
    fun idEnableFuneralListOnlyTrue() = testScope.runBlockingTest {

        // 期待結果
        val expectationsEffect = null
        val expectationsState = state.copy(
            missionStatement = missionStatementOnlyFuneralList,
            funeralList = missionStatementOnlyFuneralList.funeralList,
            isEnableFuneralList = true,
            purposeLife = "",
            isEnablePurposeLife = false,
            constitutionList = listOf(),
            isEnableConstitutionList = false
        )

        //実施
        missionStatementUseCase = mockk<MissionStatementUseCase>().also {
            coEvery { it.getMissionStatement() } returns missionStatementOnlyFuneralList
        }
        viewModel = MissionStatementListViewModel(missionStatementUseCase)

        // 比較
        result(expectationsState, expectationsEffect)
    }

    /**
     * 初期表示
     *
     * 条件：人生の目的データのみ登録済み
     * 期待結果；
     * ・画面の値
     * 　・理想の葬式リスト：空
     * 　・理想の葬式カード：非活性状態
     * 　・人生の目的　　　：取得した人生の目的
     * 　・人生の目的カード：活性状態
     * 　・憲法リスト　　　：空
     * 　・憲法カード　　　：非活性状態
     * ・画面イベント
     * 　　ー
     * ・共通処理イベント
     * 　　ー
     * ・業務ロジック
     * 　ー
     */
    @ExperimentalCoroutinesApi
    @Test
    fun idEnablePurposeLifeOnlyTrue() = testScope.runBlockingTest {

        // 期待結果
        val expectationsEffect = null
        val expectationsState = state.copy(
            missionStatement = missionStatementOnlyPurposeLife,
            funeralList = listOf(),
            isEnableFuneralList = false,
            purposeLife = missionStatementOnlyPurposeLife.purposeLife,
            isEnablePurposeLife = true,
            constitutionList = listOf(),
            isEnableConstitutionList = false
        )

        //実施
        missionStatementUseCase = mockk<MissionStatementUseCase>().also {
            coEvery { it.getMissionStatement() } returns missionStatementOnlyPurposeLife
        }
        viewModel = MissionStatementListViewModel(missionStatementUseCase)

        // 比較
        result(expectationsState, expectationsEffect)
    }

    /**
     * 初期表示
     *
     * 条件：憲法データのみ登録済み
     * 期待結果；
     * ・画面の値
     * 　・理想の葬式リスト：空
     * 　・理想の葬式カード：非活性状態
     * 　・人生の目的　　　：空
     * 　・人生の目的カード：非活性状態
     * 　・憲法リスト　　　：取得した憲法リスト
     * 　・憲法カード　　　：活性状態
     * ・画面イベント
     * 　　ー
     * ・共通処理イベント
     * 　　ー
     * ・業務ロジック
     * 　ー
     */
    @ExperimentalCoroutinesApi
    @Test
    fun idEnableConstitutionListOnlyTrue() = testScope.runBlockingTest {

        // 期待結果
        val expectationsEffect = null
        val expectationsState = state.copy(
            missionStatement = missionStatementOnlyConstitutionList,
            funeralList = listOf(),
            isEnableFuneralList = false,
            purposeLife = "",
            isEnablePurposeLife = false,
            constitutionList = missionStatementOnlyConstitutionList.constitutionList,
            isEnableConstitutionList = true
        )

        //実施
        missionStatementUseCase = mockk<MissionStatementUseCase>().also {
            coEvery { it.getMissionStatement() } returns missionStatementOnlyConstitutionList
        }
        viewModel = MissionStatementListViewModel(missionStatementUseCase)

        // 比較
        result(expectationsState, expectationsEffect)
    }

    // endregion

    // region 画面共有アクション

    /**
     * 画面共有アクション
     *
     * 条件：更新アクション発火
     * 期待結果；
     * ・画面の値
     * 　・理想の葬式リスト：更新後の理想の葬式リスト
     * 　・理想の葬式カード：活性状態
     * 　・人生の目的　　　：更新後の人生の目的
     * 　・人生の目的カード：活性状態
     * 　・憲法リスト　　　：更新後の憲法リスト
     * 　・憲法カード　　　：活性状態
     * ・画面イベント
     * 　　ー
     * ・共通処理イベント
     * 　　ー
     * ・業務ロジック
     * 　ミッションステートメント取得処理が走ること（初期表示と合計２回走ること）
     */
    @ExperimentalCoroutinesApi
    @Test
    fun actionByUpdate() = testScope.runBlockingTest {

        // 期待結果
        val expectationsEffect = null
        val expectationsState = state.copy(
            missionStatement = missionStatement,
            funeralList = missionStatement.funeralList,
            isEnableFuneralList = true,
            purposeLife = missionStatement.purposeLife,
            isEnablePurposeLife = true,
            constitutionList = missionStatement.constitutionList,
            isEnableConstitutionList = true
        )

        //実施
        viewModel = MissionStatementListViewModel(missionStatementUseCase)
        MissionStatementDispatcher.setActions(MissionStatementDispatcherContract.Action.Update)

        // 比較
        result(expectationsState, expectationsEffect)
        coVerify(exactly = 2) { (missionStatementUseCase).getMissionStatement() }
    }

    /**
     * 画面共有アクション
     *
     * 条件：理想の葬式テキスト変更アクション発火
     * 期待結果；
     * ・画面の値
     * 　ー
     * ・画面イベント
     * 　　ー
     * ・共通処理イベント
     * 　　ー
     * ・業務ロジック
     * 　ミッションステートメント取得処理が走らないこと（初期表示の１回のみ走ること）
     */
    @ExperimentalCoroutinesApi
    @Test
    fun actionByChangeFuneralText() = testScope.runBlockingTest {

        // 期待結果
        val expectationsEffect = null
        val expectationsState = state.copy(
            missionStatement = missionStatement,
            funeralList = missionStatement.funeralList,
            isEnableFuneralList = true,
            purposeLife = missionStatement.purposeLife,
            isEnablePurposeLife = true,
            constitutionList = missionStatement.constitutionList,
            isEnableConstitutionList = true
        )

        //実施
        viewModel = MissionStatementListViewModel(missionStatementUseCase)
        MissionStatementDispatcher.setActions(MissionStatementDispatcherContract.Action.ChangeFuneralText(1,"test"))

        // 比較
        result(expectationsState, expectationsEffect)
        coVerify(exactly = 1) { (missionStatementUseCase).getMissionStatement() }
    }

    /**
     * 画面共有アクション
     *
     * 条件：理想の葬式リスト追加発火
     * 期待結果；
     * ・画面の値
     * 　ー
     * ・画面イベント
     * 　　ー
     * ・共通処理イベント
     * 　　ー
     * ・業務ロジック
     * 　ミッションステートメント取得処理が走らないこと（初期表示の１回のみ走ること）
     */
    @ExperimentalCoroutinesApi
    @Test
    fun actionByAddFuneral() = testScope.runBlockingTest {

        // 期待結果
        val expectationsEffect = null
        val expectationsState = state.copy(
            missionStatement = missionStatement,
            funeralList = missionStatement.funeralList,
            isEnableFuneralList = true,
            purposeLife = missionStatement.purposeLife,
            isEnablePurposeLife = true,
            constitutionList = missionStatement.constitutionList,
            isEnableConstitutionList = true
        )

        //実施
        viewModel = MissionStatementListViewModel(missionStatementUseCase)
        MissionStatementDispatcher.setActions(MissionStatementDispatcherContract.Action.AddFuneral(2))

        // 比較
        result(expectationsState, expectationsEffect)
        coVerify(exactly = 1) { (missionStatementUseCase).getMissionStatement() }
    }

    /**
     * 画面共有アクション
     *
     * 条件：理想の葬式リスト削除発火
     * 期待結果；
     * ・画面の値
     * 　ー
     * ・画面イベント
     * 　　ー
     * ・共通処理イベント
     * 　　ー
     * ・業務ロジック
     * 　ミッションステートメント取得処理が走らないこと（初期表示の１回のみ走ること）
     */
    @ExperimentalCoroutinesApi
    @Test
    fun actionByDeleteFuneral() = testScope.runBlockingTest {

        // 期待結果
        val expectationsEffect = null
        val expectationsState = state.copy(
            missionStatement = missionStatement,
            funeralList = missionStatement.funeralList,
            isEnableFuneralList = true,
            purposeLife = missionStatement.purposeLife,
            isEnablePurposeLife = true,
            constitutionList = missionStatement.constitutionList,
            isEnableConstitutionList = true
        )

        //実施
        viewModel = MissionStatementListViewModel(missionStatementUseCase)
        MissionStatementDispatcher.setActions(MissionStatementDispatcherContract.Action.DeleteFuneral(2))

        // 比較
        result(expectationsState, expectationsEffect)
        coVerify(exactly = 1) { (missionStatementUseCase).getMissionStatement() }
    }

    /**
     * 画面共有アクション
     *
     * 条件：憲法テキスト変更アクション発火
     * 期待結果；
     * ・画面の値
     * 　ー
     * ・画面イベント
     * 　　ー
     * ・共通処理イベント
     * 　　ー
     * ・業務ロジック
     * 　ミッションステートメント取得処理が走らないこと（初期表示の１回のみ走ること）
     */
    @ExperimentalCoroutinesApi
    @Test
    fun actionByChangeConstitutionText() = testScope.runBlockingTest {

        // 期待結果
        val expectationsEffect = null
        val expectationsState = state.copy(
            missionStatement = missionStatement,
            funeralList = missionStatement.funeralList,
            isEnableFuneralList = true,
            purposeLife = missionStatement.purposeLife,
            isEnablePurposeLife = true,
            constitutionList = missionStatement.constitutionList,
            isEnableConstitutionList = true
        )

        //実施
        viewModel = MissionStatementListViewModel(missionStatementUseCase)
        MissionStatementDispatcher.setActions(MissionStatementDispatcherContract.Action.ChangeConstitutionText(1,"test"))

        // 比較
        result(expectationsState, expectationsEffect)
        coVerify(exactly = 1) { (missionStatementUseCase).getMissionStatement() }
    }

    /**
     * 画面共有アクション
     *
     * 条件：憲法リスト追加アクション発火
     * 期待結果；
     * ・画面の値
     * 　ー
     * ・画面イベント
     * 　　ー
     * ・共通処理イベント
     * 　　ー
     * ・業務ロジック
     * 　ミッションステートメント取得処理が走らないこと（初期表示の１回のみ走ること）
     */
    @ExperimentalCoroutinesApi
    @Test
    fun actionByAddConstitution() = testScope.runBlockingTest {

        // 期待結果
        val expectationsEffect = null
        val expectationsState = state.copy(
            missionStatement = missionStatement,
            funeralList = missionStatement.funeralList,
            isEnableFuneralList = true,
            purposeLife = missionStatement.purposeLife,
            isEnablePurposeLife = true,
            constitutionList = missionStatement.constitutionList,
            isEnableConstitutionList = true
        )

        //実施
        viewModel = MissionStatementListViewModel(missionStatementUseCase)
        MissionStatementDispatcher.setActions(MissionStatementDispatcherContract.Action.AddConstitution(2))

        // 比較
        result(expectationsState, expectationsEffect)
        coVerify(exactly = 1) { (missionStatementUseCase).getMissionStatement() }
    }

    /**
     * 画面共有アクション
     *
     * 条件：理想の葬式リスト削除発火
     * 期待結果；
     * ・画面の値
     * 　ー
     * ・画面イベント
     * 　　ー
     * ・共通処理イベント
     * 　　ー
     * ・業務ロジック
     * 　ミッションステートメント取得処理が走らないこと（初期表示の１回のみ走ること）
     */
    @ExperimentalCoroutinesApi
    @Test
    fun actionByDeleteConstitution() = testScope.runBlockingTest {

        // 期待結果
        val expectationsEffect = null
        val expectationsState = state.copy(
            missionStatement = missionStatement,
            funeralList = missionStatement.funeralList,
            isEnableFuneralList = true,
            purposeLife = missionStatement.purposeLife,
            isEnablePurposeLife = true,
            constitutionList = missionStatement.constitutionList,
            isEnableConstitutionList = true
        )

        //実施
        viewModel = MissionStatementListViewModel(missionStatementUseCase)
        MissionStatementDispatcher.setActions(MissionStatementDispatcherContract.Action.DeleteConstitution(2))

        // 比較
        result(expectationsState, expectationsEffect)
        coVerify(exactly = 1) { (missionStatementUseCase).getMissionStatement() }
    }

    // endregion
}
