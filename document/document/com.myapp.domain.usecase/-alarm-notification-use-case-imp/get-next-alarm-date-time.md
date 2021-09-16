[document](../../index.md) / [com.myapp.domain.usecase](../index.md) / [AlarmNotificationUseCaseImp](index.md) / [getNextAlarmDateTime](./get-next-alarm-date-time.md)

# getNextAlarmDateTime

`fun getNextAlarmDateTime(): `[`LocalDateTime`](https://developer.android.com/reference/java/time/LocalDateTime.html)

Overrides [AlarmNotificationUseCase.getNextAlarmDateTime](../-alarm-notification-use-case/get-next-alarm-date-time.md)

次回の通知時間を返す

アラームモード = 高　かつ 設定した時間 &lt; 現在の時間 &lt; 23:59:59
であれば現在＋５秒後の時間を返す
上記以外であれば設定した時間を返す

**Return**
次回通知バーを出す時間

