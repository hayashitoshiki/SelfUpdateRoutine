[document](../../index.md) / [com.myapp.chatmemo.presentation.chat](../index.md) / [ChatViewModel](./index.md)

# ChatViewModel

`class ChatViewModel : ViewModel`

チャット画面_UIロジック

### Types

| Name | Summary |
|---|---|
| [ViewModelAssistedFactory](-view-model-assisted-factory/index.md) | `interface ViewModelAssistedFactory` |

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `ChatViewModel(chatUseCase: `[`ChatUseCase`](../../com.myapp.chatmemo.domain.usecase/-chat-use-case/index.md)`, id: `[`RoomId`](../../com.myapp.chatmemo.domain.model.value/-room-id/index.md)`)`<br>チャット画面_UIロジック |

### Properties

| Name | Summary |
|---|---|
| [chatRoom](chat-room.md) | `val chatRoom: <ERROR CLASS>` |
| [commentList](comment-list.md) | `val commentList: LiveData<`[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`Comment`](../../com.myapp.chatmemo.domain.model.value/-comment/index.md)`>>` |
| [commentText](comment-text.md) | `val commentText: MutableLiveData<`[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`>` |
| [isEnableSubmitButton](is-enable-submit-button.md) | `val isEnableSubmitButton: LiveData<`[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)`>` |

### Functions

| Name | Summary |
|---|---|
| [changeUser](change-user.md) | `fun changeUser(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [deleteRoom](delete-room.md) | `fun deleteRoom(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [submit](submit.md) | `fun submit(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |

### Companion Object Functions

| Name | Summary |
|---|---|
| [provideFactory](provide-factory.md) | `fun provideFactory(assistedFactory: `[`ViewModelAssistedFactory`](-view-model-assisted-factory/index.md)`, id: `[`RoomId`](../../com.myapp.chatmemo.domain.model.value/-room-id/index.md)`): Factory` |
