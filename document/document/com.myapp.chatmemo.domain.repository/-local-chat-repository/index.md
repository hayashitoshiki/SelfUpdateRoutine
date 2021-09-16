[document](../../index.md) / [com.myapp.chatmemo.domain.repository](../index.md) / [LocalChatRepository](./index.md)

# LocalChatRepository

`interface LocalChatRepository`

チャットに関するローカルデータCRUD用Repository

### Functions

| Name | Summary |
|---|---|
| [addComment](add-comment.md) | `abstract suspend fun addComment(comment: `[`Comment`](../../com.myapp.chatmemo.domain.model.value/-comment/index.md)`, roomId: `[`RoomId`](../../com.myapp.chatmemo.domain.model.value/-room-id/index.md)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>メッセージ追加 |
| [createRoom](create-room.md) | `abstract suspend fun createRoom(chatRoom: `[`ChatRoom`](../../com.myapp.chatmemo.domain.model.entity/-chat-room/index.md)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>新規ルーム作成 |
| [deleteRoom](delete-room.md) | `abstract suspend fun deleteRoom(roomId: `[`RoomId`](../../com.myapp.chatmemo.domain.model.value/-room-id/index.md)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>チャットルーム削除 |
| [getNextRoomId](get-next-room-id.md) | `abstract suspend fun getNextRoomId(): `[`RoomId`](../../com.myapp.chatmemo.domain.model.value/-room-id/index.md)<br>次のID連番の値を返す |
| [getRoomAll](get-room-all.md) | `abstract fun getRoomAll(): Flow<`[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`ChatRoom`](../../com.myapp.chatmemo.domain.model.entity/-chat-room/index.md)`>>`<br>チャットルームリスト取得 |
| [getRoomById](get-room-by-id.md) | `abstract fun getRoomById(roomId: `[`RoomId`](../../com.myapp.chatmemo.domain.model.value/-room-id/index.md)`): Flow<`[`ChatRoom`](../../com.myapp.chatmemo.domain.model.entity/-chat-room/index.md)`>`<br>RoomIdに一致するルーム取得 |
| [getRoomByTemplateId](get-room-by-template-id.md) | `abstract suspend fun getRoomByTemplateId(templateId: `[`TemplateId`](../../com.myapp.chatmemo.domain.model.value/-template-id/index.md)`): `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`ChatRoom`](../../com.myapp.chatmemo.domain.model.entity/-chat-room/index.md)`>`<br>指定したテンプレートが使用されているルーム取得 |
| [updateComments](update-comments.md) | `abstract suspend fun updateComments(commentList: `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`Comment`](../../com.myapp.chatmemo.domain.model.value/-comment/index.md)`>): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>メッセージアップデート |
| [updateRoom](update-room.md) | `abstract suspend fun updateRoom(chatRoom: `[`ChatRoom`](../../com.myapp.chatmemo.domain.model.entity/-chat-room/index.md)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>チャットルーム更新 |

### Inheritors

| Name | Summary |
|---|---|
| [LocalChatRepositoryImp](../../com.myapp.chatmemo.data.repository/-local-chat-repository-imp/index.md) | `class LocalChatRepositoryImp : `[`LocalChatRepository`](./index.md) |
