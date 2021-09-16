[document](../../index.md) / [com.myapp.chatmemo.data.database.dao](../index.md) / [RoomDao](./index.md)

# RoomDao

`interface RoomDao`

定型文用クエリ管理

### Functions

| Name | Summary |
|---|---|
| [deleteById](delete-by-id.md) | `abstract suspend fun deleteById(id: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [getAll](get-all.md) | `abstract fun getAll(): Flow<`[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`ChatRoomEntity`](../../com.myapp.chatmemo.data.database.entity/-chat-room-entity/index.md)`>>` |
| [getNextId](get-next-id.md) | `abstract suspend fun getNextId(): `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`?` |
| [getRoomById](get-room-by-id.md) | `abstract fun getRoomById(id: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`): Flow<`[`ChatRoomEntity`](../../com.myapp.chatmemo.data.database.entity/-chat-room-entity/index.md)`>` |
| [getRoomByTemplateId](get-room-by-template-id.md) | `abstract suspend fun getRoomByTemplateId(templateId: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`): `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`ChatRoomEntity`](../../com.myapp.chatmemo.data.database.entity/-chat-room-entity/index.md)`>` |
| [insert](insert.md) | `abstract suspend fun insert(chatRoomEntity: `[`ChatRoomEntity`](../../com.myapp.chatmemo.data.database.entity/-chat-room-entity/index.md)`): `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html) |
| [update](update.md) | `abstract suspend fun update(chatRoomEntity: `[`ChatRoomEntity`](../../com.myapp.chatmemo.data.database.entity/-chat-room-entity/index.md)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
