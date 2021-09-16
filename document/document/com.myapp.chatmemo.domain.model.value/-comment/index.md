[document](../../index.md) / [com.myapp.chatmemo.domain.model.value](../index.md) / [Comment](./index.md)

# Comment

`data class Comment : `[`Serializable`](https://developer.android.com/reference/java/io/Serializable.html)

チャットのコメント定義

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `Comment(message: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, user: `[`User`](../-user/index.md)`, time: `[`CommentDateTime`](../-comment-date-time/index.md)`)`<br>チャットのコメント定義 |

### Properties

| Name | Summary |
|---|---|
| [message](message.md) | `val message: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>コメント |
| [time](time.md) | `val time: `[`CommentDateTime`](../-comment-date-time/index.md)<br>コメントした時間 |
| [user](user.md) | `val user: `[`User`](../-user/index.md)<br>コメントしたユーザ |
