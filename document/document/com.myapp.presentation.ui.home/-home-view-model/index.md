[document](../../index.md) / [com.myapp.presentation.ui.home](../index.md) / [HomeViewModel](./index.md)

# HomeViewModel

`class HomeViewModel : ViewModel`

ホーム画面　画面ロジック

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `HomeViewModel(reportUseCase: `[`ReportUseCase`](../../com.myapp.domain.usecase/-report-use-case/index.md)`, missionStatementUseCase: `[`MissionStatementUseCase`](../../com.myapp.domain.usecase/-mission-statement-use-case/index.md)`)`<br>ホーム画面　画面ロジック |

### Properties

| Name | Summary |
|---|---|
| [assessmentInputImg](assessment-input-img.md) | `val assessmentInputImg: LiveData<`[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`>` |
| [factComment](fact-comment.md) | `val factComment: LiveData<`[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`>` |
| [findComment](find-comment.md) | `val findComment: LiveData<`[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`>` |
| [improveComment](improve-comment.md) | `val improveComment: LiveData<`[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`>` |
| [isFabVisibility](is-fab-visibility.md) | `val isFabVisibility: LiveData<`[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)`>` |
| [isNotReportListVisibility](is-not-report-list-visibility.md) | `val isNotReportListVisibility: LiveData<`[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)`>` |
| [isReportListVisibility](is-report-list-visibility.md) | `val isReportListVisibility: LiveData<`[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)`>` |
| [learnComment](learn-comment.md) | `val learnComment: LiveData<`[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`>` |
| [mainContainerType](main-container-type.md) | `val mainContainerType: LiveData<`[`HomeFragmentMainContainerType`](../-home-fragment-main-container-type/index.md)`<`[`Report`](../../com.myapp.domain.model.entity/-report/index.md)`>>` |
| [missionStatement](mission-statement.md) | `val missionStatement: LiveData<`[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`>` |
| [reasonComment](reason-comment.md) | `val reasonComment: LiveData<`[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`>` |
| [report](report.md) | `val report: LiveData<`[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`Report`](../../com.myapp.domain.model.entity/-report/index.md)`>>` |
| [statementComment](statement-comment.md) | `val statementComment: LiveData<`[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`>` |
