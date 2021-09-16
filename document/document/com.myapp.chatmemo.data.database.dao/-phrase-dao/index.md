[document](../../index.md) / [com.myapp.chatmemo.data.database.dao](../index.md) / [PhraseDao](./index.md)

# PhraseDao

`interface PhraseDao`

定型文用クエリ管理

### Functions

| Name | Summary |
|---|---|
| [delete](delete.md) | `abstract suspend fun delete(comment: `[`TemplateMessageEntity`](../../com.myapp.chatmemo.data.database.entity/-template-message-entity/index.md)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [deleteById](delete-by-id.md) | `abstract suspend fun deleteById(templateId: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [deleteByTemplateId](delete-by-template-id.md) | `abstract suspend fun deleteByTemplateId(templateId: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [getAll](get-all.md) | `abstract suspend fun getAll(): `[`Array`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-array/index.html)`<`[`TemplateMessageEntity`](../../com.myapp.chatmemo.data.database.entity/-template-message-entity/index.md)`>` |
| [getAllByTitle](get-all-by-title.md) | `abstract suspend fun getAllByTitle(templateId: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`): `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`TemplateMessageEntity`](../../com.myapp.chatmemo.data.database.entity/-template-message-entity/index.md)`>` |
| [insert](insert.md) | `abstract suspend fun insert(comment: `[`TemplateMessageEntity`](../../com.myapp.chatmemo.data.database.entity/-template-message-entity/index.md)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [update](update.md) | `abstract suspend fun update(comment: `[`TemplateMessageEntity`](../../com.myapp.chatmemo.data.database.entity/-template-message-entity/index.md)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
