package com.myapp.data.local

import com.myapp.common.getDateTimeNow
import com.myapp.data.local.database.dao.ConstitutionDao
import com.myapp.data.local.database.dao.FuneralDao
import com.myapp.data.local.database.dao.PurposeLifeDao
import com.myapp.data.local.database.entity.ConstitutionEntity
import com.myapp.data.local.database.entity.FuneralEntity
import com.myapp.data.local.database.entity.PurposeLifeEntity
import com.myapp.domain.model.entity.MissionStatement
import com.myapp.domain.repository.LocalMissionStatementRepository

class LocalMissionStatementRepositoryImp(
    private val funeralDao: FuneralDao,
    private val purposeLifeDao: PurposeLifeDao,
    private val constitutionDao: ConstitutionDao
) : LocalMissionStatementRepository {

    override suspend fun saveMissionStatement(missionStatement: MissionStatement) {
        funeralDao.deleteAll()
        funeralEntityListFromMissionStatement(missionStatement).forEach {
            funeralDao.insert(it)
        }
        purposeLifeDao.get()
            ?.also {
                it.text = missionStatement.purposeLife
                it.updateAt = getDateTimeNow()
                purposeLifeDao.update(it)
            } ?: run {
            purposeLifeEntityFromMissionStatement(missionStatement).also {
                purposeLifeDao.insert(it)
            }
        }
        constitutionDao.deleteAll()
        constitutionEntityListFromMissionStatement(missionStatement).forEach {
            constitutionDao.insert(it)
        }
    }

    override suspend fun getMissionStatement(): MissionStatement? {
        val purposeList = funeralDao.getAll()
            .map { it.text }
        val purposeLife = purposeLifeDao.get()?.text ?: ""
        val constitutionList = constitutionDao.getAll()
            .map { it.text }
        return if (purposeList.isEmpty() && purposeLife == "" && constitutionList.isEmpty()) {
            null
        } else {
            MissionStatement(purposeList, purposeLife, constitutionList)
        }
    }

    private fun funeralEntityListFromMissionStatement(missionStatement: MissionStatement): List<FuneralEntity> {
        return missionStatement.funeralList.map {
            FuneralEntity(0, it, getDateTimeNow())
        }
    }

    private fun constitutionEntityListFromMissionStatement(missionStatement: MissionStatement): List<ConstitutionEntity> {
        return missionStatement.constitutionList.map {
            ConstitutionEntity(0, it, getDateTimeNow())
        }
    }

    private fun purposeLifeEntityFromMissionStatement(missionStatement: MissionStatement): PurposeLifeEntity {
        return PurposeLifeEntity(1, missionStatement.purposeLife, getDateTimeNow(), getDateTimeNow())
    }

}