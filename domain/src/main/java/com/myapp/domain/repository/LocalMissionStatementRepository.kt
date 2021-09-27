package com.myapp.domain.repository

import com.myapp.domain.model.entity.MissionStatement

/**
 * ミッションステートメント関連のCRUD処置
 *
 */
interface LocalMissionStatementRepository {

    /**
     * ミッションステートメント保存
     *
     * @param missionStatement 保存するミッションステートメント
     */
    suspend fun saveMissionStatement(missionStatement: MissionStatement)

    /**
     * ミッションステートメント取得
     *
     * @return　取得したミッションステートメント
     */
    suspend fun getMissionStatement(): MissionStatement?
}
