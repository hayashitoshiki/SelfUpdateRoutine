[document](../../index.md) / [com.myapp.chatmemo.domain.model.entity](../index.md) / [ChatRoom](./index.md)

# ChatRoom

`data class ChatRoom : `[`Serializable`](https://developer.android.com/reference/java/io/Serializable.html)

チャットルーム定義

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `ChatRoom(roomId: `[`RoomId`](../../com.myapp.chatmemo.domain.model.value/-room-id/index.md)`, title: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, templateConfiguration: `[`TemplateConfiguration`](../../com.myapp.chatmemo.domain.model.value/-template-configuration/index.md)`?, commentList: `[`MutableList`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-list/index.html)`<`[`Comment`](../../com.myapp.chatmemo.domain.model.value/-comment/index.md)`>)`<br>チャットルーム定義 |

### Properties

| Name | Summary |
|---|---|
| [commentList](comment-list.md) | `var commentList: `[`MutableList`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-list/index.html)`<`[`Comment`](../../com.myapp.chatmemo.domain.model.value/-comment/index.md)`>`<br>過去のコメントリスト |
| [roomId](room-id.md) | `val roomId: `[`RoomId`](../../com.myapp.chatmemo.domain.model.value/-room-id/index.md)<br>チャットルームのID |
| [templateConfiguration](template-configuration.md) | `var templateConfiguration: `[`TemplateConfiguration`](../../com.myapp.chatmemo.domain.model.value/-template-configuration/index.md)`?`<br>テンプレート使用設定 |
| [title](title.md) | `var title: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>チャットルーム名 |
