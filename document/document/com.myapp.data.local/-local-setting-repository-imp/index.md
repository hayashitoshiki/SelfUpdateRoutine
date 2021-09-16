[document](../../index.md) / [com.myapp.data.local](../index.md) / [LocalSettingRepositoryImp](./index.md)

# LocalSettingRepositoryImp

`class LocalSettingRepositoryImp : `[`LocalSettingRepository`](../../com.myapp.domain.repository/-local-setting-repository/index.md)

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `LocalSettingRepositoryImp(userSharedPreferences: `[`UserSharedPreferences`](../../com.myapp.data.local.preferences/-user-shared-preferences/index.md)`)` |

### Functions

| Name | Summary |
|---|---|
| [getAlarmDate](get-alarm-date.md) | `fun getAlarmDate(): `[`LocalDateTime`](https://developer.android.com/reference/java/time/LocalDateTime.html)<br>設定しているアラーム日付取得 |
| [getAlarmMode](get-alarm-mode.md) | `fun getAlarmMode(): `[`AlarmMode`](../../com.myapp.domain.model.value/-alarm-mode/index.md)<br>設定しているアラームモード取得 |
| [getLastReportSaveDateTime](get-last-report-save-date-time.md) | `fun getLastReportSaveDateTime(): `[`LocalDateTime`](https://developer.android.com/reference/java/time/LocalDateTime.html)<br>最終レポートを記録した時間取得 |
| [saveAlarmDate](save-alarm-date.md) | `fun saveAlarmDate(date: `[`LocalDateTime`](https://developer.android.com/reference/java/time/LocalDateTime.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>アラーム日付更新 |
| [saveAlarmMode](save-alarm-mode.md) | `fun saveAlarmMode(mode: `[`AlarmMode`](../../com.myapp.domain.model.value/-alarm-mode/index.md)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>アラームモード更新 |
| [setLastReportSaveDateTime](set-last-report-save-date-time.md) | `fun setLastReportSaveDateTime(date: `[`LocalDateTime`](https://developer.android.com/reference/java/time/LocalDateTime.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>最終レポート時間更新 |
