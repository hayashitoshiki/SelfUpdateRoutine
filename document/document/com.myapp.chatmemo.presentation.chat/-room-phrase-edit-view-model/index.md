[document](../../index.md) / [com.myapp.chatmemo.presentation.chat](../index.md) / [RoomPhraseEditViewModel](./index.md)

# RoomPhraseEditViewModel

`class RoomPhraseEditViewModel : `[`BaseViewModel`](../../com.myapp.chatmemo.presentation.utils.expansion/-base-view-model/index.md)

ルームの定型文設定変更ダイアログ_ロジック

### Types

| Name | Summary |
|---|---|
| [RoomPhraseEditViewModelAssistedFactory](-room-phrase-edit-view-model-assisted-factory/index.md) | `interface RoomPhraseEditViewModelAssistedFactory` |

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `RoomPhraseEditViewModel(chatRoom: `[`ChatRoom`](../../com.myapp.chatmemo.domain.model.entity/-chat-room/index.md)`, templateUseCase: `[`TemplateUseCase`](../../com.myapp.chatmemo.domain.usecase/-template-use-case/index.md)`, chatUseCase: `[`ChatUseCase`](../../com.myapp.chatmemo.domain.usecase/-chat-use-case/index.md)`)`<br>ルームの定型文設定変更ダイアログ_ロジック |

### Properties

| Name | Summary |
|---|---|
| [isEnableSubmitButton](is-enable-submit-button.md) | `val isEnableSubmitButton: LiveData<`[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)`>` |
| [isEnableTemplateMode](is-enable-template-mode.md) | `val isEnableTemplateMode: LiveData<`[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)`>` |
| [templateModeList](template-mode-list.md) | `val templateModeList: `[`ViewModelLiveData`](../../com.myapp.chatmemo.presentation.utils.expansion/-view-model-live-data/index.md)`<`[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`TemplateMode`](../../com.myapp.chatmemo.domain.model.value/-template-mode/index.md)`>>` |
| [templateModeValue](template-mode-value.md) | `val templateModeValue: MediatorLiveData<`[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`>` |
| [templateTitleList](template-title-list.md) | `val templateTitleList: <ERROR CLASS>` |
| [templateTitleValue](template-title-value.md) | `val templateTitleValue: MutableLiveData<`[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`>` |

### Functions

| Name | Summary |
|---|---|
| [submit](submit.md) | `suspend fun submit(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |

### Inherited Functions

| Name | Summary |
|---|---|
| [postValue](../../com.myapp.chatmemo.presentation.utils.expansion/-base-view-model/post-value.md) | `fun <T> `[`ViewModelLiveData`](../../com.myapp.chatmemo.presentation.utils.expansion/-view-model-live-data/index.md)`<`[`T`](../../com.myapp.chatmemo.presentation.utils.expansion/-base-view-model/post-value.md#T)`>.postValue(value: `[`T`](../../com.myapp.chatmemo.presentation.utils.expansion/-base-view-model/post-value.md#T)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [setValue](../../com.myapp.chatmemo.presentation.utils.expansion/-base-view-model/set-value.md) | `fun <T> `[`ViewModelLiveData`](../../com.myapp.chatmemo.presentation.utils.expansion/-view-model-live-data/index.md)`<`[`T`](../../com.myapp.chatmemo.presentation.utils.expansion/-base-view-model/set-value.md#T)`>.setValue(value: `[`T`](../../com.myapp.chatmemo.presentation.utils.expansion/-base-view-model/set-value.md#T)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |

### Companion Object Functions

| Name | Summary |
|---|---|
| [provideFactory](provide-factory.md) | `fun provideFactory(owner: SavedStateRegistryOwner, assistedFactory: `[`RoomPhraseEditViewModelAssistedFactory`](-room-phrase-edit-view-model-assisted-factory/index.md)`, chatRoomEntity: `[`ChatRoom`](../../com.myapp.chatmemo.domain.model.entity/-chat-room/index.md)`): Factory` |
