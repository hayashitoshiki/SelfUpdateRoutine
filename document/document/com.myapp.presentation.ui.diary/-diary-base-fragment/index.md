[document](../../index.md) / [com.myapp.presentation.ui.diary](../index.md) / [DiaryBaseFragment](./index.md)

# DiaryBaseFragment

`abstract class DiaryBaseFragment : `[`BaseFragment`](../../com.myapp.presentation.utils/-base-fragment/index.md)

振り返り画面_テキスト入力系画面 BaseFragment

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `DiaryBaseFragment()`<br>振り返り画面_テキスト入力系画面 BaseFragment |

### Properties

| Name | Summary |
|---|---|
| [binding](binding.md) | `val binding: <ERROR CLASS>` |
| [viewModel](view-model.md) | `abstract val viewModel: `[`DiaryBaseViewModel`](../-diary-base-view-model/index.md) |

### Functions

| Name | Summary |
|---|---|
| [onCreateView](on-create-view.md) | `open fun onCreateView(inflater: `[`LayoutInflater`](https://developer.android.com/reference/android/view/LayoutInflater.html)`, container: `[`ViewGroup`](https://developer.android.com/reference/android/view/ViewGroup.html)`?, savedInstanceState: `[`Bundle`](https://developer.android.com/reference/android/os/Bundle.html)`?): `[`View`](https://developer.android.com/reference/android/view/View.html) |
| [onDestroyView](on-destroy-view.md) | `open fun onDestroyView(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [onViewCreated](on-view-created.md) | `open fun onViewCreated(view: `[`View`](https://developer.android.com/reference/android/view/View.html)`, savedInstanceState: `[`Bundle`](https://developer.android.com/reference/android/os/Bundle.html)`?): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |

### Inherited Functions

| Name | Summary |
|---|---|
| [onAttach](../../com.myapp.presentation.utils/-base-fragment/on-attach.md) | `open fun onAttach(context: `[`Context`](https://developer.android.com/reference/android/content/Context.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [onCreate](../../com.myapp.presentation.utils/-base-fragment/on-create.md) | `open fun onCreate(savedInstanceState: `[`Bundle`](https://developer.android.com/reference/android/os/Bundle.html)`?): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [onDestroy](../../com.myapp.presentation.utils/-base-fragment/on-destroy.md) | `open fun onDestroy(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [onDetach](../../com.myapp.presentation.utils/-base-fragment/on-detach.md) | `open fun onDetach(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [onPause](../../com.myapp.presentation.utils/-base-fragment/on-pause.md) | `open fun onPause(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [onResume](../../com.myapp.presentation.utils/-base-fragment/on-resume.md) | `open fun onResume(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [onSaveInstanceState](../../com.myapp.presentation.utils/-base-fragment/on-save-instance-state.md) | `open fun onSaveInstanceState(outState: `[`Bundle`](https://developer.android.com/reference/android/os/Bundle.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [onStart](../../com.myapp.presentation.utils/-base-fragment/on-start.md) | `open fun onStart(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [onStop](../../com.myapp.presentation.utils/-base-fragment/on-stop.md) | `open fun onStop(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |

### Inheritors

| Name | Summary |
|---|---|
| [FfsFactFragment](../-ffs-fact-fragment/index.md) | `class FfsFactFragment : `[`DiaryBaseFragment`](./index.md)<br>振り返り_事実画面 |
| [FfsFindFragment](../-ffs-find-fragment/index.md) | `class FfsFindFragment : `[`DiaryBaseFragment`](./index.md)<br>振り返り_発見画面 |
| [FfsLearnFragment](../-ffs-learn-fragment/index.md) | `class FfsLearnFragment : `[`DiaryBaseFragment`](./index.md)<br>振り返り_教訓画面 |
| [FfsStatementFragment](../-ffs-statement-fragment/index.md) | `class FfsStatementFragment : `[`DiaryBaseFragment`](./index.md)<br>振り返り_宣言画面 |
| [WeatherImproveFragment](../-weather-improve-fragment/index.md) | `class WeatherImproveFragment : `[`DiaryBaseFragment`](./index.md)<br>振り返り_改善案画面 |
| [WeatherReasonFragment](../-weather-reason-fragment/index.md) | `class WeatherReasonFragment : `[`DiaryBaseFragment`](./index.md)<br>振り返り_理由画面 |
