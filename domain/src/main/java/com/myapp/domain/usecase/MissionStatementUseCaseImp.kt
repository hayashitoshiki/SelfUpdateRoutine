package com.myapp.domain.usecase

import com.myapp.domain.model.entity.MissionStatement

class MissionStatementUseCaseImp : MissionStatementUseCase {
    private val funeralList = listOf("弔辞：感謝されること", "人：優しい人間")
    private val purposeLife = "世界一楽しく生きること"
    private val constitutionList = listOf("迷ったときは楽しい方向へ", "常に楽しいを見つける努力を")
    private val missionStatement = MissionStatement(funeralList, purposeLife, constitutionList)
    override fun getMissionStatement(): MissionStatement {
        return missionStatement // TODO : ミッションステートメント追加機能実施後、Repository連携
    }
}