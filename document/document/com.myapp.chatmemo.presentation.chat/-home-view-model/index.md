[document](../../index.md) / [com.myapp.chatmemo.presentation.chat](../index.md) / [HomeViewModel](./index.md)

# HomeViewModel

`class HomeViewModel : ViewModel`

ホーム画面_ロジック

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `HomeViewModel(chatUseCase: `[`ChatUseCase`](../../com.myapp.chatmemo.domain.usecase/-chat-use-case/index.md)`)`<br>ホーム画面_ロジック |

### Properties

| Name | Summary |
|---|---|
| [chatRoomEntityList](chat-room-entity-list.md) | `val chatRoomEntityList: LiveData<`[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`ChatRoom`](../../com.myapp.chatmemo.domain.model.entity/-chat-room/index.md)`>>` |
| [isNoDataText](is-no-data-text.md) | `val isNoDataText: LiveData<`[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)`>` |

### Functions

| Name | Summary |
|---|---|
| [deleteRoom](delete-room.md) | `fun deleteRoom(roomId: `[`RoomId`](../../com.myapp.chatmemo.domain.model.value/-room-id/index.md)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
