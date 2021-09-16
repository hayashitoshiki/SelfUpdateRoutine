[document](../../index.md) / [com.myapp.presentation.utils](../index.md) / [BaseFragment](./index.md)

# BaseFragment

`abstract class BaseFragment : Fragment`

Fragment基盤

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `BaseFragment()`<br>Fragment基盤 |

### Functions

| Name | Summary |
|---|---|
| [onAttach](on-attach.md) | `open fun onAttach(context: `[`Context`](https://developer.android.com/reference/android/content/Context.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [onCreate](on-create.md) | `open fun onCreate(savedInstanceState: `[`Bundle`](https://developer.android.com/reference/android/os/Bundle.html)`?): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [onDestroy](on-destroy.md) | `open fun onDestroy(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [onDestroyView](on-destroy-view.md) | `open fun onDestroyView(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [onDetach](on-detach.md) | `open fun onDetach(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [onPause](on-pause.md) | `open fun onPause(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [onResume](on-resume.md) | `open fun onResume(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [onSaveInstanceState](on-save-instance-state.md) | `open fun onSaveInstanceState(outState: `[`Bundle`](https://developer.android.com/reference/android/os/Bundle.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [onStart](on-start.md) | `open fun onStart(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [onStop](on-stop.md) | `open fun onStop(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [onViewCreated](on-view-created.md) | `open fun onViewCreated(view: `[`View`](https://developer.android.com/reference/android/view/View.html)`, savedInstanceState: `[`Bundle`](https://developer.android.com/reference/android/os/Bundle.html)`?): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |

### Inheritors

| Name | Summary |
|---|---|
| [BaseDetailListFragment](../../com.myapp.presentation.ui.home/-base-detail-list-fragment/index.md) | `abstract class BaseDetailListFragment : `[`BaseFragment`](./index.md)<br>一覧画面基盤 |
| [DiaryBaseFragment](../../com.myapp.presentation.ui.diary/-diary-base-fragment/index.md) | `abstract class DiaryBaseFragment : `[`BaseFragment`](./index.md)<br>振り返り画面_テキスト入力系画面 BaseFragment |
| [HomeFragment](../../com.myapp.presentation.ui.home/-home-fragment/index.md) | `class HomeFragment : `[`BaseFragment`](./index.md)<br>ホーム画面 |
| [MissionStatementListFragment](../../com.myapp.presentation.ui.mission_statement/-mission-statement-list-fragment/index.md) | `class MissionStatementListFragment : `[`BaseFragment`](./index.md)<br>ミッションステートメント一覧画面 |
| [MissionStatementSettingFragment](../../com.myapp.presentation.ui.mission_statement/-mission-statement-setting-fragment/index.md) | `class MissionStatementSettingFragment : `[`BaseFragment`](./index.md)<br>ミッションステートメント設定画面 |
| [RememberFragment](../../com.myapp.presentation.ui.remember/-remember-fragment/index.md) | `class RememberFragment : `[`BaseFragment`](./index.md)<br>設定画面 |
| [SettingFragment](../../com.myapp.presentation.ui.setting/-setting-fragment/index.md) | `class SettingFragment : `[`BaseFragment`](./index.md)<br>設定画面 |
