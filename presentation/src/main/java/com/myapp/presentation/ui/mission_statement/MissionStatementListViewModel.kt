package com.myapp.presentation.ui.mission_statement

import androidx.lifecycle.*
import com.myapp.domain.usecase.MissionStatementUseCase
import com.myapp.presentation.utils.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ミッションステートメント一覧画面_画面ロジック
 *
 */
@HiltViewModel
class MissionStatementListViewModel @Inject constructor(private val missionStatementUseCase: MissionStatementUseCase) :
    BaseViewModel<MissionStatementListContract.State, MissionStatementListContract.Effect, MissionStatementListContract.Event>() {

    override fun initState(): MissionStatementListContract.State {
        return MissionStatementListContract.State()
    }

    override fun handleEvents(event: MissionStatementListContract.Event) = when(event) {
        is MissionStatementListContract.Event.OnClickChangeButton -> {
            setEffect { MissionStatementListContract.Effect.NavigateMissionStatementSetting(state.value?.missionStatement) }
        }
    }

    init {
        updateMissionStatement()
        MissionStatementDispatcher.action.onEach {
            if (it !is MissionStatementDispatcherContract.Action.Update) return@onEach
            updateMissionStatement()
        }.launchIn(viewModelScope)
    }

    private fun updateMissionStatement() = viewModelScope.launch {
        runCatching { missionStatementUseCase.getMissionStatement() }
            .onSuccess {
                it?.let {
                    setState {
                        copy(
                            missionStatement = it,
                            funeralList = it.funeralList,
                            purposeLife = it.purposeLife,
                            constitutionList = it.constitutionList,
                        )
                    }
                } ?: run { setNullMissionStatement() }
            }
            .onFailure { setNullMissionStatement() }
    }

    private fun setNullMissionStatement() {
        setState {
            copy(
                missionStatement = null,
                funeralList = listOf(),
                purposeLife = "",
                constitutionList = listOf()
            )
        }
    }
}
