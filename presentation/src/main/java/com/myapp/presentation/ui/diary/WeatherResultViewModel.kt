package com.myapp.presentation.ui.diary

import androidx.lifecycle.*
import com.myapp.domain.dto.AllReportInputDto
import com.myapp.domain.usecase.ReportUseCase
import com.myapp.presentation.utill.Status
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

/**
 * 振り返り_天気比喩振り返り確認画面　画面ロジック
 */
class WeatherResultViewModel(private val reportUseCase: ReportUseCase) : ViewModel() {

    // 事実
    private val _factInputText = MutableLiveData("")
    private val factInputText: LiveData<String> = _factInputText

    // 発見
    private val _findInputText = MutableLiveData("")
    private val findInputText: LiveData<String> = _findInputText

    // 学び
    private val _learnInputText = MutableLiveData("")
    private val learnInputText: LiveData<String> = _learnInputText

    // 宣言
    private val _statementInputText = MutableLiveData("")
    private val statementInputText: LiveData<String> = _statementInputText

    // 評価
    private val _assessmentInputInt = MutableLiveData(0)
    private val assessmentInputInt: LiveData<Int> = _assessmentInputInt
    val assessmentInputText: LiveData<String> = assessmentInputInt.map {
        when (it) {
            in 0..20 -> "雨"
            in 21..40 -> "雨時々曇り"
            in 41..60 -> "曇り"
            in 61..80 -> "曇り時々晴れ"
            in 81..100 -> "晴れ"
            else -> "不正値"
        }
    }

    // 理由
    private val _reasonInputText = MutableLiveData("")
    val reasonInputText: LiveData<String> = _reasonInputText

    // 改善
    private val _improveInputText = MutableLiveData("")
    val improveInputText: LiveData<String> = _improveInputText

    // 保存アクション結果
    private val _saveState = MutableLiveData<Status<*>>()
    val saveState: LiveData<Status<*>> = _saveState

    init {
        DiaryDispatcher.factTextFlow.onEach { _factInputText.value = it }
            .launchIn(viewModelScope)
        DiaryDispatcher.findTextFlow.onEach { _findInputText.value = it }
            .launchIn(viewModelScope)
        DiaryDispatcher.learnTextFlow.onEach { _learnInputText.value = it }
            .launchIn(viewModelScope)
        DiaryDispatcher.statementTextFlow.onEach { _statementInputText.value = it }
            .launchIn(viewModelScope)
        DiaryDispatcher.assessmentTextFlow.onEach { _assessmentInputInt.value = it }
            .launchIn(viewModelScope)
        DiaryDispatcher.reasonTextFlow.onEach { _reasonInputText.value = it }
            .launchIn(viewModelScope)
        DiaryDispatcher.improveTextFlow.onEach { _improveInputText.value = it }
            .launchIn(viewModelScope)
    }

    // 振り返り日記登録
    fun saveReport() {
        val section11 = factInputText.value ?: run {
            _saveState.value = Status.Failure(IllegalAccessError("事実データが入っていません"))
            return
        }
        val section12 = findInputText.value ?: run {
            _saveState.value = Status.Failure(IllegalAccessError("発見データが入っていません"))
            return
        }
        val section13 = learnInputText.value ?: run {
            _saveState.value = Status.Failure(IllegalAccessError("学びデータが入っていません"))
            return
        }
        val section14 = statementInputText.value ?: run {
            _saveState.value = Status.Failure(IllegalAccessError("宣言データが入っていません"))
            return
        }
        val section21 = assessmentInputInt.value ?: run {
            _saveState.value = Status.Failure(IllegalAccessError("評価データが入っていません"))
            return
        }
        val section22 = reasonInputText.value ?: run {
            _saveState.value = Status.Failure(IllegalAccessError("理由データが入っていません"))
            return
        }
        val section23 = improveInputText.value ?: run {
            _saveState.value = Status.Failure(IllegalAccessError("改善データが入っていません"))
            return
        }
        val allReportInputDto = AllReportInputDto(
            section11, section12, section13, section14, section21, section22, section23
        )
        viewModelScope.launch {
            reportUseCase.saveReport(allReportInputDto)
            _saveState.value = Status.Success(null)
        }
    }

}