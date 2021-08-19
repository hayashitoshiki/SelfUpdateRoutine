package com.myapp.domain.translator

import com.myapp.domain.dto.MissionStatementInputDto
import com.myapp.domain.model.entity.MissionStatement

/**
 * ミッションステートメント用　Dto -> DomainModel コンバーター
 */
object MissionStatementTranslator {

    /**
     * dto ->ミッションステートメントオブジェクト変換
     *
     * @param dto ミッションステートメント入力値
     * @return ミッションステートメントオブジェクト
     */
    fun missionStatementFromMissionStatementDto(dto: MissionStatementInputDto): MissionStatement {
        return MissionStatement(dto.funeralList, dto.purposeLife, dto.constitutionList)
    }
}