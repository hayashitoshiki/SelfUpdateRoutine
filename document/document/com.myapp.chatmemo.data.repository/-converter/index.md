[document](../../index.md) / [com.myapp.chatmemo.data.repository](../index.md) / [Converter](./index.md)

# Converter

`object Converter`

### Functions

| Name | Summary |
|---|---|
| [chatEntityFromChat](chat-entity-from-chat.md) | `fun chatEntityFromChat(chatRoom: `[`ChatRoom`](../../com.myapp.chatmemo.domain.model.entity/-chat-room/index.md)`): `[`ChatRoomEntity`](../../com.myapp.chatmemo.data.database.entity/-chat-room-entity/index.md) |
| [chatFromBy](chat-from-by.md) | `fun chatFromBy(chatRoomEntity: `[`ChatRoomEntity`](../../com.myapp.chatmemo.data.database.entity/-chat-room-entity/index.md)`, commentEntityList: `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`CommentEntity`](../../com.myapp.chatmemo.data.database.entity/-comment-entity/index.md)`>, templateTitleEntity: `[`TemplateTitleEntity`](../../com.myapp.chatmemo.data.database.entity/-template-title-entity/index.md)`?, templateMessageList: `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`TemplateMessageEntity`](../../com.myapp.chatmemo.data.database.entity/-template-message-entity/index.md)`>?): `[`ChatRoom`](../../com.myapp.chatmemo.domain.model.entity/-chat-room/index.md) |
| [commentEntityFromComment](comment-entity-from-comment.md) | `fun commentEntityFromComment(comment: `[`Comment`](../../com.myapp.chatmemo.domain.model.value/-comment/index.md)`, roomId: `[`RoomId`](../../com.myapp.chatmemo.domain.model.value/-room-id/index.md)`): `[`CommentEntity`](../../com.myapp.chatmemo.data.database.entity/-comment-entity/index.md) |
| [commentFromCommentEntity](comment-from-comment-entity.md) | `fun commentFromCommentEntity(commentEntity: `[`CommentEntity`](../../com.myapp.chatmemo.data.database.entity/-comment-entity/index.md)`): `[`Comment`](../../com.myapp.chatmemo.domain.model.value/-comment/index.md) |
| [templateEntityFromTemplate](template-entity-from-template.md) | `fun templateEntityFromTemplate(template: `[`Template`](../../com.myapp.chatmemo.domain.model.entity/-template/index.md)`): `[`TemplateTitleEntity`](../../com.myapp.chatmemo.data.database.entity/-template-title-entity/index.md) |
| [templateFromTemplateEntityAndPhraseEntity](template-from-template-entity-and-phrase-entity.md) | `fun templateFromTemplateEntityAndPhraseEntity(templateTitleEntity: `[`TemplateTitleEntity`](../../com.myapp.chatmemo.data.database.entity/-template-title-entity/index.md)`, templateMessageEntityList: `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`TemplateMessageEntity`](../../com.myapp.chatmemo.data.database.entity/-template-message-entity/index.md)`>): `[`Template`](../../com.myapp.chatmemo.domain.model.entity/-template/index.md) |
| [templateMessageEntityFromTemplateAndMessage](template-message-entity-from-template-and-message.md) | `fun templateMessageEntityFromTemplateAndMessage(template: `[`Template`](../../com.myapp.chatmemo.domain.model.entity/-template/index.md)`, message: `[`TemplateMessage`](../../com.myapp.chatmemo.domain.model.value/-template-message/index.md)`): `[`TemplateMessageEntity`](../../com.myapp.chatmemo.data.database.entity/-template-message-entity/index.md) |
| [templateMessageFromPharaseEntity](template-message-from-pharase-entity.md) | `fun templateMessageFromPharaseEntity(templateMessage: `[`TemplateMessageEntity`](../../com.myapp.chatmemo.data.database.entity/-template-message-entity/index.md)`): `[`TemplateMessage`](../../com.myapp.chatmemo.domain.model.value/-template-message/index.md) |
