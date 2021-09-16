[document](../../index.md) / [com.myapp.domain.repository](../index.md) / [LocalReportRepository](./index.md)

# LocalReportRepository

`interface LocalReportRepository`

ローカルデータへのレポートのCRUD処理

### Functions

| Name | Summary |
|---|---|
| [getAllReport](get-all-report.md) | `abstract suspend fun getAllReport(): `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`Report`](../../com.myapp.domain.model.entity/-report/index.md)`>`<br>全レポート取得 |
| [saveReport](save-report.md) | `abstract suspend fun saveReport(report: `[`Report`](../../com.myapp.domain.model.entity/-report/index.md)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>レポート保存 |

### Inheritors

| Name | Summary |
|---|---|
| [LocalReportRepositoryImp](../../com.myapp.data.local/-local-report-repository-imp/index.md) | `class LocalReportRepositoryImp : `[`LocalReportRepository`](./index.md) |
