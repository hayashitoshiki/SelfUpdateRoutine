[document](../../index.md) / [com.myapp.presentation.ui.diary](../index.md) / [WeatherResultViewModel](./index.md)

# WeatherResultViewModel

`class WeatherResultViewModel : ViewModel`

振り返り_天気比喩振り返り確認画面　画面ロジック

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `WeatherResultViewModel(reportUseCase: `[`ReportUseCase`](../../com.myapp.domain.usecase/-report-use-case/index.md)`)`<br>振り返り_天気比喩振り返り確認画面　画面ロジック |

### Properties

| Name | Summary |
|---|---|
| [assessmentInputText](assessment-input-text.md) | `val assessmentInputText: LiveData<`[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`>` |
| [improveInputText](improve-input-text.md) | `val improveInputText: LiveData<`[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`>` |
| [reasonInputText](reason-input-text.md) | `val reasonInputText: LiveData<`[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`>` |
| [saveState](save-state.md) | `val saveState: LiveData<`[`Status`](../../com.myapp.presentation.utils/-status/index.md)`<*>>` |

### Functions

| Name | Summary |
|---|---|
| [saveReport](save-report.md) | `fun saveReport(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
