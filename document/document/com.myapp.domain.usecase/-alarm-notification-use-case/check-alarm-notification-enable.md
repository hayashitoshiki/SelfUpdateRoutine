[document](../../index.md) / [com.myapp.domain.usecase](../index.md) / [AlarmNotificationUseCase](index.md) / [checkAlarmNotificationEnable](./check-alarm-notification-enable.md)

# checkAlarmNotificationEnable

`abstract fun checkAlarmNotificationEnable(): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)

通知表示するか判定

アラーム通知時刻になった時、
・既に振り返りレポートが登録されていたら通知しない
・まだ登録されていなかったら通知する

**Return**
通知するかの判定結果

