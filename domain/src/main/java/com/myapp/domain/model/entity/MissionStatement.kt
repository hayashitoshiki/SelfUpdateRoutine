package com.myapp.domain.model.entity

import java.io.Serializable

/**
 * ミッションステートメントリスト
 *
 * @property funeralList 理想の葬式リスト
 * @property purposeLife 人生の目的
 * @property constitutionList 憲法リスト
 */
data class MissionStatement(
    var funeralList: List<String>,
    var purposeLife: String,
    var constitutionList: List<String>
) : Serializable