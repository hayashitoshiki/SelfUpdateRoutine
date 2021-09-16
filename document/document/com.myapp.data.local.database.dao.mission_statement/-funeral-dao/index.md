[document](../../index.md) / [com.myapp.data.local.database.dao.mission_statement](../index.md) / [FuneralDao](./index.md)

# FuneralDao

`interface FuneralDao`

理想の葬儀用クエリ管理

### Functions

| Name | Summary |
|---|---|
| [deleteAll](delete-all.md) | `abstract suspend fun deleteAll(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [getAll](get-all.md) | `abstract suspend fun getAll(): `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`FuneralEntity`](../../com.myapp.data.local.database.entity.mission_statement/-funeral-entity/index.md)`>` |
| [insert](insert.md) | `abstract suspend fun insert(funeralEntity: `[`FuneralEntity`](../../com.myapp.data.local.database.entity.mission_statement/-funeral-entity/index.md)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
