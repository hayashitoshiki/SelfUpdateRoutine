[document](../../index.md) / [com.myapp.chatmemo.di](../index.md) / [AppModule](./index.md)

# AppModule

`@Module abstract class AppModule`

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `AppModule()` |

### Functions

| Name | Summary |
|---|---|
| [bindChatUseCase](bind-chat-use-case.md) | `abstract fun bindChatUseCase(chatUseCaseImp: `[`ChatUseCaseImp`](../../com.myapp.chatmemo.domain.usecase/-chat-use-case-imp/index.md)`): `[`ChatUseCase`](../../com.myapp.chatmemo.domain.usecase/-chat-use-case/index.md) |
| [bindLocalChatRepository](bind-local-chat-repository.md) | `abstract fun bindLocalChatRepository(localChatRepositoryImp: `[`LocalChatRepositoryImp`](../../com.myapp.chatmemo.data.repository/-local-chat-repository-imp/index.md)`): `[`LocalChatRepository`](../../com.myapp.chatmemo.domain.repository/-local-chat-repository/index.md) |
| [bindLocalTemplateRepository](bind-local-template-repository.md) | `abstract fun bindLocalTemplateRepository(localTemplateRepositoryImp: `[`LocalTemplateRepositoryImp`](../../com.myapp.chatmemo.data.repository/-local-template-repository-imp/index.md)`): `[`LocalTemplateRepository`](../../com.myapp.chatmemo.domain.repository/-local-template-repository/index.md) |
| [bindTemplateUseCase](bind-template-use-case.md) | `abstract fun bindTemplateUseCase(templateUseCaseImp: `[`TemplateUseCaseImp`](../../com.myapp.chatmemo.domain.usecase/-template-use-case-imp/index.md)`): `[`TemplateUseCase`](../../com.myapp.chatmemo.domain.usecase/-template-use-case/index.md) |
