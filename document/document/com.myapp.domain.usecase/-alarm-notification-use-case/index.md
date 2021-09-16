[document](../../index.md) / [com.myapp.domain.usecase](../index.md) / [AlarmNotificationUseCase](./index.md)

# AlarmNotificationUseCase

`interface AlarmNotificationUseCase`

アラーム通知機能

### Functions

| Name | Summary |
|---|---|
| [checkAlarmNotificationEnable](check-alarm-notification-enable.md) | `abstract fun checkAlarmNotificationEnable(): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>通知表示するか判定 |
| [getNextAlarmDateTime](get-next-alarm-date-time.md) | `abstract fun getNextAlarmDateTime(): `[`LocalDateTime`](https://developer.android.com/reference/java/time/LocalDateTime.html)<br>次回の通知時間を返す |

### Inheritors

| Name | Summary |
|---|---|
| [AlarmNotificationUseCaseImp](../-alarm-notification-use-case-imp/index.md) | `class AlarmNotificationUseCaseImp : `[`AlarmNotificationUseCase`](./index.md) |
