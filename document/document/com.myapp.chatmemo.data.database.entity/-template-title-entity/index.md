[document](../../index.md) / [com.myapp.chatmemo.data.database.entity](../index.md) / [TemplateTitleEntity](./index.md)

# TemplateTitleEntity

`data class TemplateTitleEntity : `[`Serializable`](https://developer.android.com/reference/java/io/Serializable.html)

定型文タイトルテーブル

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `TemplateTitleEntity(id: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`?, title: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, createAt: `[`LocalDateTime`](https://developer.android.com/reference/java/time/LocalDateTime.html)` = getDateTimeNow(), updateAt: `[`LocalDateTime`](https://developer.android.com/reference/java/time/LocalDateTime.html)` = getDateTimeNow())`<br>定型文タイトルテーブル |

### Properties

| Name | Summary |
|---|---|
| [createAt](create-at.md) | `val createAt: `[`LocalDateTime`](https://developer.android.com/reference/java/time/LocalDateTime.html) |
| [id](id.md) | `val id: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`?`<br>プライマリーキー |
| [title](title.md) | `var title: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>定型文のタイトル |
| [updateAt](update-at.md) | `var updateAt: `[`LocalDateTime`](https://developer.android.com/reference/java/time/LocalDateTime.html) |

### Functions

| Name | Summary |
|---|---|
| [update](update.md) | `fun update(templateTitleEntity: `[`TemplateTitleEntity`](./index.md)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
