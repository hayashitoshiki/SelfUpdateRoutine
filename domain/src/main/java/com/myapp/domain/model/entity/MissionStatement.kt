package com.myapp.domain.model.entity

import java.io.Serializable

/**
 * ミッションステートメントリスト
 *
 * @property _funeralList 理想の葬式リスト（設定用）
 * @property funeralList 理想の葬式リスト
 * @property _purposeLife 人生の目的（設定用）
 * @property purposeLife 人生の目的
 * @property _constitutionList 憲法リスト（設定用）
 * @property constitutionList 憲法リスト
 */
data class MissionStatement(
    internal var _funeralList: List<String>,
    internal var _purposeLife: String,
    internal var _constitutionList: List<String>
) : Serializable {
    val funeralList: List<String> = _funeralList
    val purposeLife: String = _purposeLife
    val constitutionList: List<String> = _constitutionList
}