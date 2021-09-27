package com.myapp.presentation.ui.mission_statement

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.myapp.domain.dto.MissionStatementInputDto
import com.myapp.domain.model.entity.MissionStatement
import com.myapp.domain.usecase.MissionStatementUseCase
import com.myapp.presentation.utils.Status
import com.nhaarman.mockito_kotlin.mock
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
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

    private lateinit var viewModel: MissionStatementSettingViewModel
    private lateinit var missionStatementUseCase: MissionStatementUseCase

    private val funeralList = listOf("弔辞：感謝されること", "人：優しい人間", "雰囲気：和気藹々")
    private val purposeLife = "世界一楽しく生きること"
    private val constitutionList = listOf("迷ったときは楽しい方向へ", "常に楽しいを見つける努力を")
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

    private fun setMockObserver() {
        val observerString = mock<Observer<String>>()
        val observerIntStringList = mock<Observer<MutableList<Pair<Long, String>>>>()
        val observerStatus = mock<Observer<Status<*>>>()
        val observerBoolean = mock<Observer<Boolean>>()
        viewModel.funeralList.observeForever(observerIntStringList)
        viewModel.purposeLife.observeForever(observerString)
        viewModel.constitutionList.observeForever(observerIntStringList)
        viewModel.confirmStatus.observeForever(observerStatus)
        viewModel.isEnableConfirmButton.observeForever(observerBoolean)
    }

    @ExperimentalCoroutinesApi
    @After
    fun tearDown() {
        Dispatchers.resetMain()
        coroutineDispatcher.cleanupTestCoroutines()
    }

    // endregion

    // region 初期表示ロジック

    /**
     * 初期表示
     *
     * 条件：
     * ・アプリ立ち上げ後編集履歴なし（Dispatcherなし）
     * ・２回目以降登録（ミッションステートの取得値がnull)
     * 期待結果：UseCaseから取得した値が格納されていること
     */
    @Test
    fun initByNotDispatcher() {
        viewModel = MissionStatementSettingViewModel(missionStatement, missionStatementUseCase)
        setMockObserver()
        assertEquals(missionStatement.funeralList.toMutableList(), viewModel.funeralList.value!!.map { it.second })
        assertEquals(missionStatement.purposeLife, viewModel.purposeLife.value)
        assertEquals(missionStatement.constitutionList.toMutableList(), viewModel.constitutionList.value!!.map { it.second })
    }

    /**
     * 初期表示
     *
     * 条件：
     * ・アプリ立ち上げ後編集履歴なし（Dispatcherなし）
     * ・初回登録（ミッションステートの取得値がnull)
     * 期待結果：UseCaseから取得した値が格納されていること
     */
    @Test
    fun initByNotDispatcherAndNotMissionStatement() {
        viewModel = MissionStatementSettingViewModel(null, missionStatementUseCase)
        setMockObserver()
        assertEquals(mutableListOf(""), viewModel.funeralList.value!!.map { it.second })
        assertEquals("", viewModel.purposeLife.value)
        assertEquals(mutableListOf(""), viewModel.constitutionList.value!!.map { it.second })
    }

    /**
     * 初期表示
     *
     * 条件：
     * ・アプリ立ち上げ後編集履歴あり（Dispatcherあり）
     * ・初回登録（ミッションステートの取得値がnull)
     * 期待結果：UseCaseから取得した値が格納されていること
     */
    @ExperimentalCoroutinesApi
    @Test
    fun initByDispatcher() = testScope.runBlockingTest {
        val index1 = missionStatement.funeralList.size - 1
        val setText1 = "test After"
        MissionStatementDispatcher.changeFuneralText(index1, setText1)
        val index2 = missionStatement.constitutionList.size - 1
        val setText2 = "test After"
        MissionStatementDispatcher.changeFuneralText(index2, setText2)
        viewModel = MissionStatementSettingViewModel(null, missionStatementUseCase)
        setMockObserver()
        assertEquals(listOf(""), viewModel.funeralList.value!!.map { it.second })
        assertEquals("", viewModel.purposeLife.value)
        assertEquals(listOf(""), viewModel.constitutionList.value!!.map { it.second })
    }

    /**
     * 初期表示
     *
     * 条件：
     * ・アプリ立ち上げ後編集履歴あり（Dispatcherあり）
     * ・初回登録（ミッションステートの取得値がnull)
     * 期待結果：UseCaseから取得した値が格納されていること
     */
    @ExperimentalCoroutinesApi
    @Test
    fun initByDispatcherAndNotMissionStatement() = testScope.runBlockingTest {
        val index1 = missionStatement.funeralList.size - 1
        val setText1 = "test After"
        MissionStatementDispatcher.changeFuneralText(index1, setText1)
        val index2 = missionStatement.constitutionList.size - 1
        val setText2 = "test After"
        MissionStatementDispatcher.changeFuneralText(index2, setText2)
        viewModel = MissionStatementSettingViewModel(null, missionStatementUseCase)
        setMockObserver()
        assertEquals(mutableListOf(""), viewModel.funeralList.value!!.map { it.second })
        assertEquals("", viewModel.purposeLife.value)
        assertEquals(mutableListOf(""), viewModel.constitutionList.value!!.map { it.second })
    }

    // endregion

    // region 理想の葬式リストロジック

    /**
     * 理想の葬式リストの内容変更
     *
     * 条件：指定した理想の葬式アイテムの文字列を変更
     * 期待結果：理想の葬式リストの文字列が変更されていること
     */
    @ExperimentalCoroutinesApi
    @Test
    fun changeFuneralTextByFoundIndex() = testScope.runBlockingTest {
        viewModel = MissionStatementSettingViewModel(missionStatement, missionStatementUseCase)
        setMockObserver()
        val index = 1
        val setText = "test After"
        val act = missionStatement.funeralList
        MissionStatementDispatcher.changeFuneralText(index, setText)
        val result = viewModel.funeralList.value!!.map { it.second }
        assertNotEquals(act, result)
        assertEquals(setText, result[index])
    }

    /**
     * 理想の葬式リストの内容変更
     *
     * 条件：指定した理想の葬式アイテムのインデックスが存在しないインデックス
     * 期待結果：理想の葬式リストの文字列が変更されないこと
     */
    @ExperimentalCoroutinesApi
    @Test
    fun changeFuneralTextByNotFoundIndex() = testScope.runBlockingTest {
        viewModel = MissionStatementSettingViewModel(missionStatement, missionStatementUseCase)
        setMockObserver()
        val index = 4
        val setText = "test After"
        val act = missionStatement.funeralList
        MissionStatementDispatcher.changeFuneralText(index, setText)
        val result = viewModel.funeralList.value!!.map { it.second }
        assertEquals(act, result)
    }

    /**
     * 理想の葬式リストのリスト削除
     *
     * 条件：指定した理想の葬式アイテムのインデックスが存在する
     * 期待結果：理想の葬式リストの文字列が削除されていること
     */
    @ExperimentalCoroutinesApi
    @Test
    fun deleteFuneralItemByFoundIndex() = testScope.runBlockingTest {
        viewModel = MissionStatementSettingViewModel(missionStatement, missionStatementUseCase)
        setMockObserver()
        val index = missionStatement.funeralList.size - 1
        val act: MutableList<String> = missionStatement.funeralList.toMutableList()
        act.removeAt(index)
        MissionStatementDispatcher.deleteFuneral(index)
        val result = viewModel.funeralList.value!!.map { it.second }
        assertEquals(act, result)
    }

    /**
     * 理想の葬式リストのリスト削除
     *
     * 条件：指定した理想の葬式アイテムのインデックスが存在しない
     * 期待結果：理想の葬式リストの文字列が削除されていないこと
     */
    @ExperimentalCoroutinesApi
    @Test
    fun deleteFuneralItemByNotFoundIndex() = testScope.runBlockingTest {
        viewModel = MissionStatementSettingViewModel(missionStatement, missionStatementUseCase)
        setMockObserver()
        val index = missionStatement.funeralList.size
        val act = missionStatement.funeralList
        MissionStatementDispatcher.deleteFuneral(index)
        val result = viewModel.funeralList.value!!.map { it.second }
        assertEquals(act, result)
    }

    /**
     * 理想の葬式リストのリスト追加
     *
     * 条件：指定した理想の葬式アイテムのインデックスが存在する
     * 期待結果：理想の葬式リストの文字列が追加されていること
     */
    @ExperimentalCoroutinesApi
    @Test
    fun addFuneralItemByFoundIndex() = testScope.runBlockingTest {
        viewModel = MissionStatementSettingViewModel(missionStatement, missionStatementUseCase)
        setMockObserver()
        val index = missionStatement.funeralList.size
        val act: MutableList<String> = missionStatement.funeralList.toMutableList()
        act.add(index, "")
        MissionStatementDispatcher.addFuneral(index)
        val result = viewModel.funeralList.value!!.map { it.second }
        assertEquals(act, result)
    }

    /**
     * 理想の葬式リストのリスト追加
     *
     * 条件：指定した理想の葬式アイテムのインデックスが存在しない
     * 期待結果：理想の葬式リストの文字列が追加されていないこと
     *
     */
    @ExperimentalCoroutinesApi
    @Test
    fun addFuneralItemByNotFoundIndex() = testScope.runBlockingTest {
        viewModel = MissionStatementSettingViewModel(missionStatement, missionStatementUseCase)
        setMockObserver()
        val index = missionStatement.funeralList.size + 1
        val act = missionStatement.funeralList
        MissionStatementDispatcher.addFuneral(index)
        val result = viewModel.funeralList.value!!.map { it.second }
        assertEquals(act, result)
    }

    // endregion

    // region 憲法リストロジック

    /**
     * 憲法リストの内容変更
     *
     * 条件：指定した憲法アイテムの文字列を変更
     * 期待結果：憲法リストの文字列が変更されていること
     */
    @ExperimentalCoroutinesApi
    @Test
    fun changeConstitutionTextByFoundIndex() = testScope.runBlockingTest {
        viewModel = MissionStatementSettingViewModel(missionStatement, missionStatementUseCase)
        setMockObserver()
        val index = missionStatement.constitutionList.size - 1
        val setText = "test After"
        val act = missionStatement.constitutionList
        MissionStatementDispatcher.changeConstitutionText(index, setText)
        val result = viewModel.constitutionList.value!!.map { it.second }
        assertNotEquals(act, result)
        assertEquals(setText, result[index])
    }

    /**
     * 憲法リストの内容変更
     *
     * 条件：指定した憲法アイテムのインデックスが存在しないインデックス
     * 期待結果：憲法リストの文字列が変更されないこと
     */
    @ExperimentalCoroutinesApi
    @Test
    fun changeConstitutionTextByNotFoundIndex() = testScope.runBlockingTest {
        viewModel = MissionStatementSettingViewModel(missionStatement, missionStatementUseCase)
        setMockObserver()
        val index = missionStatement.constitutionList.size
        val setText = "test After"
        val act = missionStatement.constitutionList
        MissionStatementDispatcher.changeConstitutionText(index, setText)
        val result = viewModel.constitutionList.value!!.map { it.second }
        assertEquals(act, result)
    }

    /**
     * 憲法リストのリスト削除
     *
     * 条件：指定した憲法アイテムのインデックスが存在する
     * 期待結果：憲法リストの文字列が削除されていること
     */
    @ExperimentalCoroutinesApi
    @Test
    fun deleteConstitutionItemByFoundIndex() = testScope.runBlockingTest {
        viewModel = MissionStatementSettingViewModel(missionStatement, missionStatementUseCase)
        setMockObserver()
        val index = missionStatement.constitutionList.size - 1
        val act: MutableList<String> = missionStatement.constitutionList.toMutableList()
        act.removeAt(index)
        MissionStatementDispatcher.deleteConstitution(index)
        val result = viewModel.constitutionList.value!!.map { it.second }
        assertEquals(act, result)
    }

    /**
     * 憲法リストのリスト削除
     *
     * 条件：指定した憲法アイテムのインデックスが存在しない
     * 期待結果：憲法リストの文字列が削除されていないこと
     */
    @ExperimentalCoroutinesApi
    @Test
    fun deleteConstitutionItemByNotFoundIndex() = testScope.runBlockingTest {
        viewModel = MissionStatementSettingViewModel(missionStatement, missionStatementUseCase)
        setMockObserver()
        val index = missionStatement.constitutionList.size
        val act = missionStatement.constitutionList
        MissionStatementDispatcher.deleteConstitution(index)
        val result = viewModel.constitutionList.value!!.map { it.second }
        assertEquals(act, result)
    }

    /**
     * 憲法リストのリスト追加
     *
     * 条件：指定した憲法アイテムのインデックスが存在する
     * 期待結果：憲法リストの文字列が追加されていること
     */
    @ExperimentalCoroutinesApi
    @Test
    fun addConstitutionItemByFoundIndex() = testScope.runBlockingTest {
        viewModel = MissionStatementSettingViewModel(missionStatement, missionStatementUseCase)
        setMockObserver()
        val index = missionStatement.constitutionList.size - 2
        val act: MutableList<String> = missionStatement.constitutionList.toMutableList()
        act.add(index, "")
        MissionStatementDispatcher.addConstitution(index)
        val result = viewModel.constitutionList.value!!.map { it.second }
        assertEquals(act, result)
    }

    /**
     * 憲法リストのリスト追加
     *
     * 条件：指定した憲法アイテムのインデックスが存在しない
     * 期待結果：憲法リストの文字列が追加されていないこと
     */
    @ExperimentalCoroutinesApi
    @Test
    fun addConstitutionItemByNotFoundIndex() = testScope.runBlockingTest {
        viewModel = MissionStatementSettingViewModel(missionStatement, missionStatementUseCase)
        setMockObserver()
        val index = missionStatement.constitutionList.size + 1
        val act = missionStatement.constitutionList
        MissionStatementDispatcher.addConstitution(index)
        val result = viewModel.constitutionList.value!!.map { it.second }
        assertEquals(act, result)
    }

    // endregion

    // region 確定ボタンロジック

    /**
     * 更新ボタン
     *
     * 条件：
     * ・更新処理(missionStatementがnullではない)であること
     * ・理想の葬式・人生の目的・憲法、の全てに対してNull以外の値が入っていること
     * 期待結果：入力した内容で更新メソッドが呼ばれること
     */
    @ExperimentalCoroutinesApi
    @Test
    fun onClickConfirmButtonByNotNullUpdate() = testScope.runBlockingTest {
        viewModel = MissionStatementSettingViewModel(missionStatement, missionStatementUseCase)
        setMockObserver()
        viewModel.onClickConfirmButton()
        val funeralList = viewModel.funeralList.value!!.toList()
            .map { it.second }
        val purposeLife = viewModel.purposeLife.value!!
        val constitutionList = viewModel.constitutionList.value!!.toList()
            .map { it.second }
        val dto = MissionStatementInputDto(funeralList, purposeLife, constitutionList)
        coVerify(exactly = 1) { (missionStatementUseCase).updateMissionStatement(missionStatement, dto) }
        assertEquals(true, viewModel.confirmStatus.value is Status.Success)
    }

    /**
     * 登録ボタン
     *
     * 条件：
     * ・登録処理(missionStatementがnull)であること
     * ・理想の葬式・人生の目的・憲法、の全てに対してNull以外の値が入っていること
     * 期待結果：入力した内容で登録メソッドが呼ばれること
     */
    @ExperimentalCoroutinesApi
    @Test
    fun onClickConfirmButtonByNotNullCreate() = testScope.runBlockingTest {
        viewModel = MissionStatementSettingViewModel(null, missionStatementUseCase)
        setMockObserver()
        viewModel.onClickConfirmButton()
        val funeralList = viewModel.funeralList.value!!.toList()
            .map { it.second }
            .filter { it.isNotBlank() }
        val purposeLife = viewModel.purposeLife.value!!
        val constitutionList = viewModel.constitutionList.value!!.toList()
            .map { it.second }
            .filter { it.isNotBlank() }
        val dto = MissionStatementInputDto(funeralList, purposeLife, constitutionList)
        coVerify(exactly = 1) { (missionStatementUseCase).createMissionStatement(dto) }
        assertEquals(true, viewModel.confirmStatus.value is Status.Success)
    }

    /**
     * 変更確定ボタン
     *
     * 条件：人生の目的がNull
     * 期待結果：
     * ・ミッションステートメント更新メソッドが呼ばれないこと
     * ・ミッションステートメント更新ステータスがエラーになること
     */
    @ExperimentalCoroutinesApi
    @Test
    fun onClickConfirmButtonByNullPurposeLife() = testScope.runBlockingTest {
        viewModel = MissionStatementSettingViewModel(missionStatement, missionStatementUseCase)
        setMockObserver()
        viewModel.purposeLife.value = null
        viewModel.onClickConfirmButton()
        coVerify(exactly = 0) { (missionStatementUseCase).updateMissionStatement(any(), any()) }
        assertEquals(true, viewModel.confirmStatus.value is Status.Failure)
    }

    // region ボタン活性非活性制御

    /**
     * 変更確定ボタン
     *
     * 条件：全ての入力項目が空
     * 期待結果：
     * ・ミッションステートメント更新ボタンが非活性(false)になること
     */
    @ExperimentalCoroutinesApi
    @Test
    fun isEnableConfirmButtonByAllEmpty() = testScope.runBlockingTest {
        viewModel = MissionStatementSettingViewModel(null, missionStatementUseCase)
        setMockObserver()
        assertEquals(false, viewModel.isEnableConfirmButton.value)
    }

    /**
     * 変更確定ボタン
     *
     * 条件：理想の葬儀のみ入力済み(半角スペースのみ)
     * 期待結果：
     * ・ミッションステートメント更新ボタンが活性(false)になること
     */
    @ExperimentalCoroutinesApi
    @Test
    fun isEnableConfirmButtonByFuneralHanSpace() = testScope.runBlockingTest {
        viewModel = MissionStatementSettingViewModel(null, missionStatementUseCase)
        setMockObserver()
        val index = 0
        val setText = " "
        MissionStatementDispatcher.changeFuneralText(index, setText)
        assertEquals(false, viewModel.isEnableConfirmButton.value)
    }

    /**
     * 変更確定ボタン
     *
     * 条件：理想の葬儀のみ入力済み(全角スペースのみ)
     * 期待結果：
     * ・ミッションステートメント更新ボタンが活性(false)になること
     */
    @ExperimentalCoroutinesApi
    @Test
    fun isEnableConfirmButtonByFuneralZenSpace() = testScope.runBlockingTest {
        viewModel = MissionStatementSettingViewModel(null, missionStatementUseCase)
        setMockObserver()
        val index = 0
        val setText = "　"
        MissionStatementDispatcher.changeFuneralText(index, setText)
        assertEquals(false, viewModel.isEnableConfirmButton.value)
    }

    /**
     * 変更確定ボタン
     *
     * 条件：理想の葬儀のみ入力済み(改行のみ)
     * 期待結果：
     * ・ミッションステートメント更新ボタンが活性(false)になること
     */
    @ExperimentalCoroutinesApi
    @Test
    fun isEnableConfirmButtonByFuneralEnter() = testScope.runBlockingTest {
        viewModel = MissionStatementSettingViewModel(null, missionStatementUseCase)
        setMockObserver()
        val index = 0
        val setText = "\n"
        MissionStatementDispatcher.changeFuneralText(index, setText)
        assertEquals(false, viewModel.isEnableConfirmButton.value)
    }

    /**
     * 変更確定ボタン
     *
     * 条件：理想の葬儀のみ入力済み
     * 期待結果：
     * ・ミッションステートメント更新ボタンが活性(true)になること
     */
    @ExperimentalCoroutinesApi
    @Test
    fun isEnableConfirmButtonByFuneral() = testScope.runBlockingTest {
        viewModel = MissionStatementSettingViewModel(null, missionStatementUseCase)
        setMockObserver()
        val index = 0
        val setText = "test After"
        MissionStatementDispatcher.changeFuneralText(index, setText)
        assertEquals(true, viewModel.isEnableConfirmButton.value)
    }

    /**
     * 変更確定ボタン
     *
     * 条件：人生の目的のみ入力済み(半角スペース)
     * 期待結果：
     * ・ミッションステートメント更新ボタンが活性(false)になること
     */
    @ExperimentalCoroutinesApi
    @Test
    fun isEnableConfirmButtonByPurposeLifeHanSpace() = testScope.runBlockingTest {
        viewModel = MissionStatementSettingViewModel(null, missionStatementUseCase)
        setMockObserver()
        viewModel.purposeLife.value = " "
        assertEquals(false, viewModel.isEnableConfirmButton.value)
    }

    /**
     * 変更確定ボタン
     *
     * 条件：人生の目的のみ入力済み(全角スペース)
     * 期待結果：
     * ・ミッションステートメント更新ボタンが活性(false)になること
     */
    @ExperimentalCoroutinesApi
    @Test
    fun isEnableConfirmButtonByPurposeLifeZenSpace() = testScope.runBlockingTest {
        viewModel = MissionStatementSettingViewModel(null, missionStatementUseCase)
        setMockObserver()
        viewModel.purposeLife.value = "　"
        assertEquals(false, viewModel.isEnableConfirmButton.value)
    }

    /**
     * 変更確定ボタン
     *
     * 条件：人生の目的のみ入力済み(改行のみ)
     * 期待結果：
     * ・ミッションステートメント更新ボタンが活性(false)になること
     */
    @ExperimentalCoroutinesApi
    @Test
    fun isEnableConfirmButtonByPurposeLifeEnter() = testScope.runBlockingTest {
        viewModel = MissionStatementSettingViewModel(null, missionStatementUseCase)
        setMockObserver()
        viewModel.purposeLife.value = "\n"
        assertEquals(false, viewModel.isEnableConfirmButton.value)
    }

    /**
     * 変更確定ボタン
     *
     * 条件：人生の目的のみ入力済み
     * 期待結果：
     * ・ミッションステートメント更新ボタンが活性(true)になること
     */
    @ExperimentalCoroutinesApi
    @Test
    fun isEnableConfirmButtonByPurposeLife() = testScope.runBlockingTest {
        viewModel = MissionStatementSettingViewModel(null, missionStatementUseCase)
        setMockObserver()
        viewModel.purposeLife.value = "test"
        assertEquals(true, viewModel.isEnableConfirmButton.value)
    }

    /**
     * 変更確定ボタン
     *
     * 条件：憲法のみ入力済み(半角スペースのみ)
     * 期待結果：
     * ・ミッションステートメント更新ボタンが活性(false)になること
     */
    @ExperimentalCoroutinesApi
    @Test
    fun isEnableConfirmButtonByConstitutionHanSpace() = testScope.runBlockingTest {
        viewModel = MissionStatementSettingViewModel(null, missionStatementUseCase)
        setMockObserver()
        val index = 0
        val setText = " "
        MissionStatementDispatcher.changeConstitutionText(index, setText)
        assertEquals(false, viewModel.isEnableConfirmButton.value)
    }

    /**
     * 変更確定ボタン
     *
     * 条件：憲法のみ入力済み(全角スペースのみ)
     * 期待結果：
     * ・ミッションステートメント更新ボタンが活性(false)になること
     */
    @ExperimentalCoroutinesApi
    @Test
    fun isEnableConfirmButtonByConstitutionZenSpace() = testScope.runBlockingTest {
        viewModel = MissionStatementSettingViewModel(null, missionStatementUseCase)
        setMockObserver()
        val index = 0
        val setText = "　"
        MissionStatementDispatcher.changeConstitutionText(index, setText)
        assertEquals(false, viewModel.isEnableConfirmButton.value)
    }

    /**
     * 変更確定ボタン
     *
     * 条件：憲法のみ入力済み(改行のみ)
     * 期待結果：
     * ・ミッションステートメント更新ボタンが活性(false)になること
     */
    @ExperimentalCoroutinesApi
    @Test
    fun isEnableConfirmButtonByConstitutionEnter() = testScope.runBlockingTest {
        viewModel = MissionStatementSettingViewModel(null, missionStatementUseCase)
        setMockObserver()
        val index = 0
        val setText = "\n"
        MissionStatementDispatcher.changeConstitutionText(index, setText)
        assertEquals(false, viewModel.isEnableConfirmButton.value)
    }

    /**
     * 変更確定ボタン
     *
     * 条件：憲法のみ入力済み
     * 期待結果：
     * ・ミッションステートメント更新ボタンが活性(true)になること
     */
    @ExperimentalCoroutinesApi
    @Test
    fun isEnableConfirmButtonByConstitution() = testScope.runBlockingTest {
        viewModel = MissionStatementSettingViewModel(null, missionStatementUseCase)
        setMockObserver()
        val index = 0
        val setText = "test After"
        MissionStatementDispatcher.changeConstitutionText(index, setText)
        assertEquals(true, viewModel.isEnableConfirmButton.value)
    }

    // endregion

    // endregion
}
