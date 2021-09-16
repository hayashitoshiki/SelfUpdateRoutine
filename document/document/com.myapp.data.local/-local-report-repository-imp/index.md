[document](../../index.md) / [com.myapp.data.local](../index.md) / [LocalReportRepositoryImp](./index.md)

# LocalReportRepositoryImp

`class LocalReportRepositoryImp : `[`LocalReportRepository`](../../com.myapp.domain.repository/-local-report-repository/index.md)

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `LocalReportRepositoryImp(weatherReportDao: `[`WeatherReportDao`](../../com.myapp.data.local.database.dao.report/-weather-report-dao/index.md)`, ffsReportDao: `[`FfsReportDao`](../../com.myapp.data.local.database.dao.report/-ffs-report-dao/index.md)`)` |

### Functions

| Name | Summary |
|---|---|
| [getAllReport](get-all-report.md) | `suspend fun getAllReport(): `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`Report`](../../com.myapp.domain.model.entity/-report/index.md)`>`<br>全レポート取得 |
| [saveReport](save-report.md) | `suspend fun saveReport(report: `[`Report`](../../com.myapp.domain.model.entity/-report/index.md)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>レポート保存 |
