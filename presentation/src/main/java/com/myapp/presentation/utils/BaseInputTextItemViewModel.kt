package com.myapp.presentation.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * リサイクルビュー_テキスト入力用アイテム 画面ベースロジック
 *
 */
abstract class BaseInputTextItemViewModel : ViewModel() {

    val value = MutableLiveData<String>()

    private val _isPlusButtonEnable = MutableLiveData(true)
    val isPlusButtonEnable: LiveData<Boolean> = _isPlusButtonEnable

    protected val _isMinusButtonEnable = MutableLiveData(true)
    val isMinusButtonEnable: LiveData<Boolean> = _isMinusButtonEnable
}
