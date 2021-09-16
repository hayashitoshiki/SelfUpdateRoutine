[document](../../index.md) / [com.myapp.chatmemo.domain.usecase](../index.md) / [TemplateUseCase](./index.md)

# TemplateUseCase

`interface TemplateUseCase`

テンプレートに関するビジネスロジック

### Functions

| Name | Summary |
|---|---|
| [createTemplate](create-template.md) | `abstract suspend fun createTemplate(template: `[`Template`](../../com.myapp.chatmemo.domain.model.entity/-template/index.md)`): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>テンプレート作成 |
| [deleteTemplate](delete-template.md) | `abstract suspend fun deleteTemplate(templateId: `[`TemplateId`](../../com.myapp.chatmemo.domain.model.value/-template-id/index.md)`): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>テンプレート削除 |
| [getNextTemplateId](get-next-template-id.md) | `abstract suspend fun getNextTemplateId(): `[`TemplateId`](../../com.myapp.chatmemo.domain.model.value/-template-id/index.md)<br>次の連番のテンプレートID取得 |
| [getSpinnerTemplateAll](get-spinner-template-all.md) | `abstract fun getSpinnerTemplateAll(): Flow<`[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`Template`](../../com.myapp.chatmemo.domain.model.entity/-template/index.md)`>>`<br>全てのテンプレート取得（選択肢スピナーに表示させる用） |
| [getTemplateAll](get-template-all.md) | `abstract fun getTemplateAll(): Flow<`[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`Template`](../../com.myapp.chatmemo.domain.model.entity/-template/index.md)`>>`<br>全てのテンプレート取得 |
| [getTemplateMessageById](get-template-message-by-id.md) | `abstract suspend fun getTemplateMessageById(templateId: `[`TemplateId`](../../com.myapp.chatmemo.domain.model.value/-template-id/index.md)`): `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`TemplateMessage`](../../com.myapp.chatmemo.domain.model.value/-template-message/index.md)`>`<br>テンプレートIDに紐づくテンプレートコメント取得 |
| [updateTemplate](update-template.md) | `abstract suspend fun updateTemplate(template: `[`Template`](../../com.myapp.chatmemo.domain.model.entity/-template/index.md)`): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>テンプレートアプデート |

### Inheritors

| Name | Summary |
|---|---|
| [TemplateUseCaseImp](../-template-use-case-imp/index.md) | `class TemplateUseCaseImp : `[`TemplateUseCase`](./index.md) |
