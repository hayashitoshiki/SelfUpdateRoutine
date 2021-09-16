[document](../../index.md) / [com.myapp.data.local](../index.md) / [Converter](index.md) / [missionStatementFromEntity](./mission-statement-from-entity.md)

# missionStatementFromEntity

`fun missionStatementFromEntity(funeralEntityList: `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`FuneralEntity`](../../com.myapp.data.local.database.entity.mission_statement/-funeral-entity/index.md)`>, purposeLifeEntity: `[`PurposeLifeEntity`](../../com.myapp.data.local.database.entity.mission_statement/-purpose-life-entity/index.md)`?, constitutionEntityList: `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`ConstitutionEntity`](../../com.myapp.data.local.database.entity.mission_statement/-constitution-entity/index.md)`>): `[`MissionStatement`](../../com.myapp.domain.model.entity/-mission-statement/index.md)`?`

各種Entityからミッションステートメントオブジェクト生成

もし理想の葬式リストが空でかつ、
人生の目的がnullまたは空でかつ、
憲法リストが空なら、
引数の全ての値が空ならnullを返す

### Parameters

`funeralEntityList` - 理想の葬儀リスト

`purposeLifeEntity` - 人生の目的

`constitutionEntityList` - 憲法リスト

**Return**
ミッションステートメント

