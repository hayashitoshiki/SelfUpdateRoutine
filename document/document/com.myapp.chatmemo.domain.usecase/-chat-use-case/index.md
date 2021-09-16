[document](../../index.md) / [com.myapp.chatmemo.domain.usecase](../index.md) / [ChatUseCase](./index.md)

# ChatUseCase

`interface ChatUseCase`

チャットルームに関するビジネスロジック

### Functions

| Name | Summary |
|---|---|
| [addComment](add-comment.md) | `abstract fun addComment(message: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, roomId: `[`RoomId`](../../com.myapp.chatmemo.domain.model.value/-room-id/index.md)`): `[`Comment`](../../com.myapp.chatmemo.domain.model.value/-comment/index.md)<br>コメント送信 |
| [addTemplateComment](add-template-comment.md) | `abstract fun addTemplateComment(templateConfiguration: `[`TemplateConfiguration`](../../com.myapp.chatmemo.domain.model.value/-template-configuration/index.md)`, roomId: `[`RoomId`](../../com.myapp.chatmemo.domain.model.value/-room-id/index.md)`): `[`Pair`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-pair/index.html)`<`[`TemplateConfiguration`](../../com.myapp.chatmemo.domain.model.value/-template-configuration/index.md)`, `[`Comment`](../../com.myapp.chatmemo.domain.model.value/-comment/index.md)`>`<br>テンプレートメッセージ送信 |
| [createRoom](create-room.md) | `abstract suspend fun createRoom(chatRoom: `[`ChatRoom`](../../com.myapp.chatmemo.domain.model.entity/-chat-room/index.md)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>チャットルーム作成 |
| [deleteRoom](delete-room.md) | `abstract suspend fun deleteRoom(roomId: `[`RoomId`](../../com.myapp.chatmemo.domain.model.value/-room-id/index.md)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>チャットルーム削除 |
| [getChatRoomByRoomById](get-chat-room-by-room-by-id.md) | `abstract fun getChatRoomByRoomById(roomId: `[`RoomId`](../../com.myapp.chatmemo.domain.model.value/-room-id/index.md)`): Flow<`[`ChatRoom`](../../com.myapp.chatmemo.domain.model.entity/-chat-room/index.md)`>`<br>RoomIDに紐づくチャットルーム取得 |
| [getNextRoomId](get-next-room-id.md) | `abstract suspend fun getNextRoomId(): `[`RoomId`](../../com.myapp.chatmemo.domain.model.value/-room-id/index.md)<br>次のチャットルームの連番取得 |
| [getRoomAll](get-room-all.md) | `abstract fun getRoomAll(): Flow<`[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`ChatRoom`](../../com.myapp.chatmemo.domain.model.entity/-chat-room/index.md)`>>`<br>全てのチャットルーム取得 |
| [reverseAllCommentUser](reverse-all-comment-user.md) | `abstract fun reverseAllCommentUser(commentList: `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`Comment`](../../com.myapp.chatmemo.domain.model.value/-comment/index.md)`>): `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`Comment`](../../com.myapp.chatmemo.domain.model.value/-comment/index.md)`>`<br>チャットルームのコメントを全て反転する |
| [updateRoom](update-room.md) | `abstract suspend fun updateRoom(chatRoom: `[`ChatRoom`](../../com.myapp.chatmemo.domain.model.entity/-chat-room/index.md)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>チャットルームアップデート |

### Inheritors

| Name | Summary |
|---|---|
| [ChatUseCaseImp](../-chat-use-case-imp/index.md) | `class ChatUseCaseImp : `[`ChatUseCase`](./index.md) |
