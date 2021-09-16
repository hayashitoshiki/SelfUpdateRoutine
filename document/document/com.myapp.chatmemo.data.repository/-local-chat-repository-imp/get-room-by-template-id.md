[document](../../index.md) / [com.myapp.chatmemo.data.repository](../index.md) / [LocalChatRepositoryImp](index.md) / [getRoomByTemplateId](./get-room-by-template-id.md)

# getRoomByTemplateId

`suspend fun getRoomByTemplateId(templateId: `[`TemplateId`](../../com.myapp.chatmemo.domain.model.value/-template-id/index.md)`): `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`ChatRoom`](../../com.myapp.chatmemo.domain.model.entity/-chat-room/index.md)`>`

Overrides [LocalChatRepository.getRoomByTemplateId](../../com.myapp.chatmemo.domain.repository/-local-chat-repository/get-room-by-template-id.md)

指定したテンプレートが使用されているルーム取得

### Parameters

`templateId` - テンプレートID

**Return**
指定したテンプレートが使用されているルーム

