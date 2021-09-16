[document](../../index.md) / [com.myapp.domain.usecase](../index.md) / [MissionStatementUseCaseImp](./index.md)

# MissionStatementUseCaseImp

`class MissionStatementUseCaseImp : `[`MissionStatementUseCase`](../-mission-statement-use-case/index.md)

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `MissionStatementUseCaseImp(localMissionStatementRepository: `[`LocalMissionStatementRepository`](../../com.myapp.domain.repository/-local-mission-statement-repository/index.md)`)` |

### Functions

| Name | Summary |
|---|---|
| [createMissionStatement](create-mission-statement.md) | `suspend fun createMissionStatement(dto: `[`MissionStatementInputDto`](../../com.myapp.domain.dto/-mission-statement-input-dto/index.md)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>ミッションステートメント作成 |
| [getMissionStatement](get-mission-statement.md) | `suspend fun getMissionStatement(): `[`MissionStatement`](../../com.myapp.domain.model.entity/-mission-statement/index.md)`?`<br>ミッションステートメントオブジェクト取得 |
| [updateMissionStatement](update-mission-statement.md) | `suspend fun updateMissionStatement(missionStatement: `[`MissionStatement`](../../com.myapp.domain.model.entity/-mission-statement/index.md)`, dto: `[`MissionStatementInputDto`](../../com.myapp.domain.dto/-mission-statement-input-dto/index.md)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>ミッションステートメント更新 |
