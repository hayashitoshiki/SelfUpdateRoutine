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
                is DiaryDispatcherContract.Action.ChangeLearn -> setState { copy(learn = it.value) }
                is DiaryDispatcherContract.Action.ChangeReason -> setState { copy(reason = it.value) }
                is DiaryDispatcherContract.Action.ChangeStatement -> setState { copy(statement = it.value) }
            }
        }
            .launchIn(viewModelScope)
    }

    // 振り返り日記登録
    private fun saveReport() = viewModelScope.launch {
        runCatching {
            if (state.value.fact.isEmpty()) throw IllegalAccessError("事実データが入っていません")
            if (state.value.find.isEmpty()) throw IllegalAccessError("発見データが入っていません")
            if (state.value.learn.isEmpty()) throw IllegalAccessError("学びデータが入っていません")
            if (state.value.statement.isEmpty()) throw IllegalAccessError("宣言データが入っていません")
            if (state.value.reason.isEmpty()) throw IllegalAccessError("理由データが入っていません")
            if (state.value.improve.isEmpty()) throw IllegalAccessError("改善案データが入っていません")

            val allReportInputDto = AllReportInputDto(
                state.value.fact, state.value.find, state.value.learn, state.value.statement, state.value.assessment,
                state.value.reason, state.value.improve
            )
            reportUseCase.saveReport(allReportInputDto)
        }
            .onSuccess { setEffect { WeatherResultContract.Effect.SaveResult(Status.Success(null)) } }
            .onFailure { setEffect { WeatherResultContract.Effect.SaveResult(Status.Failure(it)) } }
    }

}
