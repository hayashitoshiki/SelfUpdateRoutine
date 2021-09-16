[document](../../index.md) / [com.myapp.domain.repository](../index.md) / [LocalSettingRepository](./index.md)

# LocalSettingRepository

`interface LocalSettingRepository`

ローカルデータへの設定機能のCRUD処理

### Functions

| Name | Summary |
|---|---|
| [getAlarmDate](get-alarm-date.md) | `abstract fun getAlarmDate(): `[`LocalDateTime`](https://developer.android.com/reference/java/time/LocalDateTime.html)<br>設定しているアラーム日付取得 |
| [getAlarmMode](get-alarm-mode.md) | `abstract fun getAlarmMode(): `[`AlarmMode`](../../com.myapp.domain.model.value/-alarm-mode/index.md)<br>設定しているアラームモード取得 |
| [getLastReportSaveDateTime](get-last-report-save-date-time.md) | `abstract fun getLastReportSaveDateTime(): `[`LocalDateTime`](https://developer.android.com/reference/java/time/LocalDateTime.html)<br>最終レポートを記録した時間取得 |
| [saveAlarmDate](save-alarm-date.md) | `abstract fun saveAlarmDate(date: `[`LocalDateTime`](https://developer.android.com/reference/java/time/LocalDateTime.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>アラーム日付更新 |
| [saveAlarmMode](save-alarm-mode.md) | `abstract fun saveAlarmMode(mode: `[`AlarmMode`](../../com.myapp.domain.model.value/-alarm-mode/index.md)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>アラームモード更新 |
| [setLastReportSaveDateTime](set-last-report-save-date-time.md) | `abstract fun setLastReportSaveDateTime(date: `[`LocalDateTime`](https://developer.android.com/reference/java/time/LocalDateTime.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>最終レポート時間更新 |

### Inheritors

| Name | Summary |
|---|---|
| [LocalSettingRepositoryImp](../../com.myapp.data.local/-local-setting-repository-imp/index.md) | `class LocalSettingRepositoryImp : `[`LocalSettingRepository`](./index.md) |
