[document](../../index.md) / [com.myapp.presentation.ui.mission_statement](../index.md) / [MissionStatementListViewModel](./index.md)

# MissionStatementListViewModel

`class MissionStatementListViewModel : ViewModel`

ミッションステートメント一覧画面_画面ロジック

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `MissionStatementListViewModel(missionStatementUseCase: `[`MissionStatementUseCase`](../../com.myapp.domain.usecase/-mission-statement-use-case/index.md)`)`<br>ミッションステートメント一覧画面_画面ロジック |

### Properties

| Name | Summary |
|---|---|
| [constitutionList](constitution-list.md) | `val constitutionList: LiveData<`[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`>>` |
| [funeralList](funeral-list.md) | `val funeralList: LiveData<`[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`>>` |
| [isEnableConstitutionList](is-enable-constitution-list.md) | `val isEnableConstitutionList: LiveData<`[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)`>` |
| [isEnableFuneralList](is-enable-funeral-list.md) | `val isEnableFuneralList: LiveData<`[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)`>` |
| [isEnablePurposeLife](is-enable-purpose-life.md) | `val isEnablePurposeLife: LiveData<`[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)`>` |
| [missionStatement](mission-statement.md) | `var missionStatement: `[`MissionStatement`](../../com.myapp.domain.model.entity/-mission-statement/index.md)`?` |
| [purposeLife](purpose-life.md) | `val purposeLife: LiveData<`[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`>` |
| [status](status.md) | `val status: LiveData<`[`Status`](../../com.myapp.presentation.utils/-status/index.md)`<*>>` |
