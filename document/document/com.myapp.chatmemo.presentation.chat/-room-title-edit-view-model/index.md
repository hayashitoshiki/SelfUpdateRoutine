[document](../../index.md) / [com.myapp.chatmemo.presentation.chat](../index.md) / [RoomTitleEditViewModel](./index.md)

# RoomTitleEditViewModel

`class RoomTitleEditViewModel : `[`BaseViewModel`](../../com.myapp.chatmemo.presentation.utils.expansion/-base-view-model/index.md)

ルーム名変更ダイアログ_ロジック

### Types

| Name | Summary |
|---|---|
| [RoomTitleEditViewModelAssistedFactory](-room-title-edit-view-model-assisted-factory/index.md) | `interface RoomTitleEditViewModelAssistedFactory` |

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `RoomTitleEditViewModel(chatRoomUseCase: `[`ChatUseCase`](../../com.myapp.chatmemo.domain.usecase/-chat-use-case/index.md)`, chatRoomEntity: `[`ChatRoom`](../../com.myapp.chatmemo.domain.model.entity/-chat-room/index.md)`)`<br>ルーム名変更ダイアログ_ロジック |

### Properties

| Name | Summary |
|---|---|
| [isEnableSubmitButton](is-enable-submit-button.md) | `val isEnableSubmitButton: LiveData<`[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)`>` |
| [newRoomTitle](new-room-title.md) | `val newRoomTitle: MutableLiveData<`[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`>` |
| [oldRoomTitle](old-room-title.md) | `val oldRoomTitle: LiveData<`[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`>` |

### Functions

| Name | Summary |
|---|---|
| [changeRoomName](change-room-name.md) | `suspend fun changeRoomName(roomName: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |

### Inherited Functions

| Name | Summary |
|---|---|
| [postValue](../../com.myapp.chatmemo.presentation.utils.expansion/-base-view-model/post-value.md) | `fun <T> `[`ViewModelLiveData`](../../com.myapp.chatmemo.presentation.utils.expansion/-view-model-live-data/index.md)`<`[`T`](../../com.myapp.chatmemo.presentation.utils.expansion/-base-view-model/post-value.md#T)`>.postValue(value: `[`T`](../../com.myapp.chatmemo.presentation.utils.expansion/-base-view-model/post-value.md#T)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [setValue](../../com.myapp.chatmemo.presentation.utils.expansion/-base-view-model/set-value.md) | `fun <T> `[`ViewModelLiveData`](../../com.myapp.chatmemo.presentation.utils.expansion/-view-model-live-data/index.md)`<`[`T`](../../com.myapp.chatmemo.presentation.utils.expansion/-base-view-model/set-value.md#T)`>.setValue(value: `[`T`](../../com.myapp.chatmemo.presentation.utils.expansion/-base-view-model/set-value.md#T)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |

### Companion Object Functions

| Name | Summary |
|---|---|
| [provideFactory](provide-factory.md) | `fun provideFactory(assistedFactory: `[`RoomTitleEditViewModelAssistedFactory`](-room-title-edit-view-model-assisted-factory/index.md)`, chatRoomEntity: `[`ChatRoom`](../../com.myapp.chatmemo.domain.model.entity/-chat-room/index.md)`): Factory` |
