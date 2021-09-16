[document](../../index.md) / [com.myapp.chatmemo.presentation.utils.expansion](../index.md) / [BaseFragment](./index.md)

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
| [ChatFragment](../../com.myapp.chatmemo.presentation.chat/-chat-fragment/index.md) | `class ChatFragment : `[`BaseFragment`](./index.md)<br>チャット画面 |
| [HomeFragment](../../com.myapp.chatmemo.presentation.chat/-home-fragment/index.md) | `class HomeFragment : `[`BaseFragment`](./index.md)<br>ホーム画面 |
| [RoomAddFragment](../../com.myapp.chatmemo.presentation.chat/-room-add-fragment/index.md) | `class RoomAddFragment : `[`BaseFragment`](./index.md)<br>新規ルーム作成画面 |
| [TemplateAddFragment](../../com.myapp.chatmemo.presentation.template/-template-add-fragment/index.md) | `class TemplateAddFragment : `[`BaseFragment`](./index.md)<br>定型文作成画面 |
| [TtemplateListFragment](../../com.myapp.chatmemo.presentation.template/-ttemplate-list-fragment/index.md) | `class TtemplateListFragment : `[`BaseFragment`](./index.md)<br>定型文一覧画面 |
