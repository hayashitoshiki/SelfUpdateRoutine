package com.myapp.domain.usecase

import com.myapp.domain.dto.MissionStatementInputDto
import com.myapp.domain.model.entity.MissionStatement

/**
 * ミッションステートメント（７つの習慣第２の週間）関連機能のロジック
 *
 */
interface MissionStatementUseCase {

    /**
     * ミッションステートメントオブジェクト取得
     *
     * もしミッションステートメントが保存されていない場合nullを返す
     * @return ミッションステートメント
     */
    suspend fun getMissionStatement(): MissionStatement?

    /**
     * ミッションステートメント作成
     *
     * @param dto ミッションステートメント作成データ
     */
    suspend fun createMissionStatement(dto: MissionStatementInputDto)

    /**
     * ミッションステートメント更新
     *
     * @param missionStatement 更新前のミッションステートメント
     * @param dto ミッションステートメント更新データ
     */
    suspend fun updateMissionStatement(
        missionStatement: MissionStatement,
        dto: MissionStatementInputDto
    )

}