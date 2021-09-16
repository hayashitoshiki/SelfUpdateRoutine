[document](../../index.md) / [com.myapp.chatmemo.data.database.entity](../index.md) / [CommentEntity](./index.md)

# CommentEntity

`data class CommentEntity : `[`Serializable`](https://developer.android.com/reference/java/io/Serializable.html)

コメントテーブル

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `CommentEntity(id: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`?, text: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, user: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`, commentTime: `[`LocalDateTime`](https://developer.android.com/reference/java/time/LocalDateTime.html)`, roomId: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`, createAt: `[`LocalDateTime`](https://developer.android.com/reference/java/time/LocalDateTime.html)` = getDateTimeNow(), updateAt: `[`LocalDateTime`](https://developer.android.com/reference/java/time/LocalDateTime.html)` = getDateTimeNow())`<br>コメントテーブル |

### Properties

| Name | Summary |
|---|---|
| [commentTime](comment-time.md) | `val commentTime: `[`LocalDateTime`](https://developer.android.com/reference/java/time/LocalDateTime.html) |
| [createAt](create-at.md) | `val createAt: `[`LocalDateTime`](https://developer.android.com/reference/java/time/LocalDateTime.html) |
| [id](id.md) | `val id: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`?`<br>プライマリーキー |
| [roomId](room-id.md) | `val roomId: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)<br>紐づいているルームID |
| [text](text.md) | `var text: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>コメント |
| [updateAt](update-at.md) | `var updateAt: `[`LocalDateTime`](https://developer.android.com/reference/java/time/LocalDateTime.html) |
| [user](user.md) | `var user: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)<br>ユーザー |

### Functions

| Name | Summary |
|---|---|
| [update](update.md) | `fun update(commentEntity: `[`CommentEntity`](./index.md)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
