package com.myapp.presentation.ui.mission_statement

import androidx.lifecycle.*
import com.myapp.domain.usecase.MissionStatementUseCase
import com.myapp.presentation.utils.base.BaseAacViewModel
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
    BaseAacViewModel<MissionStatementListContract.State, MissionStatementListContract.Effect, MissionStatementListContract.Event>() {

    override fun initState(): MissionStatementListContract.State {
        return MissionStatementListContract.State()
    }

    override fun handleEvents(event: MissionStatementListContract.Event) = when(event) {
        is MissionStatementListContract.Event.OnClickChangeButton -> {
            setEffect { MissionStatementListContract.Effect.NavigateMissionStatementSetting(state.value?.missionStatement) }
        }
        is MissionStatementListContract.Event.OnDestroyView -> onDestroyView()
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
                            isEnableFuneralList = it.funeralList.isNotEmpty(),
                            purposeLife = it.purposeLife,
                            isEnablePurposeLife = it.purposeLife.isNotEmpty(),
                            constitutionList = it.constitutionList,
                            isEnableConstitutionList = it.constitutionList.isNotEmpty()
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
                isEnableFuneralList = false,
                purposeLife = "",
                isEnablePurposeLife = false,
                constitutionList = listOf(),
                isEnableConstitutionList = false
            )
        }
    }

    // 画面破棄（初期化）処理
    private fun onDestroyView() {
        setEffect { MissionStatementListContract.Effect.OnDestroyView }
    }
}
