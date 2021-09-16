[document](../../index.md) / [com.myapp.data.local.database](../index.md) / [AppDatabase](./index.md)

# AppDatabase

`abstract class AppDatabase : RoomDatabase`

DB定義

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `AppDatabase()`<br>DB定義 |

### Functions

| Name | Summary |
|---|---|
| [constitutionDao](constitution-dao.md) | `abstract fun constitutionDao(): `[`ConstitutionDao`](../../com.myapp.data.local.database.dao.mission_statement/-constitution-dao/index.md) |
| [emotionReportDao](emotion-report-dao.md) | `abstract fun emotionReportDao(): `[`WeatherReportDao`](../../com.myapp.data.local.database.dao.report/-weather-report-dao/index.md) |
| [ffsReportDao](ffs-report-dao.md) | `abstract fun ffsReportDao(): `[`FfsReportDao`](../../com.myapp.data.local.database.dao.report/-ffs-report-dao/index.md) |
| [funeralDao](funeral-dao.md) | `abstract fun funeralDao(): `[`FuneralDao`](../../com.myapp.data.local.database.dao.mission_statement/-funeral-dao/index.md) |
| [purposeLifeDao](purpose-life-dao.md) | `abstract fun purposeLifeDao(): `[`PurposeLifeDao`](../../com.myapp.data.local.database.dao.mission_statement/-purpose-life-dao/index.md) |

### Companion Object Properties

| Name | Summary |
|---|---|
| [MIGRATION_2_3](-m-i-g-r-a-t-i-o-n_2_3.md) | `val MIGRATION_2_3: Migration` |
