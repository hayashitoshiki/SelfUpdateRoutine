package com.myapp.presentation.ui.constitution

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.myapp.domain.usecase.MissionStatementUseCase

/**
 * ミッションステートメント一覧画面_画面ロジック
 *
 */
class ConstitutionListViewModel(missionStatementUseCase: MissionStatementUseCase) : ViewModel() {

    // 理想の葬式
    private val _funeralList = MutableLiveData<List<String>>()
    val funeralList: LiveData<List<String>> = _funeralList

    // 人生の目的
    private val _purposeLife = MutableLiveData<String>()
    val purposeLife: LiveData<String> = _purposeLife

    // 憲法
    private val _constitutionList = MutableLiveData<List<String>>()
    val constitutionList: LiveData<List<String>> = _constitutionList

    init {
        missionStatementUseCase.getMissionStatement()
            .also {
                _funeralList.value = it.funeralList
                _purposeLife.value = it.purposeLife
                _constitutionList.value = it.constitutionList
            }
    }

}