[document](../../index.md) / [com.myapp.data.local.database.dao.mission_statement](../index.md) / [ConstitutionDao](./index.md)

# ConstitutionDao

`interface ConstitutionDao`

憲法用クエリ管理

### Functions

| Name | Summary |
|---|---|
| [deleteAll](delete-all.md) | `abstract suspend fun deleteAll(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [getAll](get-all.md) | `abstract suspend fun getAll(): `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`ConstitutionEntity`](../../com.myapp.data.local.database.entity.mission_statement/-constitution-entity/index.md)`>` |
| [insert](insert.md) | `abstract suspend fun insert(constitutionEntity: `[`ConstitutionEntity`](../../com.myapp.data.local.database.entity.mission_statement/-constitution-entity/index.md)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
