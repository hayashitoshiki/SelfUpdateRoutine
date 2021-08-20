package com.myapp.domain.usecase

import com.myapp.domain.dto.MissionStatementInputDto
import com.myapp.domain.model.entity.MissionStatement
import com.myapp.domain.repository.LocalMissionStatementRepository
import com.myapp.domain.translator.MissionStatementTranslator

// ミッションステートメント機能ロジック
class MissionStatementUseCaseImp(private val localMissionStatementRepository: LocalMissionStatementRepository) :
    MissionStatementUseCase {

    // ミッションステートメント取得
    override suspend fun getMissionStatement(): MissionStatement? {
        return localMissionStatementRepository.getMissionStatement()
    }

    // ミッションステートメント作成
    override suspend fun createMissionStatement(dto: MissionStatementInputDto) {
        val missionStatement = MissionStatementTranslator.missionStatementFromMissionStatementDto(dto)
        localMissionStatementRepository.saveMissionStatement(missionStatement)
    }

    // ミッションステートメント更新
    override suspend fun updateMissionStatement(
        missionStatement: MissionStatement,
        dto: MissionStatementInputDto
    ) {
        missionStatement.also {
            it._funeralList = dto.funeralList
            it._purposeLife = dto.purposeLife
            it._constitutionList = dto.constitutionList
        }
        localMissionStatementRepository.saveMissionStatement(missionStatement)
    }

}