[document](../../index.md) / [com.myapp.presentation.ui.diary](../index.md) / [DiaryBaseViewModel](./index.md)

# DiaryBaseViewModel

`abstract class DiaryBaseViewModel : ViewModel`

振り返り画面 BaseViewModel

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `DiaryBaseViewModel()`<br>振り返り画面 BaseViewModel |

### Properties

| Name | Summary |
|---|---|
| [inputText](input-text.md) | `val inputText: MutableLiveData<`[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`>` |
| [isButtonEnable](is-button-enable.md) | `val isButtonEnable: MediatorLiveData<`[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)`>` |

### Inheritors

| Name | Summary |
|---|---|
| [FfsFactViewModel](../-ffs-fact-view-model/index.md) | `class FfsFactViewModel : `[`DiaryBaseViewModel`](./index.md)<br>振り返り_事実画面 画面ロジック |
| [FfsFindViewModel](../-ffs-find-view-model/index.md) | `class FfsFindViewModel : `[`DiaryBaseViewModel`](./index.md)<br>振り返り_発見画面 画面ロジック |
| [FfsLearnViewModel](../-ffs-learn-view-model/index.md) | `class FfsLearnViewModel : `[`DiaryBaseViewModel`](./index.md)<br>振り返り_教訓画面 画面ロジック |
| [FfsStatementViewModel](../-ffs-statement-view-model/index.md) | `class FfsStatementViewModel : `[`DiaryBaseViewModel`](./index.md)<br>振り返り_宣言画面 画面ロジック |
| [WeatherAssessmentViewModel](../-weather-assessment-view-model/index.md) | `class WeatherAssessmentViewModel : `[`DiaryBaseViewModel`](./index.md)<br>振り返り_評価画面 画面ロジック |
| [WeatherImproveViewModel](../-weather-improve-view-model/index.md) | `class WeatherImproveViewModel : `[`DiaryBaseViewModel`](./index.md)<br>振り返り_改善案画面 画面ロジック |
| [WeatherReasonViewModel](../-weather-reason-view-model/index.md) | `class WeatherReasonViewModel : `[`DiaryBaseViewModel`](./index.md)<br>振り返り_理由画面 画面ロジック |
