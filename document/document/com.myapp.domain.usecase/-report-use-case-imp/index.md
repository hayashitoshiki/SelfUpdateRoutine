[document](../../index.md) / [com.myapp.domain.usecase](../index.md) / [ReportUseCaseImp](./index.md)

# ReportUseCaseImp

`class ReportUseCaseImp : `[`ReportUseCase`](../-report-use-case/index.md)

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `ReportUseCaseImp(localReportRepository: `[`LocalReportRepository`](../../com.myapp.domain.repository/-local-report-repository/index.md)`, localSettingRepository: `[`LocalSettingRepository`](../../com.myapp.domain.repository/-local-setting-repository/index.md)`)` |

### Functions

| Name | Summary |
|---|---|
| [getAllReport](get-all-report.md) | `suspend fun getAllReport(): `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`Report`](../../com.myapp.domain.model.entity/-report/index.md)`>`<br>全ての振り返り日記取得 |
| [saveReport](save-report.md) | `suspend fun saveReport(allReportInputDto: `[`AllReportInputDto`](../../com.myapp.domain.dto/-all-report-input-dto/index.md)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>日記を登録 |
