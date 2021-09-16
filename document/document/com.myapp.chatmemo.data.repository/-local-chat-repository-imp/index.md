[document](../../index.md) / [com.myapp.chatmemo.data.repository](../index.md) / [LocalChatRepositoryImp](./index.md)

# LocalChatRepositoryImp

`class LocalChatRepositoryImp : `[`LocalChatRepository`](../../com.myapp.chatmemo.domain.repository/-local-chat-repository/index.md)

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `LocalChatRepositoryImp(roomDao: `[`RoomDao`](../../com.myapp.chatmemo.data.database.dao/-room-dao/index.md)`, commentDao: `[`CommentDao`](../../com.myapp.chatmemo.data.database.dao/-comment-dao/index.md)`, templateDao: `[`TemplateDao`](../../com.myapp.chatmemo.data.database.dao/-template-dao/index.md)`, phraseDao: `[`PhraseDao`](../../com.myapp.chatmemo.data.database.dao/-phrase-dao/index.md)`, ioDispatcher: CoroutineDispatcher = Dispatchers.IO)` |

### Functions

| Name | Summary |
|---|---|
| [addComment](add-comment.md) | `suspend fun addComment(comment: `[`Comment`](../../com.myapp.chatmemo.domain.model.value/-comment/index.md)`, roomId: `[`RoomId`](../../com.myapp.chatmemo.domain.model.value/-room-id/index.md)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>メッセージ追加 |
| [createRoom](create-room.md) | `suspend fun createRoom(chatRoom: `[`ChatRoom`](../../com.myapp.chatmemo.domain.model.entity/-chat-room/index.md)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>新規ルーム作成 |
| [deleteRoom](delete-room.md) | `suspend fun deleteRoom(roomId: `[`RoomId`](../../com.myapp.chatmemo.domain.model.value/-room-id/index.md)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>チャットルーム削除 |
| [getNextRoomId](get-next-room-id.md) | `suspend fun getNextRoomId(): `[`RoomId`](../../com.myapp.chatmemo.domain.model.value/-room-id/index.md)<br>次のID連番の値を返す |
| [getRoomAll](get-room-all.md) | `fun getRoomAll(): Flow<`[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`ChatRoom`](../../com.myapp.chatmemo.domain.model.entity/-chat-room/index.md)`>>`<br>チャットルームリスト取得 |
| [getRoomById](get-room-by-id.md) | `fun getRoomById(roomId: `[`RoomId`](../../com.myapp.chatmemo.domain.model.value/-room-id/index.md)`): Flow<`[`ChatRoom`](../../com.myapp.chatmemo.domain.model.entity/-chat-room/index.md)`>`<br>RoomIdに一致するルーム取得 |
| [getRoomByTemplateId](get-room-by-template-id.md) | `suspend fun getRoomByTemplateId(templateId: `[`TemplateId`](../../com.myapp.chatmemo.domain.model.value/-template-id/index.md)`): `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`ChatRoom`](../../com.myapp.chatmemo.domain.model.entity/-chat-room/index.md)`>`<br>指定したテンプレートが使用されているルーム取得 |
| [updateComments](update-comments.md) | `suspend fun updateComments(commentList: `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`Comment`](../../com.myapp.chatmemo.domain.model.value/-comment/index.md)`>): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>メッセージアップデート |
| [updateRoom](update-room.md) | `suspend fun updateRoom(chatRoom: `[`ChatRoom`](../../com.myapp.chatmemo.domain.model.entity/-chat-room/index.md)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>チャットルーム更新 |
