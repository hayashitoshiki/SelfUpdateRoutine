[document](../../index.md) / [com.myapp.presentation.ui.home](../index.md) / [BaseDetailListFragment](./index.md)

# BaseDetailListFragment

`abstract class BaseDetailListFragment : `[`BaseFragment`](../../com.myapp.presentation.utils/-base-fragment/index.md)

一覧画面基盤

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `BaseDetailListFragment()`<br>一覧画面基盤 |

### Properties

| Name | Summary |
|---|---|
| [_binding](_binding.md) | `var _binding: <ERROR CLASS>?` |
| [binding](binding.md) | `val binding: <ERROR CLASS>` |

### Functions

| Name | Summary |
|---|---|
| [onCreateView](on-create-view.md) | `open fun onCreateView(inflater: `[`LayoutInflater`](https://developer.android.com/reference/android/view/LayoutInflater.html)`, container: `[`ViewGroup`](https://developer.android.com/reference/android/view/ViewGroup.html)`?, savedInstanceState: `[`Bundle`](https://developer.android.com/reference/android/os/Bundle.html)`?): `[`View`](https://developer.android.com/reference/android/view/View.html) |
| [onDestroyView](on-destroy-view.md) | `open fun onDestroyView(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |

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
| [onViewCreated](../../com.myapp.presentation.utils/-base-fragment/on-view-created.md) | `open fun onViewCreated(view: `[`View`](https://developer.android.com/reference/android/view/View.html)`, savedInstanceState: `[`Bundle`](https://developer.android.com/reference/android/os/Bundle.html)`?): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |

### Inheritors

| Name | Summary |
|---|---|
| [LearnListFragment](../-learn-list-fragment/index.md) | `class LearnListFragment : `[`BaseDetailListFragment`](./index.md)<br>宣言一覧画面 |
| [StatementListFragment](../-statement-list-fragment/index.md) | `class StatementListFragment : `[`BaseDetailListFragment`](./index.md)<br>宣言一覧画面 |
