package com.myapp.presentation.ui.mission_statement

import androidx.lifecycle.*
import com.myapp.domain.model.entity.MissionStatement
import com.myapp.domain.usecase.MissionStatementUseCase
import com.myapp.presentation.utils.Status
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

/**
 * ミッションステートメント一覧画面_画面ロジック
 *
 */
class MissionStatementListViewModel(private val missionStatementUseCase: MissionStatementUseCase) : ViewModel() {

    // 登録済みのミッションステートメント
    var missionStatement: MissionStatement? = null

    // 理想の葬式
    private val _funeralList = MutableLiveData(listOf(""))
    val funeralList: LiveData<List<String>> = _funeralList
    private val _isEnableFuneralList = MediatorLiveData<Boolean>()
    val isEnableFuneralList: LiveData<Boolean> = _isEnableFuneralList

    // 人生の目的
    private val _purposeLife = MutableLiveData("")
    val purposeLife: LiveData<String> = _purposeLife
    private val _isEnablePurposeLife = MediatorLiveData<Boolean>()
    val isEnablePurposeLife: LiveData<Boolean> = _isEnablePurposeLife


    // 憲法
    private val _constitutionList = MutableLiveData(listOf(""))
    val constitutionList: LiveData<List<String>> = _constitutionList
    private val _isEnableConstitutionList = MediatorLiveData<Boolean>()
    val isEnableConstitutionList: LiveData<Boolean> = _isEnableConstitutionList

    //　ミッションステートメント取得ステータス
    private val _status = MutableLiveData<Status<*>>()
    val status: LiveData<Status<*>> = _status

    init {
        _isEnableFuneralList.addSource(funeralList) { changeFuneral() }
        _isEnablePurposeLife.addSource(purposeLife) { changePurposeLife() }
        _isEnableConstitutionList.addSource(constitutionList) { changeConstitutionList() }
        updateMissionStatement()
        MissionStatementDispatcher.updateMessage.onEach {
            updateMissionStatement()
        }
            .launchIn(viewModelScope)
    }

    private fun updateMissionStatement() = viewModelScope.launch {
        missionStatementUseCase.getMissionStatement()
            ?.let {
                _funeralList.value = it.funeralList
                _purposeLife.value = it.purposeLife
                _constitutionList.value = it.constitutionList
                missionStatement = it
                _status.value = Status.Success(it)
            } ?: run {
            _funeralList.value = listOf("")
            _purposeLife.value = ""
            _constitutionList.value = listOf("")
            _status.value = Status.Failure(IllegalAccessException("初回起動"))
        }
    }

    private fun changeFuneral() {
        _isEnableFuneralList.value = _funeralList.value != listOf("")
    }

    private fun changePurposeLife() {
        _isEnablePurposeLife.value = _purposeLife.value != ""
    }

    private fun changeConstitutionList() {
        _isEnableConstitutionList.value = _constitutionList.value != listOf("")
    }

}