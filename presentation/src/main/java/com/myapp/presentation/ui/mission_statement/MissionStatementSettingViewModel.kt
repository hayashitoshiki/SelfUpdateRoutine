package com.myapp.presentation.ui.mission_statement

import androidx.lifecycle.*
import com.myapp.domain.dto.MissionStatementInputDto
import com.myapp.domain.model.entity.MissionStatement
import com.myapp.domain.usecase.MissionStatementUseCase
import com.myapp.presentation.R
import com.myapp.presentation.utils.base.Status
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

/**
 * ミッションステートメント設定画面_画面ロジック
 *
 */
class MissionStatementSettingViewModel @AssistedInject constructor(
    @Assisted private val missionStatement: MissionStatement?,
    private val missionStatementUseCase: MissionStatementUseCase
) : ViewModel() {

    // 更新ボタンステータス
    private val _confirmStatus = MutableLiveData<Status<*>>()
    val confirmStatus: LiveData<Status<*>> = _confirmStatus

    // 確定ボタン
    private val _isEnableConfirmButton = MediatorLiveData<Boolean>()
    val isEnableConfirmButton: LiveData<Boolean> = _isEnableConfirmButton

    // 理想の葬式
    private var funeralListCount: Long = 0
    private val _funeralList = MutableLiveData<MutableList<Pair<Long, String>>>()
    val funeralList: LiveData<MutableList<Pair<Long, String>>> = _funeralList

    // 変更前と変更後の人生の目的の変化の文字色
    private val _funeralListDiffColor = MediatorLiveData<Int>()
    val funeralListDiffColor: LiveData<Int> = _funeralListDiffColor

    // 人生の目的
    val purposeLife = MutableLiveData<String>()

    // 変更前と変更後の人生の目的の変化の文字色
    private val _purposeLifeDiffColor = MediatorLiveData<Int>()
    val purposeLifeDiffColor: LiveData<Int> = _purposeLifeDiffColor

    // 憲法
    private var constitutionListCount: Long = 0
    private val _constitutionList = MutableLiveData<MutableList<Pair<Long, String>>>()
    val constitutionList: LiveData<MutableList<Pair<Long, String>>> = _constitutionList

    // 変更前と変更後の憲法の変化の文字色
    private val _constitutionListDiffColor = MediatorLiveData<Int>()
    val constitutionListDiffColor: LiveData<Int> = _constitutionListDiffColor

    init {
        MissionStatementDispatcher.funeralText.onEach {
            _funeralList.value?.also { funeralList ->
                if (funeralList.size <= it.first) return@onEach
                val value = funeralList[it.first]
                funeralList[it.first] = Pair(value.first, it.second)
                _funeralList.value = funeralList
                changeButton()
            }
        }
            .launchIn(viewModelScope)

        MissionStatementDispatcher.funeralMinusButton.onEach {
            val list = _funeralList.value ?: return@onEach
            if (list.size <= it) return@onEach
            list.removeAt(it)
            _funeralList.value = list
        }
            .launchIn(viewModelScope)

        MissionStatementDispatcher.funeralPlusButton.onEach {
            val list = _funeralList.value ?: return@onEach
            if (list.size < it) return@onEach
            list.add(it, Pair(funeralListCount, ""))
            funeralListCount++
            _funeralList.value = list
        }
            .launchIn(viewModelScope)

        MissionStatementDispatcher.constitutionText.onEach {
            _constitutionList.value?.also { constitutionList ->
                if (constitutionList.size <= it.first) return@onEach
                val value = constitutionList[it.first]
                constitutionList[it.first] = Pair(value.first, it.second)
                _constitutionList.value = constitutionList
                changeButton()
            }
        }
            .launchIn(viewModelScope)

        MissionStatementDispatcher.constitutionMinusButton.onEach {
            val list = _constitutionList.value ?: return@onEach
            if (list.size <= it) return@onEach
            list.removeAt(it)
            _constitutionList.value = list
        }
            .launchIn(viewModelScope)

        MissionStatementDispatcher.constitutionPlusButton.onEach {
            val list = _constitutionList.value ?: return@onEach
            if (list.size < it) return@onEach
            list.add(it, Pair(constitutionListCount, ""))
            constitutionListCount++
            _constitutionList.value = list
        }
            .launchIn(viewModelScope)

        missionStatement?.also {
            _funeralList.value = if (it.funeralList.isEmpty()) {
                val value = mutableListOf(Pair(0L, ""))
                funeralListCount++
                value
            } else {
                it.funeralList.map { funeral ->
                    val value = Pair(funeralListCount, funeral)
                    funeralListCount++
                    return@map value
                }
                    .toMutableList()
            }
            purposeLife.value = it.purposeLife
            _constitutionList.value = if (it.constitutionList.isEmpty()) {
                val value = mutableListOf(Pair(0L, ""))
                funeralListCount++
                value
            } else {
                it.constitutionList.map { constitution ->
                    val value = Pair(constitutionListCount, constitution)
                    constitutionListCount++
                    return@map value
                }
                    .toMutableList()
            }
        } ?: run {
            _funeralList.value = mutableListOf(Pair(0, ""))
            purposeLife.value = ""
            _constitutionList.value = mutableListOf(Pair(0, ""))
            funeralListCount++
            constitutionListCount++
        }

        _isEnableConfirmButton.addSource(_funeralList) { changeButton() }
        _isEnableConfirmButton.addSource(purposeLife) { changeButton() }
        _isEnableConfirmButton.addSource(_constitutionList) { changeButton() }
        _purposeLifeDiffColor.addSource(purposeLife) { onChangePurposeLifeTextColor(it) }
        _funeralListDiffColor.addSource(_funeralList) { onChangeFuneralListTextColor(it) }
        _constitutionListDiffColor.addSource(_constitutionList) { onChangeConstitutionListTextColor(it) }
    }

    // 人生の目的文字色変更
    private fun onChangePurposeLifeTextColor(data: String) {
        _purposeLifeDiffColor.value =
            if ((missionStatement == null && data.isNotBlank()) || (missionStatement != null && missionStatement.purposeLife != data)) {
                R.color.text_color_light_primary
            } else {
                R.color.text_color_light_secondary
            }
    }

    // 理想の葬式文字色変更
    private fun onChangeFuneralListTextColor(data: MutableList<Pair<Long, String>>) {
        val funeralList = data.map { it.second }
            .filter { it.isNotBlank() }
        _funeralListDiffColor.value =
            if (((missionStatement == null || missionStatement.funeralList.isEmpty()) && funeralList.isEmpty()) || (missionStatement != null && missionStatement.funeralList == funeralList)) {
                R.color.text_color_light_secondary
            } else {
                R.color.text_color_light_primary
            }
    }

    // 憲法文字色変更
    private fun onChangeConstitutionListTextColor(data: MutableList<Pair<Long, String>>) {
        val constitutionList = data.map { it.second }
            .filter { it.isNotBlank() }
        _constitutionListDiffColor.value =
            if (((missionStatement == null || missionStatement.constitutionList.isEmpty()) && constitutionList.isEmpty()) || (missionStatement != null && missionStatement.constitutionList == constitutionList)) {
                R.color.text_color_light_secondary
            } else {
                R.color.text_color_light_primary
            }
    }

    // 確定ボタン
    fun onClickConfirmButton() {
        val funeral = funeralList.value?.map { it.second }
            ?.filter { it.isNotBlank() } ?: run {
            _confirmStatus.value = Status.Failure(IllegalAccessError("理想の葬式データが入っていません"))
            return
        }
        val purposeLife = purposeLife.value ?: run {
            _confirmStatus.value = Status.Failure(IllegalAccessError("人生の目的データが入っていません"))
            return
        }
        val constitutionList = constitutionList.value?.map { it.second }
            ?.filter { it.isNotBlank() } ?: run {
            _confirmStatus.value = Status.Failure(IllegalAccessError("憲法データが入っていません"))
            return
        }
        viewModelScope.launch {
            val dto = MissionStatementInputDto(funeral, purposeLife, constitutionList)
            missionStatement?.let {
                missionStatementUseCase.updateMissionStatement(it, dto)
            } ?: run {
                missionStatementUseCase.createMissionStatement(dto)
            }
            _confirmStatus.value = Status.Success(missionStatement)
            MissionStatementDispatcher.updateMissionStatement()
        }
    }

    // 登録/更新ボタン活性・非活性変更
    private fun changeButton() {
        val purposeLife = purposeLife.value ?: return
        val funeralList = _funeralList.value?.map { it.second }
            ?.filter { it.isNotBlank() } ?: return
        val constitutionList = _constitutionList.value?.map { it.second }
            ?.filter { it.isNotBlank() } ?: return

        _isEnableConfirmButton.value =
            (purposeLife.isNotBlank() || funeralList.isNotEmpty() || constitutionList.isNotEmpty()) && (missionStatement == null || (purposeLife != missionStatement.purposeLife || funeralList != missionStatement.funeralList || constitutionList != missionStatement.constitutionList))
    }

    @AssistedFactory
    interface Factory {
        fun create(missionStatement: MissionStatement?): MissionStatementSettingViewModel
    }

    companion object {
        @Suppress("UNCHECKED_CAST")
        fun provideFactory(
            factory: Factory,
            missionStatement: MissionStatement?
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return factory.create(missionStatement) as T
            }
        }
    }
}
