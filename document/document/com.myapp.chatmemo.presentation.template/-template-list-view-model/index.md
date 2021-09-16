[document](../../index.md) / [com.myapp.chatmemo.presentation.template](../index.md) / [TemplateListViewModel](./index.md)

# TemplateListViewModel

`class TemplateListViewModel : `[`BaseViewModel`](../../com.myapp.chatmemo.presentation.utils.expansion/-base-view-model/index.md)

定型文一覧画面_UIロジック

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `TemplateListViewModel(templateUseCase: `[`TemplateUseCase`](../../com.myapp.chatmemo.domain.usecase/-template-use-case/index.md)`)`<br>定型文一覧画面_UIロジック |

### Properties

| Name | Summary |
|---|---|
| [isNoDataText](is-no-data-text.md) | `val isNoDataText: LiveData<`[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)`>` |
| [status](status.md) | `val status: MutableLiveData<`[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)`?>` |
| [templateList](template-list.md) | `val templateList: LiveData<`[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`Template`](../../com.myapp.chatmemo.domain.model.entity/-template/index.md)`>>` |

### Functions

| Name | Summary |
|---|---|
| [deletePhrase](delete-phrase.md) | `fun deletePhrase(template: `[`Template`](../../com.myapp.chatmemo.domain.model.entity/-template/index.md)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |

### Inherited Functions

| Name | Summary |
|---|---|
| [postValue](../../com.myapp.chatmemo.presentation.utils.expansion/-base-view-model/post-value.md) | `fun <T> `[`ViewModelLiveData`](../../com.myapp.chatmemo.presentation.utils.expansion/-view-model-live-data/index.md)`<`[`T`](../../com.myapp.chatmemo.presentation.utils.expansion/-base-view-model/post-value.md#T)`>.postValue(value: `[`T`](../../com.myapp.chatmemo.presentation.utils.expansion/-base-view-model/post-value.md#T)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [setValue](../../com.myapp.chatmemo.presentation.utils.expansion/-base-view-model/set-value.md) | `fun <T> `[`ViewModelLiveData`](../../com.myapp.chatmemo.presentation.utils.expansion/-view-model-live-data/index.md)`<`[`T`](../../com.myapp.chatmemo.presentation.utils.expansion/-base-view-model/set-value.md#T)`>.setValue(value: `[`T`](../../com.myapp.chatmemo.presentation.utils.expansion/-base-view-model/set-value.md#T)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
