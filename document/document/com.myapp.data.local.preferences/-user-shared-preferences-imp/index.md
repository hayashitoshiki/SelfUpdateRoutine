[document](../../index.md) / [com.myapp.data.local.preferences](../index.md) / [UserSharedPreferencesImp](./index.md)

# UserSharedPreferencesImp

`class UserSharedPreferencesImp : `[`UserSharedPreferences`](../-user-shared-preferences/index.md)

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `UserSharedPreferencesImp(preferenceManager: `[`PreferenceManager`](../-preference-manager/index.md)`)` |

### Functions

| Name | Summary |
|---|---|
| [getAlarmDate](get-alarm-date.md) | `fun getAlarmDate(): `[`LocalDateTime`](https://developer.android.com/reference/java/time/LocalDateTime.html)<br>時間取得 |
| [getAlarmMode](get-alarm-mode.md) | `fun getAlarmMode(): `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)<br>アラームモード取得 |
| [getLastReportSaveDateTime](get-last-report-save-date-time.md) | `fun getLastReportSaveDateTime(): `[`LocalDateTime`](https://developer.android.com/reference/java/time/LocalDateTime.html)<br>最終レポート記録時間取得 |
| [setAlarmDate](set-alarm-date.md) | `fun setAlarmDate(date: `[`LocalDateTime`](https://developer.android.com/reference/java/time/LocalDateTime.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>時間設定 |
| [setAlarmMode](set-alarm-mode.md) | `fun setAlarmMode(mode: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>アラームモード設定 |
| [setLastReportSaveDateTime](set-last-report-save-date-time.md) | `fun setLastReportSaveDateTime(date: `[`LocalDateTime`](https://developer.android.com/reference/java/time/LocalDateTime.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>最終レポート記録時間設定 |
