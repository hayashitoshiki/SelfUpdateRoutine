[document](../../index.md) / [com.myapp.chatmemo.presentation.utils.expansion](../index.md) / [BaseViewModel](./index.md)

# BaseViewModel

`abstract class BaseViewModel : ViewModel`

BaseViewModel

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `BaseViewModel()`<br>BaseViewModel |

### Functions

| Name | Summary |
|---|---|
| [postValue](post-value.md) | `fun <T> `[`ViewModelLiveData`](../-view-model-live-data/index.md)`<`[`T`](post-value.md#T)`>.postValue(value: `[`T`](post-value.md#T)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [setValue](set-value.md) | `fun <T> `[`ViewModelLiveData`](../-view-model-live-data/index.md)`<`[`T`](set-value.md#T)`>.setValue(value: `[`T`](set-value.md#T)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |

### Inheritors

| Name | Summary |
|---|---|
| [RoomAddViewModel](../../com.myapp.chatmemo.presentation.chat/-room-add-view-model/index.md) | `class RoomAddViewModel : `[`BaseViewModel`](./index.md)<br>新規ルーム作成画面_ロジック |
| [RoomPhraseEditViewModel](../../com.myapp.chatmemo.presentation.chat/-room-phrase-edit-view-model/index.md) | `class RoomPhraseEditViewModel : `[`BaseViewModel`](./index.md)<br>ルームの定型文設定変更ダイアログ_ロジック |
| [RoomTitleEditViewModel](../../com.myapp.chatmemo.presentation.chat/-room-title-edit-view-model/index.md) | `class RoomTitleEditViewModel : `[`BaseViewModel`](./index.md)<br>ルーム名変更ダイアログ_ロジック |
| [TempalteAddViewModel](../../com.myapp.chatmemo.presentation.template/-tempalte-add-view-model/index.md) | `class TempalteAddViewModel : `[`BaseViewModel`](./index.md)<br>定型文作成画面_UIロジック |
| [TemplateListViewModel](../../com.myapp.chatmemo.presentation.template/-template-list-view-model/index.md) | `class TemplateListViewModel : `[`BaseViewModel`](./index.md)<br>定型文一覧画面_UIロジック |
