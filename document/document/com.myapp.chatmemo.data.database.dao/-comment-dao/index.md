[document](../../index.md) / [com.myapp.chatmemo.data.database.dao](../index.md) / [CommentDao](./index.md)

# CommentDao

`interface CommentDao`

コメント用クエリ管理

### Functions

| Name | Summary |
|---|---|
| [deleteById](delete-by-id.md) | `abstract suspend fun deleteById(roomId: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [getAllCommentByRoom](get-all-comment-by-room.md) | `abstract suspend fun getAllCommentByRoom(roomId: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`): `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`CommentEntity`](../../com.myapp.chatmemo.data.database.entity/-comment-entity/index.md)`>` |
| [getCommentByDate](get-comment-by-date.md) | `abstract suspend fun getCommentByDate(commentTim: `[`LocalDateTime`](https://developer.android.com/reference/java/time/LocalDateTime.html)`): `[`CommentEntity`](../../com.myapp.chatmemo.data.database.entity/-comment-entity/index.md) |
| [insert](insert.md) | `abstract suspend fun insert(comment: `[`CommentEntity`](../../com.myapp.chatmemo.data.database.entity/-comment-entity/index.md)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [update](update.md) | `abstract suspend fun update(comment: `[`CommentEntity`](../../com.myapp.chatmemo.data.database.entity/-comment-entity/index.md)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
