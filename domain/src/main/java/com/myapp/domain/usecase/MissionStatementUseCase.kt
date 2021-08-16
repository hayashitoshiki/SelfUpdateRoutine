package com.myapp.domain.usecase

import com.myapp.domain.model.entity.MissionStatement

/**
 * ミッションステートメント（７つの習慣第２の週間）関連機能のロジック
 *
 */
interface MissionStatementUseCase {

    /**
     * ミッションステートメントオブジェクト取得
     *
     * @return
     */
    fun getMissionStatement(): MissionStatement
}