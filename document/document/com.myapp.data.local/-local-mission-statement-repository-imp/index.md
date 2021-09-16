[document](../../index.md) / [com.myapp.data.local](../index.md) / [LocalMissionStatementRepositoryImp](./index.md)

# LocalMissionStatementRepositoryImp

`class LocalMissionStatementRepositoryImp : `[`LocalMissionStatementRepository`](../../com.myapp.domain.repository/-local-mission-statement-repository/index.md)

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `LocalMissionStatementRepositoryImp(funeralDao: `[`FuneralDao`](../../com.myapp.data.local.database.dao.mission_statement/-funeral-dao/index.md)`, purposeLifeDao: `[`PurposeLifeDao`](../../com.myapp.data.local.database.dao.mission_statement/-purpose-life-dao/index.md)`, constitutionDao: `[`ConstitutionDao`](../../com.myapp.data.local.database.dao.mission_statement/-constitution-dao/index.md)`)` |

### Functions

| Name | Summary |
|---|---|
| [getMissionStatement](get-mission-statement.md) | `suspend fun getMissionStatement(): `[`MissionStatement`](../../com.myapp.domain.model.entity/-mission-statement/index.md)`?`<br>ミッションステートメント取得 |
| [saveMissionStatement](save-mission-statement.md) | `suspend fun saveMissionStatement(missionStatement: `[`MissionStatement`](../../com.myapp.domain.model.entity/-mission-statement/index.md)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>ミッションステートメント保存 |
