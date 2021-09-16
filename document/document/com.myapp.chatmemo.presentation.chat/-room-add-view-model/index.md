[document](../../index.md) / [com.myapp.chatmemo.presentation.chat](../index.md) / [RoomAddViewModel](./index.md)

# RoomAddViewModel

`class RoomAddViewModel : `[`BaseViewModel`](../../com.myapp.chatmemo.presentation.utils.expansion/-base-view-model/index.md)

新規ルーム作成画面_ロジック

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `RoomAddViewModel(templateUseCase: `[`TemplateUseCase`](../../com.myapp.chatmemo.domain.usecase/-template-use-case/index.md)`, chatUseCase: `[`ChatUseCase`](../../com.myapp.chatmemo.domain.usecase/-chat-use-case/index.md)`)`<br>新規ルーム作成画面_ロジック |

### Properties

| Name | Summary |
|---|---|
| [isEnableSubmitButton](is-enable-submit-button.md) | `val isEnableSubmitButton: LiveData<`[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)`>` |
| [isEnableTemplateMode](is-enable-template-mode.md) | `val isEnableTemplateMode: LiveData<`[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)`>` |
| [templateModeList](template-mode-list.md) | `val templateModeList: `[`ViewModelLiveData`](../../com.myapp.chatmemo.presentation.utils.expansion/-view-model-live-data/index.md)`<`[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`TemplateMode`](../../com.myapp.chatmemo.domain.model.value/-template-mode/index.md)`>>` |
| [templateModeValue](template-mode-value.md) | `val templateModeValue: MediatorLiveData<`[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`>` |
| [templateTitleList](template-title-list.md) | `val templateTitleList: LiveData<`[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`Template`](../../com.myapp.chatmemo.domain.model.entity/-template/index.md)`>>` |
| [templateTitleValue](template-title-value.md) | `val templateTitleValue: MutableLiveData<`[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`>` |
| [titleText](title-text.md) | `val titleText: MutableLiveData<`[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`>` |

### Functions

| Name | Summary |
|---|---|
| [changeSubmitButton](change-submit-button.md) | `fun changeSubmitButton(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [createRoom](create-room.md) | `suspend fun createRoom(): `[`ChatRoom`](../../com.myapp.chatmemo.domain.model.entity/-chat-room/index.md) |

### Inherited Functions

| Name | Summary |
|---|---|
| [postValue](../../com.myapp.chatmemo.presentation.utils.expansion/-base-view-model/post-value.md) | `fun <T> `[`ViewModelLiveData`](../../com.myapp.chatmemo.presentation.utils.expansion/-view-model-live-data/index.md)`<`[`T`](../../com.myapp.chatmemo.presentation.utils.expansion/-base-view-model/post-value.md#T)`>.postValue(value: `[`T`](../../com.myapp.chatmemo.presentation.utils.expansion/-base-view-model/post-value.md#T)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [setValue](../../com.myapp.chatmemo.presentation.utils.expansion/-base-view-model/set-value.md) | `fun <T> `[`ViewModelLiveData`](../../com.myapp.chatmemo.presentation.utils.expansion/-view-model-live-data/index.md)`<`[`T`](../../com.myapp.chatmemo.presentation.utils.expansion/-base-view-model/set-value.md#T)`>.setValue(value: `[`T`](../../com.myapp.chatmemo.presentation.utils.expansion/-base-view-model/set-value.md#T)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
