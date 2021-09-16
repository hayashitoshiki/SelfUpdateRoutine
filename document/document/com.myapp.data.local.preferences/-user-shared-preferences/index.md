[document](../../index.md) / [com.myapp.data.local.preferences](../index.md) / [UserSharedPreferences](./index.md)

# UserSharedPreferences

`interface UserSharedPreferences`

Preferenceへの設定情報格納

### Functions

| Name | Summary |
|---|---|
| [getAlarmDate](get-alarm-date.md) | `abstract fun getAlarmDate(): `[`LocalDateTime`](https://developer.android.com/reference/java/time/LocalDateTime.html)<br>時間取得 |
| [getAlarmMode](get-alarm-mode.md) | `abstract fun getAlarmMode(): `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)<br>アラームモード取得 |
| [getLastReportSaveDateTime](get-last-report-save-date-time.md) | `abstract fun getLastReportSaveDateTime(): `[`LocalDateTime`](https://developer.android.com/reference/java/time/LocalDateTime.html)<br>最終レポート記録時間取得 |
| [setAlarmDate](set-alarm-date.md) | `abstract fun setAlarmDate(date: `[`LocalDateTime`](https://developer.android.com/reference/java/time/LocalDateTime.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>時間設定 |
| [setAlarmMode](set-alarm-mode.md) | `abstract fun setAlarmMode(mode: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>アラームモード設定 |
| [setLastReportSaveDateTime](set-last-report-save-date-time.md) | `abstract fun setLastReportSaveDateTime(date: `[`LocalDateTime`](https://developer.android.com/reference/java/time/LocalDateTime.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>最終レポート記録時間設定 |

### Inheritors

| Name | Summary |
|---|---|
| [UserSharedPreferencesImp](../-user-shared-preferences-imp/index.md) | `class UserSharedPreferencesImp : `[`UserSharedPreferences`](./index.md) |
