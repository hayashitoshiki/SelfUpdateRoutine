[document](../../index.md) / [com.myapp.chatmemo.domain.repository](../index.md) / [LocalTemplateRepository](./index.md)

# LocalTemplateRepository

`interface LocalTemplateRepository`

テンプレートに関するローカルデータソースCRUD用Repository

### Functions

| Name | Summary |
|---|---|
| [createTemplate](create-template.md) | `abstract suspend fun createTemplate(template: `[`Template`](../../com.myapp.chatmemo.domain.model.entity/-template/index.md)`): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>定型文登録 |
| [deleteTemplate](delete-template.md) | `abstract suspend fun deleteTemplate(templateId: `[`TemplateId`](../../com.myapp.chatmemo.domain.model.value/-template-id/index.md)`): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>指定の定型文削除 |
| [getNextTemplateId](get-next-template-id.md) | `abstract suspend fun getNextTemplateId(): `[`TemplateId`](../../com.myapp.chatmemo.domain.model.value/-template-id/index.md)<br>次のID連番の値を返す |
| [getTemplateAll](get-template-all.md) | `abstract fun getTemplateAll(): Flow<`[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`Template`](../../com.myapp.chatmemo.domain.model.entity/-template/index.md)`>>`<br>定型文のタイトル一覧取得 |
| [getTemplateMessageById](get-template-message-by-id.md) | `abstract suspend fun getTemplateMessageById(templateId: `[`TemplateId`](../../com.myapp.chatmemo.domain.model.value/-template-id/index.md)`): `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`TemplateMessage`](../../com.myapp.chatmemo.domain.model.value/-template-message/index.md)`>`<br>タイトルに紐づいた定型文リスト取得 |
| [updateTemplate](update-template.md) | `abstract suspend fun updateTemplate(template: `[`Template`](../../com.myapp.chatmemo.domain.model.entity/-template/index.md)`): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>定型文更新 |

### Inheritors

| Name | Summary |
|---|---|
| [LocalTemplateRepositoryImp](../../com.myapp.chatmemo.data.repository/-local-template-repository-imp/index.md) | `class LocalTemplateRepositoryImp : `[`LocalTemplateRepository`](./index.md) |
