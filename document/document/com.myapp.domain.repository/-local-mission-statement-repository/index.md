[document](../../index.md) / [com.myapp.domain.repository](../index.md) / [LocalMissionStatementRepository](./index.md)

# LocalMissionStatementRepository

`interface LocalMissionStatementRepository`

ミッションステートメント関連のCRUD処置

### Functions

| Name | Summary |
|---|---|
| [getMissionStatement](get-mission-statement.md) | `abstract suspend fun getMissionStatement(): `[`MissionStatement`](../../com.myapp.domain.model.entity/-mission-statement/index.md)`?`<br>ミッションステートメント取得 |
| [saveMissionStatement](save-mission-statement.md) | `abstract suspend fun saveMissionStatement(missionStatement: `[`MissionStatement`](../../com.myapp.domain.model.entity/-mission-statement/index.md)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>ミッションステートメント保存 |

### Inheritors

| Name | Summary |
|---|---|
| [LocalMissionStatementRepositoryImp](../../com.myapp.data.local/-local-mission-statement-repository-imp/index.md) | `class LocalMissionStatementRepositoryImp : `[`LocalMissionStatementRepository`](./index.md) |
