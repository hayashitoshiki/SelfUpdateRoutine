[document](../../index.md) / [com.myapp.domain.usecase](../index.md) / [MissionStatementUseCase](./index.md)

# MissionStatementUseCase

`interface MissionStatementUseCase`

ミッションステートメント（７つの習慣第２の週間）関連機能のロジック

### Functions

| Name | Summary |
|---|---|
| [createMissionStatement](create-mission-statement.md) | `abstract suspend fun createMissionStatement(dto: `[`MissionStatementInputDto`](../../com.myapp.domain.dto/-mission-statement-input-dto/index.md)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>ミッションステートメント作成 |
| [getMissionStatement](get-mission-statement.md) | `abstract suspend fun getMissionStatement(): `[`MissionStatement`](../../com.myapp.domain.model.entity/-mission-statement/index.md)`?`<br>ミッションステートメントオブジェクト取得 |
| [updateMissionStatement](update-mission-statement.md) | `abstract suspend fun updateMissionStatement(missionStatement: `[`MissionStatement`](../../com.myapp.domain.model.entity/-mission-statement/index.md)`, dto: `[`MissionStatementInputDto`](../../com.myapp.domain.dto/-mission-statement-input-dto/index.md)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>ミッションステートメント更新 |

### Inheritors

| Name | Summary |
|---|---|
| [MissionStatementUseCaseImp](../-mission-statement-use-case-imp/index.md) | `class MissionStatementUseCaseImp : `[`MissionStatementUseCase`](./index.md) |
