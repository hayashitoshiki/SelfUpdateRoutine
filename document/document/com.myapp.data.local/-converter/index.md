[document](../../index.md) / [com.myapp.data.local](../index.md) / [Converter](./index.md)

# Converter

`object Converter`

DomainModel ←→ DataBase Entity　コンバーダー

### Functions

| Name | Summary |
|---|---|
| [constitutionEntityListFromMissionStatement](constitution-entity-list-from-mission-statement.md) | `fun constitutionEntityListFromMissionStatement(missionStatement: `[`MissionStatement`](../../com.myapp.domain.model.entity/-mission-statement/index.md)`): `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`ConstitutionEntity`](../../com.myapp.data.local.database.entity.mission_statement/-constitution-entity/index.md)`>`<br>ミッションステートメントオブジェクト -&gt; 憲法Entityリスト |
| [ffsReportEntityFromFfsReport](ffs-report-entity-from-ffs-report.md) | `fun ffsReportEntityFromFfsReport(ffsReport: `[`FfsReport`](../../com.myapp.domain.model.entity/-ffs-report/index.md)`): `[`FfsReportEntity`](../../com.myapp.data.local.database.entity.report/-ffs-report-entity/index.md)<br>FFS式日記オブジェクト -&gt; FFS式日記Entity |
| [ffsReportFromFfsReportEntity](ffs-report-from-ffs-report-entity.md) | `fun ffsReportFromFfsReportEntity(ffsReportEntity: `[`FfsReportEntity`](../../com.myapp.data.local.database.entity.report/-ffs-report-entity/index.md)`): `[`FfsReport`](../../com.myapp.domain.model.entity/-ffs-report/index.md)<br>FFS式日記Entity -&gt; FFS式日記オブジェクト |
| [funeralEntityListFromMissionStatement](funeral-entity-list-from-mission-statement.md) | `fun funeralEntityListFromMissionStatement(missionStatement: `[`MissionStatement`](../../com.myapp.domain.model.entity/-mission-statement/index.md)`): `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`FuneralEntity`](../../com.myapp.data.local.database.entity.mission_statement/-funeral-entity/index.md)`>`<br>ミッションステートメントオブジェクト -&gt; 理想の葬儀Entityリスト |
| [missionStatementFromEntity](mission-statement-from-entity.md) | `fun missionStatementFromEntity(funeralEntityList: `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`FuneralEntity`](../../com.myapp.data.local.database.entity.mission_statement/-funeral-entity/index.md)`>, purposeLifeEntity: `[`PurposeLifeEntity`](../../com.myapp.data.local.database.entity.mission_statement/-purpose-life-entity/index.md)`?, constitutionEntityList: `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`ConstitutionEntity`](../../com.myapp.data.local.database.entity.mission_statement/-constitution-entity/index.md)`>): `[`MissionStatement`](../../com.myapp.domain.model.entity/-mission-statement/index.md)`?`<br>各種Entityからミッションステートメントオブジェクト生成 |
| [purposeLifeEntityFromMissionStatement](purpose-life-entity-from-mission-statement.md) | `fun purposeLifeEntityFromMissionStatement(missionStatement: `[`MissionStatement`](../../com.myapp.domain.model.entity/-mission-statement/index.md)`): `[`PurposeLifeEntity`](../../com.myapp.data.local.database.entity.mission_statement/-purpose-life-entity/index.md)<br>ミッションステートメントオブジェクト -&gt; 人生の目的Entity |
| [weatherReportEntityFromWeatherReport](weather-report-entity-from-weather-report.md) | `fun weatherReportEntityFromWeatherReport(weatherReport: `[`WeatherReport`](../../com.myapp.domain.model.entity/-weather-report/index.md)`): `[`WeatherReportEntity`](../../com.myapp.data.local.database.entity.report/-weather-report-entity/index.md)<br>感情日記オブジェクト -&gt; 感情日記Entity |
| [weatherReportFromWeatherReportEntity](weather-report-from-weather-report-entity.md) | `fun weatherReportFromWeatherReportEntity(weatherReportEntity: `[`WeatherReportEntity`](../../com.myapp.data.local.database.entity.report/-weather-report-entity/index.md)`): `[`WeatherReport`](../../com.myapp.domain.model.entity/-weather-report/index.md)<br>感情日記Entity -&gt; 感情日記オブジェクト |
