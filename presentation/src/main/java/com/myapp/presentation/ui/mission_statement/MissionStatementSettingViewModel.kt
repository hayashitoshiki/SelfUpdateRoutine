package com.myapp.presentation.ui.mission_statement

import androidx.lifecycle.*
import com.myapp.domain.dto.MissionStatementInputDto
import com.myapp.domain.model.entity.MissionStatement
import com.myapp.domain.usecase.MissionStatementUseCase
import com.myapp.presentation.R
import com.myapp.presentation.utils.base.BaseAacViewModel
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
) : BaseAacViewModel<MissionStatementSettingContract.State, MissionStatementSettingContract.Effect, MissionStatementSettingContract.Event>() {

    override fun initState(): MissionStatementSettingContract.State {
        return MissionStatementSettingContract.State()
    }

    override fun handleEvents(event: MissionStatementSettingContract.Event) = when(event) {
        is MissionStatementSettingContract.Event.OnChangePurposeText -> changePurposeText(event.value)
        is MissionStatementSettingContract.Event.OnClickChangeButton -> onClickConfirmButton()
        is MissionStatementSettingContract.Event.OnDestroyView -> onDestroyView()
    }

    init {
        var purposeLife = ""
        var funeralListCount = 1L
        var funeralList = mutableListOf(Pair(0L, ""))
        var constitutionListCount = 1L
        var constitutionList = mutableListOf(Pair(0L, ""))
        missionStatement?.let {
            purposeLife = it.purposeLife
            funeralList = it.funeralList
                .map { funeral -> Pair(funeralListCount++, funeral) }
                .toMutableList()
            constitutionList = it.constitutionList
                .map { constitution -> Pair(constitutionListCount++, constitution) }
                .toMutableList()
        }
        setState {
            copy(
                funeralListCount = funeralListCount,
                funeralList = funeralList,
                purposeLife = purposeLife,
                constitutionListCount = constitutionListCount,
                constitutionList= constitutionList
            )
        }

        MissionStatementDispatcher.action.onEach {
            when(it) {
                is MissionStatementDispatcherContract.Action.AddConstitution -> addConstitution(it.index)
                is MissionStatementDispatcherContract.Action.AddFuneral -> addFuneral(it.index)
                is MissionStatementDispatcherContract.Action.ChangeConstitutionText -> changeConstitutionText(it.id, it.text)
                is MissionStatementDispatcherContract.Action.ChangeFuneralText -> changeFuneralText(it.id, it.text)
                is MissionStatementDispatcherContract.Action.DeleteConstitution -> deleteConstitution(it.index)
                is MissionStatementDispatcherContract.Action.DeleteFuneral -> deleteFuneral(it.index)
            }
        }.launchIn(viewModelScope)
    }

    private fun changePurposeText(text: String) {
        setState { copy(purposeLife = text) }
        changeButton()
        onChangePurposeLifeTextColor(text)
    }

    private fun changeConstitutionText(id: Long, text: String){
        state.value?.also { state ->
            val constitutionList = state.constitutionList.toMutableList()
            val index = constitutionList.indexOfFirst { it.first == id }
            if (index == -1) return
            constitutionList[index] = Pair(id, text)
            setState { copy(constitutionList = constitutionList) }
            changeButton()
            onChangeConstitutionListTextColor(constitutionList)
        }
    }

    private fun changeFuneralText(id: Long, text: String){
        state.value?.also { state ->
            val funeralList = state.funeralList.toMutableList()
            val index = funeralList.indexOfFirst { it.first == id }
            if (index == -1) return
            funeralList[index] = Pair(id, text)
            setState { copy(funeralList = funeralList) }
            changeButton()
            onChangeFuneralListTextColor(funeralList)
        }
    }

    private fun addConstitution(index: Int) {
        val state = state.value ?: return
        val list = state.constitutionList.toMutableList()
        if (list.size < index) return
        list.add(index, Pair(state.constitutionListCount, ""))
        setState { copy(constitutionListCount = state.constitutionListCount+ 1, constitutionList = list) }
        changeButton()
    }

    private fun addFuneral(index: Int) {
        val state = state.value ?: return
        val list = state.funeralList.toMutableList()
        if (list.size < index) return
        list.add(index, Pair(state.funeralListCount, ""))
        setState { copy(funeralListCount = state.funeralListCount+ 1, funeralList = list) }
        changeButton()
    }

    private fun deleteFuneral(index: Int) {
        val list = state.value?.funeralList?.toMutableList() ?: return
        if (list.size <= index) return
        list.removeAt(index)
        setState { copy(funeralList = list) }
        onChangeFuneralListTextColor(list)
        changeButton()
    }

    private fun deleteConstitution(index: Int) {
        val list = state.value?.constitutionList?.toMutableList() ?: return
        if (list.size <= index) return
        list.removeAt(index)
        setState { copy(constitutionList = list) }
        onChangeConstitutionListTextColor(list)
        changeButton()
    }

    // 人生の目的文字色変更
    private fun onChangePurposeLifeTextColor(data: String) {
        val purposeLifeDiffColor =
            if ((missionStatement == null && data.isNotBlank()) || (missionStatement != null && missionStatement.purposeLife != data)) {
                R.color.text_color_light_primary
            } else {
                R.color.text_color_light_secondary
            }
        setState { copy(purposeLifeDiffColor = purposeLifeDiffColor) }
    }

    // 理想の葬式文字色変更
    private fun onChangeFuneralListTextColor(data: MutableList<Pair<Long, String>>) {
        val funeralList = data.map { it.second }.filter { it.isNotBlank() }
        val funeralListDiffColor =
            if (((missionStatement == null || missionStatement.funeralList.isEmpty()) && funeralList.isEmpty()) || (missionStatement != null && missionStatement.funeralList == funeralList)) {
                R.color.text_color_light_secondary
            } else {
                R.color.text_color_light_primary
            }
        setState { copy(funeralListDiffColor = funeralListDiffColor) }
    }

    // 憲法文字色変更
    private fun onChangeConstitutionListTextColor(data: MutableList<Pair<Long, String>>) {
        val constitutionList = data.map { it.second }
            .filter { it.isNotBlank() }
        val constitutionListDiffColor =
            if (((missionStatement == null || missionStatement.constitutionList.isEmpty()) && constitutionList.isEmpty()) || (missionStatement != null && missionStatement.constitutionList == constitutionList)) {
                R.color.text_color_light_secondary
            } else {
                R.color.text_color_light_primary
            }
        setState { copy(constitutionListDiffColor = constitutionListDiffColor) }
    }

    // 確定ボタン
    fun onClickConfirmButton() {
        val funeral = state.value?.funeralList
            ?.map { it.second }
            ?.filter { it.isNotBlank() } ?: run {
            setEffect { MissionStatementSettingContract.Effect.ShowError(IllegalAccessError("理想の葬式データが入っていません")) }
            return
            }
        val purposeLife = state.value?.purposeLife ?: run {
            setEffect { MissionStatementSettingContract.Effect.ShowError(IllegalAccessError("人生の目的データが入っていません")) }
            return
        }
        val constitutionList = state.value?.constitutionList
            ?.map { it.second }
            ?.filter { it.isNotBlank() } ?: run {
            setEffect { MissionStatementSettingContract.Effect.ShowError(IllegalAccessError("憲法データが入っていません")) }
            return
            }
        viewModelScope.launch {
            val dto = MissionStatementInputDto(funeral, purposeLife, constitutionList)
            runCatching {
                if (missionStatement != null) missionStatementUseCase.updateMissionStatement(missionStatement, dto)
                else missionStatementUseCase.createMissionStatement(dto)
            }
                .onSuccess {
                    setEffect { MissionStatementSettingContract.Effect.NavigateMissionStatementSetting }
                    MissionStatementDispatcher.setActions(MissionStatementDispatcherContract.Action.Update)
                }
                .onFailure { setEffect { MissionStatementSettingContract.Effect.ShowError(it) } }
        }
    }

    // 登録/更新ボタン活性・非活性変更
    private fun changeButton() {
        val purposeLife = state.value?.purposeLife ?: return
        val funeralList = state.value?.funeralList
            ?.map { it.second }
            ?.filter { it.isNotBlank() } ?: return
        val constitutionList = state.value?.constitutionList
            ?.map { it.second }
            ?.filter { it.isNotBlank() } ?: return
        val isEnableConfirmButton =
            (purposeLife.isNotBlank() || funeralList.isNotEmpty() || constitutionList.isNotEmpty())
                    && (missionStatement == null || (purposeLife != missionStatement.purposeLife || funeralList != missionStatement.funeralList || constitutionList != missionStatement.constitutionList))
        setState { copy(isEnableConfirmButton = isEnableConfirmButton) }
    }

    private fun onDestroyView() {
        cashState = null
        setEffect{ MissionStatementSettingContract.Effect.OnDestroyView }
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

