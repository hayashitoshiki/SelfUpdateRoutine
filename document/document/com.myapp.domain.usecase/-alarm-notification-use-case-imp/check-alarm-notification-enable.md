[document](../../index.md) / [com.myapp.domain.usecase](../index.md) / [AlarmNotificationUseCaseImp](index.md) / [checkAlarmNotificationEnable](./check-alarm-notification-enable.md)

# checkAlarmNotificationEnable

`fun checkAlarmNotificationEnable(): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)

Overrides [AlarmNotificationUseCase.checkAlarmNotificationEnable](../-alarm-notification-use-case/check-alarm-notification-enable.md)

通知表示するか判定

アラーム通知時刻になった時、
・既に振り返りレポートが登録されていたら通知しない
・まだ登録されていなかったら通知する

**Return**
通知するかの判定結果

