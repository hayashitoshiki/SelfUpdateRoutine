[document](../../index.md) / [com.myapp.domain.usecase](../index.md) / [AlarmNotificationUseCaseImp](./index.md)

# AlarmNotificationUseCaseImp

`class AlarmNotificationUseCaseImp : `[`AlarmNotificationUseCase`](../-alarm-notification-use-case/index.md)

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `AlarmNotificationUseCaseImp(localSettingRepository: `[`LocalSettingRepository`](../../com.myapp.domain.repository/-local-setting-repository/index.md)`)` |

### Functions

| Name | Summary |
|---|---|
| [checkAlarmNotificationEnable](check-alarm-notification-enable.md) | `fun checkAlarmNotificationEnable(): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>通知表示するか判定 |
| [getNextAlarmDateTime](get-next-alarm-date-time.md) | `fun getNextAlarmDateTime(): `[`LocalDateTime`](https://developer.android.com/reference/java/time/LocalDateTime.html)<br>次回の通知時間を返す |
