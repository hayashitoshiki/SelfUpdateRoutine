[document](../../index.md) / [com.myapp.domain.usecase](../index.md) / [ReportUseCase](./index.md)

# ReportUseCase

`interface ReportUseCase`

今日の振り返り機能

### Functions

| Name | Summary |
|---|---|
| [getAllReport](get-all-report.md) | `abstract suspend fun getAllReport(): `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`Report`](../../com.myapp.domain.model.entity/-report/index.md)`>`<br>全ての振り返り日記取得 |
| [saveReport](save-report.md) | `abstract suspend fun saveReport(allReportInputDto: `[`AllReportInputDto`](../../com.myapp.domain.dto/-all-report-input-dto/index.md)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>日記を登録 |

### Inheritors

| Name | Summary |
|---|---|
| [ReportUseCaseImp](../-report-use-case-imp/index.md) | `class ReportUseCaseImp : `[`ReportUseCase`](./index.md) |
