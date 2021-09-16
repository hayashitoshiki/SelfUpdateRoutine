[document](../../index.md) / [com.myapp.chatmemo.domain.model.value](../index.md) / [CommentDateTime](./index.md)

# CommentDateTime

`data class CommentDateTime`

コメントした日時

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `CommentDateTime(date: `[`LocalDateTime`](https://developer.android.com/reference/java/time/LocalDateTime.html)`)`<br>コメントした日時 |

### Properties

| Name | Summary |
|---|---|
| [date](date.md) | `val date: `[`LocalDateTime`](https://developer.android.com/reference/java/time/LocalDateTime.html) |

### Functions

| Name | Summary |
|---|---|
| [toMessageDate](to-message-date.md) | `fun toMessageDate(): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>メッセージリスト用の日時文字列を返す |
| [toSectionDate](to-section-date.md) | `fun toSectionDate(): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>セクション用の日時文字列を返す |
