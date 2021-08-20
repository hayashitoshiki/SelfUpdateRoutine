package com.myapp.domain.dto

/**
 * 未確定のミッションステートメントデータの受け渡し用Dto
 *
 * @property funeralList 理想の葬式
 * @property purposeLife 人生の目的
 * @property constitutionList 憲法
 */
data class MissionStatementInputDto(
    val funeralList: List<String>,
    val purposeLife: String,
    val constitutionList: List<String>
)