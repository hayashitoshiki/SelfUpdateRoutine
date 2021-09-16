[document](../../index.md) / [com.myapp.chatmemo.domain.model.entity](../index.md) / [Template](./index.md)

# Template

`data class Template : `[`Serializable`](https://developer.android.com/reference/java/io/Serializable.html)

テンプレート定義

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `Template(templateId: `[`TemplateId`](../../com.myapp.chatmemo.domain.model.value/-template-id/index.md)`, title: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, templateMessageList: `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`TemplateMessage`](../../com.myapp.chatmemo.domain.model.value/-template-message/index.md)`>)`<br>テンプレート定義 |

### Properties

| Name | Summary |
|---|---|
| [templateId](template-id.md) | `val templateId: `[`TemplateId`](../../com.myapp.chatmemo.domain.model.value/-template-id/index.md)<br>テンプレートID |
| [templateMessageList](template-message-list.md) | `var templateMessageList: `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`TemplateMessage`](../../com.myapp.chatmemo.domain.model.value/-template-message/index.md)`>`<br>テンプレートメッセージリスト |
| [title](title.md) | `var title: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>テンプレートタイトル |

### Extension Properties

| Name | Summary |
|---|---|
| [firsText](../../com.myapp.chatmemo.presentation.utils.expansion/firs-text.md) | `val `[`Template`](./index.md)`.firsText: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
