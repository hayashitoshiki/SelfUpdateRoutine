package com.myapp.presentation.ui.diary

import androidx.lifecycle.viewModelScope
import com.myapp.domain.dto.AllReportInputDto
import com.myapp.domain.usecase.ReportUseCase
import com.myapp.presentation.utils.base.BaseViewModel
import com.myapp.presentation.utils.base.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * 振り返り_天気比喩振り返り確認画面　画面ロジック
 */
@HiltViewModel
class WeatherResultViewModel @Inject constructor(private val reportUseCase: ReportUseCase) :
    BaseViewModel<WeatherResultContract.State, WeatherResultContract.Effect, WeatherResultContract.Event>() {

    override fun initState(): WeatherResultContract.State {
        return WeatherResultContract.State()
    }

    override fun handleEvents(event: WeatherResultContract.Event) {
        when (event) {
            is WeatherResultContract.Event.OnClickSaveButton -> saveReport()
        }
    }

    init {
        DiaryDispatcher.action.onEach {
            when (it) {
                is DiaryDispatcherContract.Action.ChangeAssessment -> setState { copy(assessment = (it.value * 100).toInt()) }
                is DiaryDispatcherContract.Action.ChangeFact -> setState { copy(fact = it.value) }
                is DiaryDispatcherContract.Action.ChangeFind -> setState { copy(find = it.value) }
                is DiaryDispatcherContract.Action.ChangeImprove -> setState { copy(improve = it.value) }
                is DiaryDispatcherContract.Action.ChangeLesson -> setState { copy(learn = it.value) }
                is DiaryDispatcherContract.Action.ChangeReason -> setState { copy(reason = it.value) }
                is DiaryDispatcherContract.Action.ChangeStatement -> setState { copy(statement = it.value) }
            }
        }
            .launchIn(viewModelScope)
    }

    // 振り返り日記登録
    private fun saveReport() {
        if (state.value.fact.isEmpty()) {
            setEffect { WeatherResultContract.Effect.SaveResult(Status.Failure(IllegalAccessError("事実データが入っていません"))) }
            return
        }
        if (state.value.find.isEmpty()) {
            setEffect { WeatherResultContract.Effect.SaveResult(Status.Failure(IllegalAccessError("発見データが入っていません"))) }
            return
        }
        if (state.value.learn.isEmpty()) {
            setEffect { WeatherResultContract.Effect.SaveResult(Status.Failure(IllegalAccessError("学びデータが入っていません"))) }
            return
        }
        if (state.value.statement.isEmpty()) {
            setEffect { WeatherResultContract.Effect.SaveResult(Status.Failure(IllegalAccessError("宣言データが入っていません"))) }
            return
        }
        if (state.value.reason.isEmpty()) {
            setEffect { WeatherResultContract.Effect.SaveResult(Status.Failure(IllegalAccessError("理由データが入っていません"))) }
            return
        }
        if (state.value.improve.isEmpty()) {
            setEffect { WeatherResultContract.Effect.SaveResult(Status.Failure(IllegalAccessError("改善データが入っていません"))) }
            return
        }
        val allReportInputDto = AllReportInputDto(
            state.value.fact, state.value.find, state.value.learn, state.value.statement, state.value.assessment,
            state.value.reason, state.value.improve
        )
        viewModelScope.launch {
            reportUseCase.saveReport(allReportInputDto)
            setEffect { WeatherResultContract.Effect.SaveResult(Status.Success(null)) }
        }
    }

}
