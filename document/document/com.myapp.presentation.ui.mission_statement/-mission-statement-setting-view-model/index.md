[document](../../index.md) / [com.myapp.presentation.ui.mission_statement](../index.md) / [MissionStatementSettingViewModel](./index.md)

# MissionStatementSettingViewModel

`class MissionStatementSettingViewModel : ViewModel`

ミッションステートメント設定画面_画面ロジック

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `MissionStatementSettingViewModel(missionStatement: `[`MissionStatement`](../../com.myapp.domain.model.entity/-mission-statement/index.md)`?, missionStatementUseCase: `[`MissionStatementUseCase`](../../com.myapp.domain.usecase/-mission-statement-use-case/index.md)`)`<br>ミッションステートメント設定画面_画面ロジック |

### Properties

| Name | Summary |
|---|---|
| [confirmStatus](confirm-status.md) | `val confirmStatus: LiveData<`[`Status`](../../com.myapp.presentation.utils/-status/index.md)`<*>>` |
| [constitutionList](constitution-list.md) | `val constitutionList: LiveData<`[`MutableList`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-list/index.html)`<`[`Pair`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-pair/index.html)`<`[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`, `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`>>>` |
| [constitutionListDiffColor](constitution-list-diff-color.md) | `val constitutionListDiffColor: LiveData<`[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`>` |
| [funeralList](funeral-list.md) | `val funeralList: LiveData<`[`MutableList`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-list/index.html)`<`[`Pair`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-pair/index.html)`<`[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`, `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`>>>` |
| [funeralListDiffColor](funeral-list-diff-color.md) | `val funeralListDiffColor: LiveData<`[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`>` |
| [isEnableConfirmButton](is-enable-confirm-button.md) | `val isEnableConfirmButton: LiveData<`[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)`>` |
| [purposeLife](purpose-life.md) | `val purposeLife: MutableLiveData<`[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`>` |
| [purposeLifeDiffColor](purpose-life-diff-color.md) | `val purposeLifeDiffColor: LiveData<`[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`>` |

### Functions

| Name | Summary |
|---|---|
| [onClickConfirmButton](on-click-confirm-button.md) | `fun onClickConfirmButton(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
