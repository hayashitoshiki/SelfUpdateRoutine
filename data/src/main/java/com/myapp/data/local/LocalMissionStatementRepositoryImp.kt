package com.myapp.data.local

import com.myapp.common.getDateTimeNow
import com.myapp.data.Converter
import com.myapp.data.local.database.dao.mission_statement.ConstitutionDao
import com.myapp.data.local.database.dao.mission_statement.FuneralDao
import com.myapp.data.local.database.dao.mission_statement.PurposeLifeDao
import com.myapp.domain.model.entity.MissionStatement
import com.myapp.domain.repository.LocalMissionStatementRepository
import javax.inject.Inject

class LocalMissionStatementRepositoryImp @Inject constructor(
    private val funeralDao: FuneralDao,
    private val purposeLifeDao: PurposeLifeDao,
    private val constitutionDao: ConstitutionDao
) : LocalMissionStatementRepository {

    // ミッションステートメント保存
    override suspend fun saveMissionStatement(missionStatement: MissionStatement) {
        funeralDao.deleteAll()
        Converter.funeralEntityListFromMissionStatement(missionStatement)
            .forEach {
                funeralDao.insert(it)
            }
        purposeLifeDao.get()
            ?.also {
                it.text = missionStatement.purposeLife
                it.updateAt = getDateTimeNow()
                purposeLifeDao.update(it)
            } ?: run {
            val purposeEntity = Converter.purposeLifeEntityFromMissionStatement(missionStatement)
            purposeLifeDao.insert(purposeEntity)
        }
        constitutionDao.deleteAll()
        Converter.constitutionEntityListFromMissionStatement(missionStatement)
            .forEach {
                constitutionDao.insert(it)
            }
    }

    // ミッションステートメント取得
    override suspend fun getMissionStatement(): MissionStatement? {
        val funeralList = funeralDao.getAll()
        val purposeLife = purposeLifeDao.get()
        val constitutionList = constitutionDao.getAll()
        return Converter.missionStatementFromEntity(funeralList, purposeLife, constitutionList)
    }
}
