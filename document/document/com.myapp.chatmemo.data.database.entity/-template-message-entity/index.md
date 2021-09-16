[document](../../index.md) / [com.myapp.chatmemo.data.database.entity](../index.md) / [TemplateMessageEntity](./index.md)

# TemplateMessageEntity

`data class TemplateMessageEntity`

定型文テーブル

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `TemplateMessageEntity(id: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`?, text: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, templateId: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`, createAt: `[`LocalDateTime`](https://developer.android.com/reference/java/time/LocalDateTime.html)` = getDateTimeNow(), updateAt: `[`LocalDateTime`](https://developer.android.com/reference/java/time/LocalDateTime.html)` = getDateTimeNow())`<br>定型文テーブル |

### Properties

| Name | Summary |
|---|---|
| [createAt](create-at.md) | `val createAt: `[`LocalDateTime`](https://developer.android.com/reference/java/time/LocalDateTime.html) |
| [id](id.md) | `val id: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`?`<br>プライマリーキー |
| [templateId](template-id.md) | `var templateId: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)<br>定型文タイトルのId |
| [text](text.md) | `var text: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>定型文 |
| [updateAt](update-at.md) | `var updateAt: `[`LocalDateTime`](https://developer.android.com/reference/java/time/LocalDateTime.html) |
