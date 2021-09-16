[document](../../index.md) / [com.myapp.presentation.utils](../index.md) / [BaseInputTextItemViewModel](./index.md)

# BaseInputTextItemViewModel

`abstract class BaseInputTextItemViewModel : ViewModel`

リサイクルビュー_テキスト入力用アイテム 画面ベースロジック

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `BaseInputTextItemViewModel()`<br>リサイクルビュー_テキスト入力用アイテム 画面ベースロジック |

### Properties

| Name | Summary |
|---|---|
| [_isMinusButtonEnable](_is-minus-button-enable.md) | `val _isMinusButtonEnable: MutableLiveData<`[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)`>` |
| [isMinusButtonEnable](is-minus-button-enable.md) | `val isMinusButtonEnable: LiveData<`[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)`>` |
| [isPlusButtonEnable](is-plus-button-enable.md) | `val isPlusButtonEnable: LiveData<`[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)`>` |
| [value](value.md) | `val value: MutableLiveData<`[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`>` |

### Inheritors

| Name | Summary |
|---|---|
| [ConstitutionInputItemViewModel](../../com.myapp.presentation.ui.mission_statement/-constitution-input-item-view-model/index.md) | `class ConstitutionInputItemViewModel : `[`BaseInputTextItemViewModel`](./index.md)<br>憲法アイテム_画面ロジック |
| [FuneralInputItemViewModel](../../com.myapp.presentation.ui.mission_statement/-funeral-input-item-view-model/index.md) | `class FuneralInputItemViewModel : `[`BaseInputTextItemViewModel`](./index.md)<br>理想の葬式アイテム_画面ロジック |
